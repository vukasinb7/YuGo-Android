package com.example.uberapp.gui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.VideoView;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("YuGo", Context.MODE_PRIVATE);
                String retrievedToken  = preferences.getString("accessToken",null);
//                if (retrievedToken!=null)
//                {
//                    TokenManager.setToken(retrievedToken);
//                    TokenManager.setRefreshToken(preferences.getString("refreshToken",null));
//                    if (TokenManager.getRole().equals("PASSENGER"))
//                        startActivity(new Intent(SplashScreenActivity.this, PassengerMainActivity.class));
//                    else
//                        startActivity(new Intent(SplashScreenActivity.this, DriverMainActivity.class));
//                }else
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }
        },500);
        VideoView videoView = (VideoView) findViewById(R.id.videoView);  //casting to VideoView is not Strictly required above API level 26
        if (getResources().getConfiguration().uiMode== 33)
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.splashscreen);
        else
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.splashscreen_white);
        setDimension(videoView);//set the path of the video that we need to use in our VideoView
        videoView.start();  //start() method of the VideoView class will start the video to play

    }
    private void setDimension(VideoView videoView) {
        // Adjust the size of the video
        // so it fits on the screen
        float videoProportion = getVideoProportion();
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        System.out.println(screenWidth);
        System.out.println(screenHeight);
        float screenProportion = (float) screenHeight / (float) screenWidth;
        android.view.ViewGroup.LayoutParams lp = videoView.getLayoutParams();
        System.out.println(lp.height);
        System.out.println(lp.width);
        int width=1080;
        int height=1920;
        videoView.setScaleX((float)screenHeight/1920+0.2f);
        videoView.setScaleY((float)screenHeight/1920+0.2f);
    }

    // This method gets the proportion of the video that you want to display.
// I already know this ratio since my video is hardcoded, you can get the
// height and width of your video and appropriately generate  the proportion
//    as :height/width
    private float getVideoProportion(){
        return 1.5f;
    }
}