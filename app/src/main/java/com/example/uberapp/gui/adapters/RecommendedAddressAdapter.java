package com.example.uberapp.gui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uberapp.R;
import com.example.uberapp.core.model.LocationInfo;
import com.example.uberapp.core.model.VehicleType;

import java.util.List;

public class RecommendedAddressAdapter extends BaseAdapter {
    public Activity activity;
    private List<LocationInfo> addresses;
    public RecommendedAddressAdapter(Activity activity, List<LocationInfo> addresses){
        this.activity = activity;
        this.addresses = addresses;
    }

    public void changeDataSet(List<LocationInfo> data){
        this.addresses = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public Object getItem(int position) {
        return addresses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocationInfo address = addresses.get(position);
        View view = convertView;

        if(convertView == null){
            view = LayoutInflater.from(activity).inflate(R.layout.recommended_address_card, null);
        }

        TextView addressNameTextView = view.findViewById(R.id.addressName);
        addressNameTextView.setText(address.getAddress());

        return view;
    }
}
