package com.example.uberapp.gui.fragments.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.LocationDTO;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.ImageService;
import com.example.uberapp.core.services.PassengerService;
import com.example.uberapp.core.services.RideService;
import com.example.uberapp.gui.activities.UserChatChannel;
import com.example.uberapp.gui.dialogs.NewRideDialog;
import com.example.uberapp.gui.dialogs.ReasonDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentRideFragment extends Fragment {
    private static final String ARG_RIDE = "ride";
    private PassengerService passengerService;
    private RideService rideService;
    private DriverService driverService;
    private ImageService imageService;
    private RideDetailedDTO ride;
    public OnEndCurrentRideListener endCurrentRideListener;

    public CurrentRideFragment() {
    }

    public interface OnEndCurrentRideListener{
        void endCurrentRide();
    }

    public static CurrentRideFragment newInstance(RideDetailedDTO ride, Fragment parentFragment) {
        CurrentRideFragment fragment = new CurrentRideFragment();
        fragment.endCurrentRideListener = (OnEndCurrentRideListener) parentFragment;
        Bundle args = new Bundle();
        args.putSerializable(ARG_RIDE, ride);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ride = (RideDetailedDTO) getArguments().get(ARG_RIDE);
        passengerService = APIClient.getClient().create(PassengerService.class);
        driverService = APIClient.getClient().create(DriverService.class);
        imageService = APIClient.getClient().create(ImageService.class);
        rideService = APIClient.getClient().create(RideService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_ride, container, false);
        ImageView profilePic = view.findViewById(R.id.driverProfilePic);
        CardView panic= view.findViewById(R.id.panicCurrentRide);
        TextView distanceTb=view.findViewById(R.id.passengerRemainingDistCurrentTime);
        TextView timeRemainingTb=view.findViewById(R.id.passengerRemainingTimeCurrentRide);
        TextView timeRemainingTotalTb=view.findViewById(R.id.passengerDurationDist);
        ExtendedFloatingActionButton endRide= view.findViewById(R.id.end_ride_button);
        LocationDTO departure= ride.getLocations().get(0).getDeparture();
        LocationDTO destination= ride.getLocations().get(0).getDestination();

        Call<UserDetailedDTO> userCall;
        getLength(departure.getLatitude(), departure.getLongitude(),destination.getLatitude(), destination.getLongitude(),new CurrentRideFragment.CallbackLengthTime() {
            @Override
            public void onSuccess(Double distance, Double remainingTime) {
                distanceTb.setText(Double.toString(Math.round(distance*100)/100)+"km");
                timeRemainingTb.setText(String.valueOf((int)Math.round(remainingTime/60))+"min");
                timeRemainingTotalTb.setText(String.valueOf((int)Math.round(remainingTime/60))+"min");
            }
        });



        if (TokenManager.getRole().equals("DRIVER")){
            userCall = passengerService.getPassenger(ride.getPassengers().get(0).getId());
        }
        else{
            userCall = driverService.getDriver(ride.getDriver().getId());
        }
        userCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserDetailedDTO> call, @NonNull Response<UserDetailedDTO> response) {
                UserDetailedDTO user = response.body();

                Call<ResponseBody> profilePictureCall = imageService.getProfilePicture(user.getProfilePicture());
                profilePictureCall.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        try {
                            byte[] bytes = response.body().bytes();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            profilePic.setImageBitmap(bitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                    }
                });

                profilePic.setOnClickListener(v -> showPopupMenu(v, user));

                panic.setOnClickListener(v -> {
                    Dialog dialog = new ReasonDialog(getActivity(),ride.getId(),"PANIC");
                    dialog.show();
                });

                if (TokenManager.getRole().equals("DRIVER")){
                    endRide.setVisibility(View.VISIBLE);
                    endRide.setOnClickListener(v -> {
                        Call<RideDetailedDTO> call1 = rideService.endRide(ride.getId());
                        call1.enqueue(new Callback<>() {
                            @Override
                            public void onResponse(@NonNull Call<RideDetailedDTO> call1, @NonNull Response<RideDetailedDTO> response1) {
                                if (response1.code() == 200) {
                                    Toast.makeText(getContext(), "Ride Ended!", Toast.LENGTH_SHORT).show();
                                    endCurrentRideListener.endCurrentRide();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<RideDetailedDTO> call1, @NonNull Throwable t) {

                                Toast.makeText(getContext(), "Ride Ended!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                }

            }

            @Override
            public void onFailure(@NonNull Call<UserDetailedDTO> call, @NonNull Throwable t) {

            }
        });
        return view;
    }

    public void showPopupMenu(View view, UserDetailedDTO user){
        Context wrapper = new ContextThemeWrapper(getActivity(), R.style.popupBgStyle);
        PopupMenu popupMenu = new PopupMenu(wrapper,view);
        Menu menu = popupMenu.getMenu();
        menu.add(0 , 0, 0,user.getName() + " " + user.getSurname());
        MenuItem call = menu.add(1 , 1, 1, "Call (" + user.getTelephoneNumber() + ")");
        call.setOnMenuItemClickListener(item -> {
            Uri number = Uri.parse("tel:" + user.getTelephoneNumber());
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            startActivity(callIntent);
            return true;
        });
        MenuItem message = menu.add(2 , 2, 2,"Message");
        message.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(this.getActivity(), UserChatChannel.class);
            Bundle bundle = new Bundle();
            bundle.putInt("senderId", user.getId());
            bundle.putInt("rideId", ride.getId());
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        });
        menu.add(3 , 3, 3,"Report");
        menu.setGroupDividerEnabled(true);
        popupMenu.show();
    }
    public void getLength(double startLatitude,double startLongitude,double endLatitude,double endLongitude,final CurrentRideFragment.CallbackLengthTime callback){

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
                        callback.onSuccess(road.mLength,road.mDuration);
                    }
                });
            }
        });
    }
    private interface CallbackLengthTime {
        void onSuccess(Double distance,Double remainingTime);
    }
}