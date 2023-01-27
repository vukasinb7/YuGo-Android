package com.example.uberapp.gui.fragments.inbox;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.AllMessagesDTO;
import com.example.uberapp.core.dto.MessageDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.UserService;
import com.example.uberapp.gui.adapters.InboxMessageAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMessagesFragment extends Fragment implements SensorEventListener {
    private final UserService userService = APIClient.getClient().create(UserService.class);
    private SensorManager sensorManager;
    private View view;
    private long lastUpdate;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 800;
    private boolean sortDesc;
    public UserMessagesFragment() {
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
        View view = inflater.inflate(R.layout.fragment_user_messages, container, false);
        this.view = view;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadConversations();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
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
                    this.loadConversations();
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

    public void loadConversations(){
        Call<AllMessagesDTO> allMessagesCall = userService.getUserMessages(TokenManager.getUserId());
        allMessagesCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<AllMessagesDTO> call, @NonNull Response<AllMessagesDTO> response) {
                if (response.code() == 200){
                    AllMessagesDTO allMessagesDTO = response.body();
                    List<MessageDTO> messages = allMessagesDTO.getMessages();

                    ListView listView = (ListView) view.findViewById(R.id.listViewMessages);
                    InboxMessageAdapter adapter = new InboxMessageAdapter((Activity) getContext(), getConversations(messages));
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllMessagesDTO> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<MessageDTO> getConversations(List<MessageDTO> messages){
        HashMap<Integer, MessageDTO> hashMap = new HashMap<>();
        messages.sort(Comparator.comparing(MessageDTO::getTimeOfSending));
        for(int i=0 ; i < messages.size(); i++){
            if (messages.get(i).getSenderId().equals(TokenManager.getUserId())){
                MessageDTO tempMess = new MessageDTO(messages.get(i));
                tempMess.setMessage("Me: " + tempMess.getMessage());
                Integer tempReceiverId = tempMess.getReceiverId();
                Integer tempSenderId = tempMess.getSenderId();
                tempMess.setSenderId(tempReceiverId);
                tempMess.setReceiverId(tempSenderId);
                hashMap.put(tempReceiverId,tempMess);
            }
            else{
                hashMap.put(messages.get(i).getSenderId(), messages.get(i));
            }
        }
        ArrayList<MessageDTO> conversations = new ArrayList<>(hashMap.values());
        if (sortDesc){
            conversations.sort(Comparator.comparing(MessageDTO::getTimeOfSending).reversed());
        }
        else{
            conversations.sort(Comparator.comparing(MessageDTO::getTimeOfSending));
        }
        return conversations;
    }
}