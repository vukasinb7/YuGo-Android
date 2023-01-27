package com.example.uberapp.gui.fragments.history;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.AllRidesDTO;
import com.example.uberapp.core.dto.MessageDTO;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.PassengerService;
import com.example.uberapp.gui.adapters.DriverHistoryAdapter;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHistoryFragment extends Fragment implements SensorEventListener {
    DriverService driverService = APIClient.getClient().create(DriverService.class);
    PassengerService passengerService = APIClient.getClient().create(PassengerService.class);
    private SensorManager sensorManager;
    private View view;
    private long lastUpdate;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 1500;
    private boolean sortDesc;
    public UserHistoryFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
        last_x = last_y = last_z = 0;
        sortDesc = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_history, container, false);
        this.view=view;
        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        loadHistory();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onPause() {

        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                this.lastUpdate = curTime;

                float[] values = event.values;
                float x = values[0];
                float y = values[1];
                float z = values[2];

                float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    sortDesc = !sortDesc;
                    this.loadHistory();
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void loadHistory(){
        ListView listView = view.findViewById(R.id.driverHistoryList);
        Call<AllRidesDTO> userRidesCall;
        if (TokenManager.getRole().equals("DRIVER")){
            userRidesCall = driverService.getDriverRides(TokenManager.getUserId());
        }
        else{
            userRidesCall = passengerService.getPassengerRides(TokenManager.getUserId());
        }
        userRidesCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<AllRidesDTO> call, Response<AllRidesDTO> response) {
                List<RideDetailedDTO> rides= response.body().getResults();
                if (sortDesc){
                    rides.sort(Comparator.comparing(RideDetailedDTO::getStartTime).reversed());
                }
                else{
                    rides.sort(Comparator.comparing(RideDetailedDTO::getStartTime));
                }
                DriverHistoryAdapter adapter = new DriverHistoryAdapter((Activity) getContext(),rides);
                listView.setAdapter(adapter);
                // Inflate the layout for this fragment

            }

            @Override
            public void onFailure(Call<AllRidesDTO> call, Throwable t) {

            }

        });}
}