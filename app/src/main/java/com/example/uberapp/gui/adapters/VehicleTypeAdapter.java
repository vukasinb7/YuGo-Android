package com.example.uberapp.gui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uberapp.R;
import com.example.uberapp.core.model.VehicleType;
import com.example.uberapp.core.tools.VehicleTypeMockup;

public class VehicleTypeAdapter extends BaseAdapter {

    public Activity activity;
    public VehicleTypeAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return VehicleTypeMockup.getVehicleTypes().size();
    }

    @Override
    public Object getItem(int i) {
        return VehicleTypeMockup.getVehicleTypes().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VehicleType vht = VehicleTypeMockup.getVehicleTypes().get(i);
        View v = view;

        if(view == null){
            v = LayoutInflater.from(activity).inflate(R.layout.vehicle_type_card, null);
        }

        ImageView icon = (ImageView) v.findViewById(R.id.imageViewVehicle);
        TextView category = (TextView) v.findViewById(R.id.textViewVehicleCategory);
        TextView estTime = (TextView) v.findViewById(R.id.textViewEstimatedTimeOfRideEnd);
        TextView personCount = (TextView) v.findViewById(R.id.textViewPersonCount);
        TextView price = (TextView) v.findViewById(R.id.textViewPrice);

        icon.setImageResource(vht.getIcon());
        category.setText(vht.getVehicleCategory().toString());
        estTime.setText("2:03pm");
        personCount.setText("3");
        price.setText("$9.99");

        return v;
    }
}
