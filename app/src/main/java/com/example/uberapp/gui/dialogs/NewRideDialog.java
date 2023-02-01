package com.example.uberapp.gui.dialogs;

import static android.content.Context.LOCATION_SERVICE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.LocationDTO;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.RideService;
import com.example.uberapp.gui.fragments.home.CreateRideSubfragment01;
import com.example.uberapp.gui.fragments.home.MapFragment;

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

public class NewRideDialog extends DialogFragment implements android.view.View.OnClickListener {
    private MapView map;
    private LocationManager locationManager;
    public Button yes, no;
    TextView price,distance,numOfPerson,startLocation,endLocation;
    private Integer rideID;
    private Context context;
    private FragmentManager fragmentManager;
    RideService rideService = APIClient.getClient().create(RideService.class);
    public static String TAG = "NewRideDialog";

    public interface OnAcceptRideListener{
        void acceptRide(RideDetailedDTO ride);
    }
    OnAcceptRideListener acceptRideListener;

    public NewRideDialog(Integer rideID) {
        this.rideID=rideID;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        acceptRideListener = (OnAcceptRideListener) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_acceptance_ride, null, false);

        Context context = getContext();
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        map = view.findViewById(R.id.mapViewOffer);
        loadMap();
        IMapController mapController = map.getController();
        mapController.setZoom(12.0);
        mapController.setCenter(new GeoPoint(44.97639, 19.61222));


        fragmentManager = getChildFragmentManager();
        yes = (Button) view.findViewById(R.id.accept);
        no = (Button) view.findViewById(R.id.decline);
        price =(TextView) view.findViewById(R.id.priceRideOffer);
        distance=(TextView) view.findViewById(R.id.remainingDistRideOffer);
        numOfPerson=(TextView) view.findViewById(R.id.personNumRideOffer);
        startLocation=(TextView) view.findViewById(R.id.startDestRideOffer);
        endLocation=(TextView) view.findViewById(R.id.endDestRideOffer);

        Call<RideDetailedDTO> call = rideService.getRide(rideID);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<RideDetailedDTO> call, @NonNull Response<RideDetailedDTO> response) {
                if (response.code() == 200) {
                    RideDetailedDTO ride = response.body();
                    price.setText("$"+Double.toString(Math.round(ride.getTotalCost()*100)/100.0));
                    numOfPerson.setText("5");
                    startLocation.setText(cleanUpLocation(ride.getLocations().get(0).getDeparture().getAddress()));
                    endLocation.setText(cleanUpLocation(ride.getLocations().get(0).getDestination().getAddress()));
                    loadMap();

                    LocationDTO departure = ride.getLocations().get(0).getDeparture();
                    LocationDTO destination = ride.getLocations().get(0).getDestination();

                    getLength(departure.getLatitude(), departure.getLongitude(),destination.getLatitude(), destination.getLongitude(),new CallbackLength() {
                        @Override
                        public void onSuccess(Double value) {
                            distance.setText(Double.toString(Math.round(value*100)/100.0)+"km");
                        }
                    });
                    numOfPerson.setText(Integer.toString(ride.getPassengers().size()));


                    createMarker(departure.getLatitude(), departure.getLongitude(), "Departure",R.drawable.start_location_pin);
                    createMarker(destination.getLatitude(), destination.getLongitude(), "Destination",R.drawable.finish_location_pin);
                    createRoute(departure.getLatitude(), departure.getLongitude(),destination.getLatitude(), destination.getLongitude());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RideDetailedDTO> call, @NonNull Throwable t) {

            }
        });
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
                            acceptRideListener.acceptRide(response.body());
                            dismiss();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<RideDetailedDTO> call, @NonNull Throwable t) {

                    }
                });

                break;
            case R.id.decline:
                Dialog dialog=new ReasonDialog(getActivity(),rideID,"REJECTION");
                dialog.show();
                dismiss();
                break;
            default:
                break;
        }
    }
    public void loadMap() {
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
    public void createMarker(double latitude, double longitude, String title,Integer drawableID){
        if(map == null || map.getRepository() == null) {
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
        Drawable d = ResourcesCompat.getDrawable(getResources(), drawableID, null);
        marker.setIcon(d);
        map.getOverlays().add(marker);
        map.invalidate();
        IMapController mapController = map.getController();
        mapController.setZoom(14.0);
        mapController.setCenter(geoPoint);
    }


    public void createRoute(double startLatitude,double startLongitude, double endLatitude, double endLongitude){
        if(map == null || map.getRepository() == null) {
            return;
        }

        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                RoadManager roadManager = new OSRMRoadManager(getContext(),"RoadManager");

                ArrayList<GeoPoint> track = new ArrayList<>();
                GeoPoint startPoint = new GeoPoint(startLatitude, startLongitude );
                GeoPoint endPoint = new GeoPoint(endLatitude, endLongitude);
                track.add(startPoint);
                track.add(endPoint);

                Road road = roadManager.getRoad(track);

                Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        map.getOverlays().add(roadOverlay);
                        map.invalidate();
                        IMapController mapController = map.getController();
                        mapController.setZoom(12.0);
                        mapController.setCenter(new GeoPoint((startLatitude+endLatitude)/2,
                                (startLongitude+endLongitude)/2));
                    }
                });
            }
        });

    }
    public Double getLength(double startLatitude,double startLongitude,double endLatitude,double endLongitude,final CallbackLength callback){
        if(map == null || map.getRepository() == null) {
            return null;
        }
        final Double[] length = {0.0};

        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                RoadManager roadManager = new OSRMRoadManager(getContext(),"RoadManager");

                ArrayList<GeoPoint> track = new ArrayList<>();
                GeoPoint startPoint = new GeoPoint(startLatitude, startLongitude );
                GeoPoint endPoint = new GeoPoint(endLatitude, endLongitude);
                track.add(startPoint);
                track.add(endPoint);

                Road road = roadManager.getRoad(track);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(road.mLength);
                    }
                });
            }
        });
        return length[0];
    }
    @Override
    public void onPause() {
        super.onPause();
        locationManager = null;
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
        locationManager = null;
    }
    private interface CallbackLength {
        void onSuccess(Double value);
    }
    private String cleanUpLocation(String location){
        if (location.contains(",")){
            String[] partialLocations=location.split(",");
            String result="";
            for (int i=0;i< partialLocations.length;i++){
                if(i<=2){
                    if (i== partialLocations.length-1 || i==2)
                        result=result+partialLocations[i];
                    else
                        result=result+partialLocations[i]+", ";
                }
            }
            return result;
        }
        return location;

    }
}