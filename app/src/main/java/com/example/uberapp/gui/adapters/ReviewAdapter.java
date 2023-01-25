package com.example.uberapp.gui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.ReviewResponseDTO;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.PassengerService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewAdapter extends BaseAdapter {
    public List<ReviewResponseDTO> reviews;
    public Activity activity;
    PassengerService passengerService = APIClient.getClient().create(PassengerService.class);

    public ReviewAdapter(Activity activity,List<ReviewResponseDTO> reviews){
        this.activity=activity;
        this.reviews=reviews;

    }
    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ReviewResponseDTO review = reviews.get(i);
        View v = view;
        if(view == null) {
            v = LayoutInflater.from(activity).inflate(R.layout.list_item_review, null);
        }
        TextView title=(TextView) v.findViewById(R.id.li_titleReview);
        RatingBar ratingBar=(RatingBar) v.findViewById(R.id.li_ratingReview);
        TextView userCredentials=(TextView) v.findViewById(R.id.li_person);
        TextView comment=(TextView) v.findViewById(R.id.li_inputComment);
        Call<UserDetailedDTO> getPassengerDetails=passengerService.getPassenger(review.getPassenger().getId());
        View finalV = v;
        getPassengerDetails.enqueue(new Callback<UserDetailedDTO>() {
            @Override
            public void onResponse(Call<UserDetailedDTO> call, Response<UserDetailedDTO> response) {
                UserDetailedDTO user=response.body();
                if (review.getType().equals("VEHICLE"))
                    title.setText("Vehicle Review");
                else
                    title.setText("Ride Review");
                ratingBar.setRating(review.getRating());
                comment.setText(review.getComment());
                userCredentials.setText("By "+ user.getName()+" "+user.getSurname());
            }

            @Override
            public void onFailure(Call<UserDetailedDTO> call, Throwable t) {
                Toast.makeText(finalV.getContext(), "Ups, something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
