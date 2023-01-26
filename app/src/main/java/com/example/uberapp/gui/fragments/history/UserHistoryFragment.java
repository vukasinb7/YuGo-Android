package com.example.uberapp.gui.fragments.history;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.PassengerService;
import com.example.uberapp.gui.adapters.DriverHistoryAdapter;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHistoryFragment extends Fragment {
    DriverService driverService = APIClient.getClient().create(DriverService.class);
    PassengerService passengerService = APIClient.getClient().create(PassengerService.class);
    public UserHistoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_history, container, false);
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
                                      DriverHistoryAdapter adapter = new DriverHistoryAdapter((Activity) getContext(),rides);
                                      listView.setAdapter(adapter);
                                      // Inflate the layout for this fragment

                                  }

                                  @Override
                                  public void onFailure(Call<AllRidesDTO> call, Throwable t) {

                                  }

                              });

        return view;

    }
    @Override
    public void onPause() {

        super.onPause();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}