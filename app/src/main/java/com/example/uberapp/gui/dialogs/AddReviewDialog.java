package com.example.uberapp.gui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.AcumulatedReviewsDTO;
import com.example.uberapp.core.dto.ReviewRequestDTO;
import com.example.uberapp.core.dto.ReviewResponseDTO;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.ReviewService;
import com.example.uberapp.core.services.RideService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReviewDialog extends Dialog implements  View.OnClickListener {
    private Integer rideID;
    private Button submit,cancel;
    private RatingBar driverRating,vehicleRating;
    private TextView driverComment,vehicleComment;
    RideService rideService = APIClient.getClient().create(RideService.class);
    ReviewService reviewService = APIClient.getClient().create(ReviewService.class);

    public AddReviewDialog(Activity a, Integer rideID){
        super(a);
        this.rideID=rideID;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_review);

        submit=(Button) findViewById(R.id.submitReview);
        cancel=(Button) findViewById(R.id.cancelReview);
        driverRating=(RatingBar) findViewById(R.id.driverRatingReview);
        vehicleRating=(RatingBar) findViewById(R.id.vehicleRatingReview);
        driverComment=(TextView) findViewById(R.id.commentInputDriverRating);
        vehicleComment=(TextView) findViewById(R.id.commentInputPassengerRating);
        Call<RideDetailedDTO> getRide=rideService.getRide(rideID);
        Call<List<AcumulatedReviewsDTO>>getRideReviews=reviewService.getRideReviews(rideID);
        final ReviewResponseDTO[] rideReview = {null};
        final ReviewResponseDTO[] vehicleReview = {null};

        getRide.enqueue(new Callback<RideDetailedDTO>() {
            @Override
            public void onResponse(Call<RideDetailedDTO> call, Response<RideDetailedDTO> response) {
                RideDetailedDTO ride=response.body();
                LocalDateTime rideDate= LocalDateTime.parse(ride.getStartTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                long diff =Math.abs(ChronoUnit.DAYS.between(LocalDateTime.now(),rideDate));
                if (diff>3){
                    driverRating.setIsIndicator(true);
                    vehicleRating.setIsIndicator(true);
                    driverComment.setFocusable(View.NOT_FOCUSABLE);
                    vehicleRating.setFocusable(View.NOT_FOCUSABLE);
                    submit.setEnabled(false);
                }
                getRideReviews.enqueue(new Callback<List<AcumulatedReviewsDTO>>() {
                    @Override
                    public void onResponse(Call<List<AcumulatedReviewsDTO>> call, Response<List<AcumulatedReviewsDTO>> response) {
                        List<AcumulatedReviewsDTO> reviews=response.body();
                        for (AcumulatedReviewsDTO review:reviews) {
                            if (review.getDriverReview()!=null)
                                if (review.getDriverReview().getPassenger().getId()== TokenManager.getUserId())
                                    rideReview[0] =review.getDriverReview();
                            if (review.getVehicleReview()!=null)
                                if (review.getVehicleReview().getPassenger().getId()==TokenManager.getUserId())
                                    vehicleReview[0]=review.getVehicleReview();
                        }
                        if (rideReview[0]!=null){
                            driverRating.setRating(rideReview[0].getRating());
                            driverComment.setText(rideReview[0].getComment());
                            driverRating.setIsIndicator(true);
                            driverComment.setFocusable(View.NOT_FOCUSABLE);
                        }
                        else if (rideReview[0]!=null && diff>3){
                            driverComment.setText("Time for reviewing ride has expired!");
                        }
                        if (vehicleReview[0]!=null){
                            vehicleRating.setRating(vehicleReview[0].getRating());
                            vehicleComment.setText(vehicleReview[0].getComment());
                            vehicleComment.setFocusable(View.NOT_FOCUSABLE);
                            vehicleRating.setIsIndicator(true);
                        }
                        else if (vehicleReview[0]!=null && diff>3){
                            vehicleComment.setText("Time for reviewing vehicle has expired!");
                        }
                        if (vehicleReview[0]!=null && rideReview[0]!=null)
                            submit.setEnabled(false);
                    }

                    @Override
                    public void onFailure(Call<List<AcumulatedReviewsDTO>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<RideDetailedDTO> call, Throwable t) {

            }
        });
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitReview:
                if (driverRating.getRating()>0 && !driverRating.isIndicator()){
                    Call<ReviewResponseDTO> sendDriverReview=reviewService.createRideReview(new ReviewRequestDTO((int) driverRating.getRating(),driverComment.getText().toString()),rideID);
                    sendDriverReview.enqueue(new Callback<ReviewResponseDTO>() {
                        @Override
                        public void onResponse(Call<ReviewResponseDTO> call, Response<ReviewResponseDTO> response) {
                            Toast.makeText(getContext(), "Ride Review Submitted", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ReviewResponseDTO> call, Throwable t) {
                            Toast.makeText(getContext(), "Ups, something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (vehicleRating.getRating()>0 && !vehicleRating.isIndicator()){
                    Call<ReviewResponseDTO> sendVehicleReview=reviewService.createVehicleReview(new ReviewRequestDTO((int) vehicleRating.getRating(),vehicleComment.getText().toString()),rideID);
                    sendVehicleReview.enqueue(new Callback<ReviewResponseDTO>() {
                        @Override
                        public void onResponse(Call<ReviewResponseDTO> call, Response<ReviewResponseDTO> response) {
                            Toast.makeText(getContext(), "Vehicle Review Submitted", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ReviewResponseDTO> call, Throwable t) {
                            Toast.makeText(getContext(), "Ups, something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                dismiss();
                break;
            case R.id.cancelReview:
                dismiss();
                break;
        }

    }

}
