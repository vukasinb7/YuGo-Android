package com.example.uberapp.gui.fragments.account;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.ImageService;
import com.example.uberapp.core.services.PassengerService;
import com.example.uberapp.core.services.UserService;
import com.example.uberapp.gui.activities.DriverMainActivity;
import com.example.uberapp.gui.activities.LoginActivity;
import com.example.uberapp.gui.adapters.viewpagers.DriverAccountViewPagerAdapter;
import com.example.uberapp.gui.adapters.viewpagers.PassengerAccountViewPagerAdapter;
import com.example.uberapp.gui.dialogs.ReasonDialog;
import com.example.uberapp.gui.fragments.home.MapFragment;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    TabLayout accountTabLayout;
    ViewPager2 accountViewPager;
    FragmentStateAdapter accountAdapter;

    DriverService driverService = APIClient.getClient().create(DriverService.class);
    PassengerService passengerService = APIClient.getClient().create(PassengerService.class);
    UserService userService = APIClient.getClient().create(UserService.class);
    ImageService imageService = APIClient.getClient().create(ImageService.class);

    public AccountFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Fragment parentFragment = this;

        Call<UserDetailedDTO> userCall;
        if (TokenManager.getRole().equals("DRIVER")){
            userCall = driverService.getDriver(TokenManager.getUserId());
        }
        else{
            userCall = passengerService.getPassenger(TokenManager.getUserId());
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
                            byte[] bytes = new byte[0];
                            bytes = response.body().bytes();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ShapeableImageView imageView = view.findViewById(R.id.profilePic);
                            imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Ups, something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

                accountTabLayout = view.findViewById(R.id.accountTabLayout);
                accountViewPager = view.findViewById(R.id.accountViewPager);
                if (TokenManager.getRole().equals("DRIVER")){
                    accountAdapter =  new DriverAccountViewPagerAdapter(parentFragment, user);
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_round_account));
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_key));
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_car));
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_document));
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_chart));
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_newspaper));
                }
                else{
                    accountAdapter =  new PassengerAccountViewPagerAdapter(parentFragment, user);
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_round_account));
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_key));
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_newspaper));
                }
                accountViewPager.setAdapter(accountAdapter);
                accountViewPager.setOffscreenPageLimit(5);
                accountTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        accountViewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

                accountViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        accountTabLayout.getTabAt(position).select();
                    }
                });
            }
            @Override
            public void onFailure(@NonNull Call<UserDetailedDTO> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Ups, something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton logoutButtonDriver = view.findViewById(R.id.logoutButton);
        logoutButtonDriver.setOnClickListener(v -> {
            Call<ResponseBody> logoutCall = userService.logout();
            logoutCall.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    TokenManager.clearToken();
                    Intent loginPage = new Intent(getActivity(), LoginActivity.class);
                    startActivity(loginPage);
                    getActivity().finish();
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Ups, something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }
}