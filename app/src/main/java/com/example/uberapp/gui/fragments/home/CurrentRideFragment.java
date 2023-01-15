package com.example.uberapp.gui.fragments.home;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.ImageService;
import com.example.uberapp.core.services.PassengerService;
import com.example.uberapp.core.services.RideService;
import com.example.uberapp.core.services.UserService;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.gui.dialogs.ReasonDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.IOException;

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

    public CurrentRideFragment() {
    }

    public static CurrentRideFragment newInstance(RideDetailedDTO ride) {
        CurrentRideFragment fragment = new CurrentRideFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_ride, container, false);
        ImageView profilePic = view.findViewById(R.id.driverProfilePic);
        CardView panic= view.findViewById(R.id.panicCurrentRide);
        ExtendedFloatingActionButton endRide= view.findViewById(R.id.end_ride_button);

        Call<UserDetailedDTO> userCall;
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

                profilePic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopupMenu(v, user);
                    }
                });
                panic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new ReasonDialog(ride.getId(),"PANIC").show(getChildFragmentManager(), ReasonDialog.TAG);
                    }
                });
                endRide.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<RideDetailedDTO> call = rideService.endRide(ride.getId());
                        call.enqueue(new Callback<>() {
                            @Override
                            public void onResponse(@NonNull Call<RideDetailedDTO> call, @NonNull Response<RideDetailedDTO> response) {
                                if (response.code() == 200) {
                                    Toast.makeText(getContext(), "Ride Ended!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<RideDetailedDTO> call, @NonNull Throwable t) {

                            }
                        });
                    }
                });

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
        menu.add(2 , 2, 2,"Message");
        menu.add(3 , 3, 3,"Report");
        menu.setGroupDividerEnabled(true);
        popupMenu.show();
    }
}