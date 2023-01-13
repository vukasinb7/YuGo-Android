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
import com.example.uberapp.core.model.VehicleTypePrice;
import com.example.uberapp.core.tools.VehicleTypeMockup;

import java.util.List;

public class VehicleTypeAdapter extends BaseAdapter {

    public Activity activity;
    private List<VehicleTypePrice> data;
    public VehicleTypeAdapter(Activity activity, List<VehicleTypePrice> data){
        this.activity = activity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return VehicleTypeMockup.getVehicleTypes().size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VehicleTypePrice vht = data.get(i);
        View v = view;

        if(view == null){
            v = LayoutInflater.from(activity).inflate(R.layout.list_item_vehicle_type, null);
        }

        ImageView icon = (ImageView) v.findViewById(R.id.imageViewVehicle);
        TextView category = (TextView) v.findViewById(R.id.textViewVehicleCategory);
        TextView price = (TextView) v.findViewById(R.id.textViewPrice);

        category.setText(vht.getVehicleType().toString());
        price.setText("$9.99");
        //icon.setImageBitmap(vht.getIcon());

        return v;
    }
}
