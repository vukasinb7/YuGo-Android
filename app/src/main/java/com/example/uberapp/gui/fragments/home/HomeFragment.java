package com.example.uberapp.gui.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.LocationDTO;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.RideService;
import com.example.uberapp.core.auth.TokenManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class HomeFragment extends Fragment implements CurrentRideFragment.OnEndCurrentRideListener {
    private MapFragment mapFragment;
    private CurrentRideFragment currentRideFragment;
    private final RideService rideService = APIClient.getClient().create(RideService.class);
    private RideDetailedDTO nextRide;
    private boolean hasActiveRide;
    private ExtendedFloatingActionButton startRideButton;
    private ExtendedFloatingActionButton createRideButton;
    private CardView onlineButton;
    private Fragment parentFragment;
    private FragmentManager fragmentManager;

    private StompClient mStompClient;
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        createRideButton = view.findViewById(R.id.buttonCreateRide);
        startRideButton = view.findViewById(R.id.buttonStartRide);
        onlineButton = view.findViewById(R.id.online_offline_button);

        fragmentManager = getActivity().getSupportFragmentManager();

        createRideButton.setOnClickListener(view1 -> new CreateRideFragment()
                .show(getChildFragmentManager().beginTransaction(),
                        CreateRideFragment.TAG));

        Call<RideDetailedDTO> activeRide;
        if (TokenManager.getRole().equals("DRIVER")){
            activeRide = rideService.getActiveDriverRide(TokenManager.getUserId());
            initStartRideButton();
            onlineButton.setVisibility(View.VISIBLE);
            mapFragment = MapFragment.newInstance(false);
        }
        else{
            activeRide = rideService.getActivePassengerRide(TokenManager.getUserId());
            mapFragment = MapFragment.newInstance(false);

            mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://192.168.1.33:9000/api/socket/websocket");

            mStompClient.topic("/ride-topic/notify-passenger-end-ride/"+ TokenManager.getUserId()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(topicMessage -> {
                        hasActiveRide = false;
                        mapFragment = MapFragment.newInstance(true);
                        fragmentManager.beginTransaction().replace(R.id.fragment_home_map, mapFragment).commit();
                        fragmentManager.beginTransaction().remove(currentRideFragment).commit();
                        createRideButton.setVisibility(View.VISIBLE);

                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            System.out.println(throwable.getMessage());
                        }
                    });
            mStompClient.topic("/ride-topic/notify-passenger-start-ride/"+ TokenManager.getUserId()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(topicMessage -> {
                        hasActiveRide = true;
                        createRideButton.setVisibility(View.GONE);
                        mapFragment = MapFragment.newInstance(false);
                        fragmentManager.beginTransaction().replace(R.id.fragment_home_map, mapFragment).commit();
                        Call<RideDetailedDTO> activeRideNotified;
                        activeRideNotified = rideService.getActivePassengerRide(TokenManager.getUserId());
                        activeRideNotified.enqueue(new Callback<>() {
                           @Override
                           public void onResponse(@NonNull Call<RideDetailedDTO> call, @NonNull Response<RideDetailedDTO> response) {
                               if (response.code() == 200) {
                                   RideDetailedDTO ride = response.body();
                                   currentRideFragment =  CurrentRideFragment.newInstance(ride, parentFragment);
                                   fragmentManager.beginTransaction().replace(R.id.fragment_current_ride, currentRideFragment).commit();
                                   hasActiveRide = true;
                                   LocationDTO departure = ride.getLocations().get(0).getDeparture();
                                   LocationDTO destination = ride.getLocations().get(0).getDestination();
                                   mapFragment.createRoute(departure.getLatitude(), departure.getLongitude(),
                                           destination.getLatitude(), destination.getLongitude());

                               }}

                            @Override
                            public void onFailure(Call<RideDetailedDTO> call, Throwable t) {

                            }
                        });

                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            System.out.println(throwable.getMessage());
                        }
                    });
            mStompClient.lifecycle().subscribe(lifecycleEvent -> {
                switch (lifecycleEvent.getType()) {

                    case OPENED:
                        System.out.println("OPENED");
                        break;

                    case ERROR:
                        System.out.println(lifecycleEvent.getException().getMessage());
                        break;

                    case CLOSED:
                        System.out.println("CLOSED");
                        break;
                }
            });

            mStompClient.connect();

        }

        fragmentManager.beginTransaction().replace(R.id.fragment_home_map, mapFragment).commit();
        parentFragment = this;
        activeRide.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<RideDetailedDTO> call, @NonNull Response<RideDetailedDTO> response) {
                if (response.code() == 200) {
                    RideDetailedDTO ride = response.body();
                    currentRideFragment =  CurrentRideFragment.newInstance(ride, parentFragment);
                    fragmentManager.beginTransaction().replace(R.id.fragment_current_ride, currentRideFragment).commit();
                    hasActiveRide = true;
                    LocationDTO departure = ride.getLocations().get(0).getDeparture();
                    LocationDTO destination = ride.getLocations().get(0).getDestination();
                    mapFragment.createRoute(departure.getLatitude(), departure.getLongitude(),
                            destination.getLatitude(), destination.getLongitude());

                }
                else{
                    if (TokenManager.getRole().equals("DRIVER")) {
                        // TODO kada se vozac uloguje proveriti da li ima ACCEPTED voznje, ako ima postaviti start ride dugme na VISIBLE
                    }
                    else{
                        createRideButton.setVisibility(View.VISIBLE);


                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RideDetailedDTO> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Ups, something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void initStartRideButton(){
        startRideButton.setOnClickListener(v -> {
            Call<RideDetailedDTO> startRide = rideService.startRide(nextRide.getId());
            startRide.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<RideDetailedDTO> call, Response<RideDetailedDTO> response) {
                    currentRideFragment = CurrentRideFragment.newInstance(nextRide, parentFragment);
                    fragmentManager.beginTransaction().replace(R.id.fragment_current_ride, currentRideFragment).commit();
                    startRideButton.setVisibility(View.GONE);
                    hasActiveRide = true;
                    LocationDTO departure = nextRide.getLocations().get(0).getDeparture();
                    LocationDTO destination = nextRide.getLocations().get(0).getDestination();
                    mapFragment.createRoute(departure.getLatitude(), departure.getLongitude(),
                            destination.getLatitude(), destination.getLongitude());

                    nextRide = null;

                }

                @Override
                public void onFailure(Call<RideDetailedDTO> call, Throwable t) {
                    Toast.makeText(getContext(), "Ups, something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void acceptRide(RideDetailedDTO ride){
        nextRide = ride;
        if(!hasActiveRide){
            startRideButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void endCurrentRide() {
        hasActiveRide = false;
        if(nextRide != null){
            startRideButton.setVisibility(View.VISIBLE);
        }

        if(TokenManager.getRole().equals("DRIVER"))
            mapFragment = MapFragment.newInstance(false);
        else
            mapFragment = MapFragment.newInstance(true);
        fragmentManager.beginTransaction().replace(R.id.fragment_home_map, mapFragment).commit();
        fragmentManager.beginTransaction().remove(currentRideFragment).commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onPause();
    }
}