package com.example.uberapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.VideoView;

import com.example.uberapp.account.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }
        },5500);
        VideoView videoView = (VideoView) findViewById(R.id.videoView);  //casting to VideoView is not Strictly required above API level 26
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.splashscreen);
        setDimension(videoView);//set the path of the video that we need to use in our VideoView
        videoView.start();  //start() method of the VideoView class will start the video to play

    }
    private void setDimension(VideoView videoView) {
        // Adjust the size of the video
        // so it fits on the screen
        float videoProportion = getVideoProportion();
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        float screenProportion = (float) screenHeight / (float) screenWidth;
        android.view.ViewGroup.LayoutParams lp = videoView.getLayoutParams();

        if (videoProportion < screenProportion) {
            lp.height= screenHeight;
            lp.width = (int) ((float) screenHeight / videoProportion);
        } else {
            lp.width = screenWidth;
            lp.height = (int) ((float) screenWidth * videoProportion);
        }
        videoView.setLayoutParams(lp);
    }

    // This method gets the proportion of the video that you want to display.
// I already know this ratio since my video is hardcoded, you can get the
// height and width of your video and appropriately generate  the proportion
//    as :height/width
    private float getVideoProportion(){
        return 1.5f;
    }
}