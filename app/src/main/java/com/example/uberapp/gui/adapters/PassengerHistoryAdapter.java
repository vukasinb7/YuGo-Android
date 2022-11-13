package com.example.uberapp.gui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.uberapp.R;
import com.example.uberapp.core.model.Ride;
import com.example.uberapp.core.tools.DriverHistoryMockup;
import com.example.uberapp.core.tools.PassengerHistoryMockup;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PassengerHistoryAdapter extends BaseAdapter {
    public Activity activity;
    public PassengerHistoryAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return PassengerHistoryMockup.getRides().size();
    }

    @Override
    public Object getItem(int i) {
        return PassengerHistoryMockup.getRides().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private String parseDuration(Duration duration){
        String minute=String.format("%02d",Math.abs(duration.toMinutes()));
        return minute+"min";

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Ride vht = PassengerHistoryMockup.getRides().get(i);
        View v = view;

        if(view == null){
            v = LayoutInflater.from(activity).inflate(R.layout.card_driver_history_list_item, null);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        TextView nameLb = (TextView) v.findViewById(R.id.nameLb);
        TextView startLocation = (TextView) v.findViewById(R.id.startText);
        TextView endLocation = (TextView) v.findViewById(R.id.destText);
        TextView startTime = (TextView) v.findViewById(R.id.startTime);
        TextView endTime = (TextView) v.findViewById(R.id.endTime);
        TextView price = (TextView) v.findViewById(R.id.priceTag);
        TextView distance = (TextView) v.findViewById(R.id.distanceText);
        TextView personNum = (TextView) v.findViewById(R.id.personNum);
        TextView dateTitle = (TextView) v.findViewById(R.id.dateTitleHistory);
        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBarHistory);

        if (i!=0)
            if(vht.getStartTime().toLocalDate().isEqual(DriverHistoryMockup.getRides().get(i-1).getStartTime().toLocalDate()))
                dateTitle.setVisibility(View.GONE);
        Random r= new Random();
        nameLb.setText(vht.getPassengers().get(0).getName()+" "+ vht.getPassengers().get(0).getLastName());
        startLocation.setText("Kralja Petra I 54");
        endLocation.setText("Bulevar Mihajla Pupina 54");
        startTime.setText(vht.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm")));
        endTime.setText(vht.getEndTime().format(DateTimeFormatter.ofPattern("hh:mm")));
        price.setText("$"+String.format("%.2f",vht.getTotalPrice()));
        distance.setText(String.format("%.2f",(1 + (10 - 1) * r.nextDouble()))+"km");
        dateTitle.setText(vht.getStartTime().format(formatter));
        personNum.setText(Integer.toString(1 + (int)(Math.random() * ((5 - 1) + 1))));
        ratingBar.setRating(1 + (int)(Math.random() * ((5 - 1) + 1)));

        CardView cardView = v.findViewById(R.id.cardViewDriverHistory);
        ImageButton arrow = v.findViewById(R.id.arrowBtnDriverHistory);
        RelativeLayout hiddenView = v.findViewById(R.id.hiddenDriverHistory);

        arrow.setOnClickListener(view_click -> {
            // If the CardView is already expanded, set its visibility
            // to gone and change the expand less icon to expand more.
            if (hiddenView.getVisibility() == View.VISIBLE) {
                // The transition of the hiddenView is carried out by the TransitionManager class.
                // Here we use an object of the AutoTransition Class to create a default transition
                //TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.arrow_down);
            }

            // If the CardView is not expanded, set its visibility to
            // visible and change the expand more icon to expand less.
            else {
                //TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.arrow_up);
            }
        });


        return v;
    }
}
