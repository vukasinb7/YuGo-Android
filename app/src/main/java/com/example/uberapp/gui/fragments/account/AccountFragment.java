package com.example.uberapp.gui.fragments.account;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.DocumentDTO;
import com.example.uberapp.core.dto.PictureResponseDTO;
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

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    TabLayout accountTabLayout;
    ViewPager2 accountViewPager;
    FragmentStateAdapter accountAdapter;
    UserDetailedDTO user;
    DriverService driverService = APIClient.getClient().create(DriverService.class);
    PassengerService passengerService = APIClient.getClient().create(PassengerService.class);
    UserService userService = APIClient.getClient().create(UserService.class);
    ImageService imageService = APIClient.getClient().create(ImageService.class);
    ShapeableImageView profilePicture;
    ImageButton logoutButtonDriver;
    Fragment thisFragment;
    public AccountFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        profilePicture = view.findViewById(R.id.profilePic);
        logoutButtonDriver = view.findViewById(R.id.logoutButton);
        accountTabLayout = view.findViewById(R.id.accountTabLayout);
        accountViewPager = view.findViewById(R.id.accountViewPager);
        thisFragment = this;

        this.setupAccount();
        this.setupButtons();

        return view;
    }

    public void setupAccount(){
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
                user = response.body();

                Call<ResponseBody> profilePictureCall = imageService.getProfilePicture(user.getProfilePicture());
                profilePictureCall.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        try {
                            byte[] bytes = response.body().bytes();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            profilePicture.setImageBitmap(bitmap);
                            Toast.makeText(getContext(), "Profile picture uploaded successfully!", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Ups, something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

                if (TokenManager.getRole().equals("DRIVER")){
                    accountAdapter =  new DriverAccountViewPagerAdapter(thisFragment, user);
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_round_account));
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_key));
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_car));
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_document));
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_chart));
                    accountTabLayout.addTab(accountTabLayout.newTab().setText("").setIcon(R.drawable.icon_newspaper));
                }
                else{
                    accountAdapter =  new PassengerAccountViewPagerAdapter(thisFragment, user);
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
    }

    public void setupButtons(){
        logoutButtonDriver.setOnClickListener(v -> {
            Call<ResponseBody> logoutCall = userService.logout();
            logoutCall.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    TokenManager.clearToken();
                    Intent loginPage = new Intent(getActivity(), LoginActivity.class);
                    startActivity(loginPage);
                    getActivity().finish();
                    SharedPreferences preferences = getActivity().getSharedPreferences("YuGo", Context.MODE_PRIVATE);
                    preferences.edit().remove("accessToken").commit();
                    preferences.edit().remove("refreshToken").commit();
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Oops, something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        });

        ActivityResultLauncher<Intent> pickProfilePicture =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        File file = new File(getPath(result.getData().getData()));
                        RequestBody body =  RequestBody.create(file, MediaType.parse("multipart/form-data"));
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), body);
                        Call<PictureResponseDTO> uploadProfilePictureCall = imageService.uploadProfilePicture(user.getId(), filePart);
                        uploadProfilePictureCall.enqueue(new Callback<>() {
                            @Override
                            public void onResponse(@NonNull Call<PictureResponseDTO> call, @NonNull Response<PictureResponseDTO> response) {
                                if (response.code() == 200){
                                    user.setProfilePicture(response.body().getPictureName());
                                    profilePicture.setImageURI(result.getData().getData());
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<PictureResponseDTO> call, @NonNull Throwable t) {
                                Toast.makeText(getContext(), "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(getContext(), "You didn't select any image!", Toast.LENGTH_SHORT).show();
                    }
                });

        profilePicture.setOnClickListener(v -> {
            if (!Environment.isExternalStorageManager()) {
                Intent getPermission = new Intent();
                getPermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getPermission);
            }

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickProfilePicture.launch(intent);
        });
    }

    public String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = new CursorLoader(getContext(), uri, projection, null, null, null).loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }
}