package com.example.uberapp.gui.fragments.home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberapp.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

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
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PassengerHomeFragment extends Fragment {

    MapView map;
    public PassengerHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // redirecting to passenger current ride for now
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flPassengerMainFragment,  new PassengerCurrentRideFragment()).commit();

        View view = inflater.inflate(R.layout.fragment_passenger_home, container, false);
        Context ctx = getActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        map = (MapView) view.findViewById(R.id.mapView);
        map.getTileProvider().clearTileCache();
        Configuration.getInstance().setCacheMapTileCount((short)12);
        Configuration.getInstance().setCacheMapTileOvershoot((short)12);
        // Create a custom tile source
        map.setTileSource(new OnlineTileSourceBase("", 1, 20, 512, ".png",
                new String[] { "https://a.tile.openstreetmap.org/" }) {
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
        IMapController mapController = map.getController();
        GeoPoint startPoint;
        startPoint = new GeoPoint(51.0, 4.0);
        mapController.setZoom(11.0);
        mapController.setCenter(startPoint);
        final Context context = this.getContext();
        map.invalidate();
        createmarker(44.975980,19.583750,"Pocetak");
        createmarker(44.985980, 19.593750,"Kraj");
        createRoute(44.975980,19.583750,44.985980, 19.593750);



        ExtendedFloatingActionButton button = view.findViewById(R.id.buttonCreateRide);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CreateRideFragment().show(getChildFragmentManager().beginTransaction(), CreateRideFragment.TAG);
            }
        });
        return view;
        // Inflate the layout for this fragment
    }
    public void createmarker(double latitude,double longitude,String title){
        if(map == null) {
            return;
        }

        Marker my_marker = new Marker(map);
        my_marker.setPosition(new GeoPoint(latitude,longitude));
        my_marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        my_marker.setTitle(title);
        my_marker.setPanToView(true);
        map.getOverlays().add(my_marker);
        map.invalidate();
    }
    public void createRoute(double startLatitude,double startLongitude, double endLatitude, double endLongitude){
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                RoadManager roadManager = new OSRMRoadManager(getContext(),"Pera");

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
                        mapController.setZoom(14.0);
                        mapController.setCenter(new GeoPoint((startLatitude+endLatitude)/2,(startLongitude+endLongitude)/2));

                    }
                });

            }
        });

    }
}