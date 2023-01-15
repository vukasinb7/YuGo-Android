package com.example.uberapp.gui.dialogs;

import static android.content.Context.LOCATION_SERVICE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.LocationDTO;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.RideService;
import com.example.uberapp.gui.fragments.home.MapFragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRideDialog extends DialogFragment implements android.view.View.OnClickListener {
    MapFragment mapFragment;
    LocationManager locationManager;
    public Button yes, no;
    TextView price,distance,numOfPerson,startLocation,endLocation;
    private Integer rideID;
    private Context context;
    private FragmentManager fragmentManager;
    RideService rideService = APIClient.getClient().create(RideService.class);
    public static String TAG = "NewRideDialog";

    public NewRideDialog(Integer rideID) {
        this.rideID=rideID;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_acceptance_ride, null, false);
        mapFragment = MapFragment.newInstance(false);
        fragmentManager = getChildFragmentManager();
        yes = (Button) view.findViewById(R.id.accept);
        no = (Button) view.findViewById(R.id.decline);
        price =(TextView) view.findViewById(R.id.priceRideOffer);
        distance=(TextView) view.findViewById(R.id.remainingDistRideOffer);
        numOfPerson=(TextView) view.findViewById(R.id.personNumRideOffer);
        startLocation=(TextView) view.findViewById(R.id.startDestRideOffer);
        endLocation=(TextView) view.findViewById(R.id.endDestRideOffer);
        Context context = getContext();
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        Call<RideDetailedDTO> call = rideService.getRide(rideID);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<RideDetailedDTO> call, @NonNull Response<RideDetailedDTO> response) {
                if (response.code() == 200) {
                    RideDetailedDTO ride = response.body();
                    LocationDTO departure = ride.getLocations().get(0).getDeparture();
                    LocationDTO destination = ride.getLocations().get(0).getDestination();
                    price.setText("$"+ride.getTotalCost().toString());
                    numOfPerson.setText("5");
                    startLocation.setText(ride.getLocations().get(0).getDeparture().getAddress());
                    endLocation.setText(ride.getLocations().get(0).getDestination().getAddress());

                    fragmentManager.beginTransaction().replace(R.id.mapViewOffer, mapFragment).commit();
                    mapFragment.createMarker(departure.getLatitude(), departure.getLongitude(), "Departure");
                    mapFragment.createMarker(destination.getLatitude(), destination.getLongitude(), "Destination");
                    mapFragment.createRoute(departure.getLatitude(), departure.getLongitude(),destination.getLatitude(), destination.getLongitude());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RideDetailedDTO> call, @NonNull Throwable t) {

            }
        });
        
        price.setText("AAA");
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        builder.setView(view);
        return builder.create();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.accept:
                Call<RideDetailedDTO> call = rideService.acceptRide(rideID);
                call.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<RideDetailedDTO> call, @NonNull Response<RideDetailedDTO> response) {
                        if (response.code() == 200) {
                            dismiss();
                            }
                    }

                    @Override
                    public void onFailure(@NonNull Call<RideDetailedDTO> call, @NonNull Throwable t) {

                    }
                });

                break;
            case R.id.decline:
                new ReasonDialog(rideID,"REJECTION").show(getChildFragmentManager(),ReasonDialog.TAG);

                break;
            default:
                break;
        }
    }
}