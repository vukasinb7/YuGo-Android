package com.example.uberapp.gui.dialogs;

import static android.content.Context.LOCATION_SERVICE;

import android.app.Activity;
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
import androidx.cardview.widget.CardView;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.LocationDTO;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.RideService;
import com.example.uberapp.core.services.auth.TokenManager;
import com.example.uberapp.gui.activities.DriverMainActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRideDialog extends Dialog implements android.view.View.OnClickListener {
    MapView map;
    LocationManager locationManager;
    public Activity c;
    public Button yes, no;
    TextView price,distance,numOfPerson,startLocation,endLocation;
    public Integer rideID;
    RideService rideService = APIClient.getClient().create(RideService.class);

    public NewRideDialog(Activity a, Integer rideID) {
        super(a);
        this.c = a;
        this.rideID=rideID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.list_item_acceptance_ride);
        yes = (Button) findViewById(R.id.accept);
        no = (Button) findViewById(R.id.decline);
        price =(TextView) findViewById(R.id.priceRideOffer);
        distance=(TextView) findViewById(R.id.remainingDistRideOffer);
        numOfPerson=(TextView) findViewById(R.id.personNumRideOffer);
        startLocation=(TextView) findViewById(R.id.startDestRideOffer);
        endLocation=(TextView) findViewById(R.id.endDestRideOffer);

        Context context = getContext();
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        map = (MapView) findViewById(R.id.mapViewOffer);
        loadMap();













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
                    createMarker(departure.getLatitude(), departure.getLongitude(), "Departure");
                    createMarker(destination.getLatitude(), destination.getLongitude(), "Destination");

                }
            }

            @Override
            public void onFailure(@NonNull Call<RideDetailedDTO> call, @NonNull Throwable t) {

            }
        });
        
        price.setText("AAA");
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.accept:
                dismiss();
                break;
            case R.id.decline:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
    private void loadMap() {
        map.getTileProvider().clearTileCache();
        Configuration.getInstance().setCacheMapTileCount((short) 12);
        Configuration.getInstance().setCacheMapTileOvershoot((short) 12);
        map.setTileSource(new OnlineTileSourceBase("", 1, 20,
                512, ".png",
                new String[]{"https://a.tile.openstreetmap.org/"}) {
            @Override
            public String getTileURLString(long pMapTileIndex) {
                return getBaseUrl()
                        + MapTileIndex.getZoom(pMapTileIndex)
                        + "/" + MapTileIndex.getX(pMapTileIndex)
                        + "/" + MapTileIndex.getY(pMapTileIndex)
                        + mImageFilenameEnding;
            }
        });
        map.setMultiTouchControls(true);
        map.invalidate();
    }
    private void createMarker(double latitude, double longitude, String title){
        if(map == null) {
            return;
        }
        for(int i=0;i<map.getOverlays().size();i++){
            Overlay overlay=map.getOverlays().get(i);
            if(overlay instanceof Marker && ((Marker) overlay).getId().equals(title)){
                map.getOverlays().remove(overlay);
            }
        }

        Marker marker = new Marker(map);
        GeoPoint geoPoint = new GeoPoint(latitude,longitude);
        marker.setPosition(geoPoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setTitle(title);
        marker.setId(title);
        marker.setPanToView(true);
        map.getOverlays().add(marker);
        map.invalidate();
        IMapController mapController = map.getController();
        mapController.setZoom(14.0);
        mapController.setCenter(geoPoint);
    }
}