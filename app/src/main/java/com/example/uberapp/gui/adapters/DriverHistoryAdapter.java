package com.example.uberapp.gui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uberapp.R;
import com.example.uberapp.core.model.Ride;
import com.example.uberapp.core.model.VehicleType;
import com.example.uberapp.core.tools.DriverHistoryMockup;
import com.example.uberapp.core.tools.Mockup;

public class DriverHistoryAdapter extends BaseAdapter {
    public Activity activity;
    public DriverHistoryAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return DriverHistoryMockup.getRides().size();
    }

    @Override
    public Object getItem(int i) {
        return DriverHistoryMockup.getRides().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Ride vht = DriverHistoryMockup.getRides().get(i);
        View v = view;

        if(view == null){
            v = LayoutInflater.from(activity).inflate(R.layout.card_driver_history_list_item, null);
        }

        TextView nameLb = (TextView) v.findViewById(R.id.nameLb);
        TextView startLocation = (TextView) v.findViewById(R.id.startText);
        TextView endLocation = (TextView) v.findViewById(R.id.destText);
        nameLb.setText(vht.getPassengers().get(0).getName());

        /*TextView category = (TextView) v.findViewById(R.id.textViewVehicleCategory);
        TextView estTime = (TextView) v.findViewById(R.id.textViewEstimatedTimeOfRideEnd);
        TextView personCount = (TextView) v.findViewById(R.id.textViewPersonCount);
        TextView price = (TextView) v.findViewById(R.id.textViewPrice);

        icon.setImageResource(vht.getIcon());
        category.setText(vht.getVehicleCategory().toString());
        estTime.setText("2:03pm");
        personCount.setText("3");
        price.setText("$9.99");*/

        return v;
    }
}
