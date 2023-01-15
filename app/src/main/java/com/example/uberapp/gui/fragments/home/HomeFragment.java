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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements CurrentRideFragment.OnEndCurrentRideListener {
    private MapFragment mapFragment;
    private RideService rideService;
    private RideDetailedDTO nextRide;
    private boolean hasActiveRide;
    private ExtendedFloatingActionButton startRideButton;
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rideService = APIClient.getClient().create(RideService.class);
    }
    public void acceptRide(RideDetailedDTO ride){
        nextRide = ride;
        if(!hasActiveRide){
            startRideButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        mapFragment = MapFragment.newInstance(true);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_home_map, mapFragment).commit();

        startRideButton = view.findViewById(R.id.buttonStartRide);
        startRideButton.setOnClickListener(v -> {
            Call<RideDetailedDTO> startRide = rideService.startRide(nextRide.getId());
            startRide.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<RideDetailedDTO> call, Response<RideDetailedDTO> response) {
                    CurrentRideFragment currentRideFragment = CurrentRideFragment.newInstance(nextRide);
                    fragmentManager.beginTransaction().replace(R.id.fragment_current_ride, currentRideFragment).commit();
                    startRideButton.setVisibility(View.GONE);
                    hasActiveRide = true;

                    LocationDTO departure = nextRide.getLocations().get(0).getDeparture();
                    LocationDTO destination = nextRide.getLocations().get(0).getDestination();
                    mapFragment.createRoute(departure.getLatitude(), departure.getLongitude(),
                            destination.getLatitude(), destination.getLongitude());

                }

                @Override
                public void onFailure(Call<RideDetailedDTO> call, Throwable t) {
                    Toast.makeText(getContext(), "Ups, something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // TODO kada se vozac uloguje proveriti da li ima ACCEPTED voznje, ako ima postaviti start ride dugme na VISIBLE
        Call<RideDetailedDTO> activeRide;
        if (TokenManager.getRole().equals("DRIVER")){
            activeRide = rideService.getActiveDriverRide(TokenManager.getUserId());
            CardView toggleButton = view.findViewById(R.id.online_offline_button);
            toggleButton.setVisibility(View.VISIBLE);
        }
        else{
            activeRide = rideService.getActivePassengerRide(TokenManager.getUserId());
        }
        activeRide.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<RideDetailedDTO> call, @NonNull Response<RideDetailedDTO> response) {
                if (response.code() == 200) {
                    RideDetailedDTO ride = response.body();
                    CurrentRideFragment currentRideFragment =  CurrentRideFragment.newInstance(ride);
                    fragmentManager.beginTransaction().replace(R.id.fragment_current_ride, currentRideFragment).commit();
                    hasActiveRide = true;

                    LocationDTO departure = ride.getLocations().get(0).getDeparture();
                    LocationDTO destination = ride.getLocations().get(0).getDestination();
                    mapFragment.createRoute(departure.getLatitude(), departure.getLongitude(),
                            destination.getLatitude(), destination.getLongitude());

                }
                else{
                    if (TokenManager.getRole().equals("PASSENGER")) {
                        ExtendedFloatingActionButton createRideButton = view.findViewById(R.id.buttonCreateRide);
                        createRideButton.setVisibility(View.VISIBLE);
                        createRideButton.setOnClickListener(view1 -> new CreateRideFragment()
                                .show(getChildFragmentManager().beginTransaction(),
                                        CreateRideFragment.TAG));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RideDetailedDTO> call, @NonNull Throwable t) {

            }
        });

        return view;
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

    @Override
    public void endCurrentRide() {
        hasActiveRide = false;
        if(nextRide != null){
            startRideButton.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_home_map, mapFragment).commit();
        }
    }
}