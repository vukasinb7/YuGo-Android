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
    private CurrentRideFragment currentRideFragment;
    private final RideService rideService = APIClient.getClient().create(RideService.class);
    private RideDetailedDTO nextRide;
    private boolean hasActiveRide;
    private ExtendedFloatingActionButton startRideButton;
    private ExtendedFloatingActionButton createRideButton;
    private CardView onlineButton;
    private Fragment parentFragment;
    private FragmentManager fragmentManager;
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

        Call<RideDetailedDTO> activeRide;
        if (TokenManager.getRole().equals("DRIVER")){
            activeRide = rideService.getActiveDriverRide(TokenManager.getUserId());
            initStartRideButton();
            onlineButton.setVisibility(View.VISIBLE);
            mapFragment = MapFragment.newInstance(false);
        }
        else{
            activeRide = rideService.getActivePassengerRide(TokenManager.getUserId());
            mapFragment = MapFragment.newInstance(true);
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
                        createRideButton.setOnClickListener(view1 -> new CreateRideFragment()
                                .show(getChildFragmentManager().beginTransaction(),
                                        CreateRideFragment.TAG));

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
        mapFragment = MapFragment.newInstance(false);
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