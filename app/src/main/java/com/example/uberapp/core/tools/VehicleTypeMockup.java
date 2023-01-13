package com.example.uberapp.core.tools;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.example.uberapp.core.model.VehicleType;
import com.example.uberapp.core.model.VehicleTypePrice;

import java.util.ArrayList;
import java.util.List;

public class VehicleTypeMockup {
    public static List<VehicleTypePrice> getVehicleTypes(){
        List<VehicleTypePrice> vehicleTypes = new ArrayList<>();

        //Bitmap icon1 = BitmapFactory.decodeFile("./../../../../../../res/drawable/car_model_01.png");
        VehicleTypePrice vt1 = new VehicleTypePrice();

        VehicleTypePrice vt2 = new VehicleTypePrice();

        VehicleTypePrice vt3 = new VehicleTypePrice();

        vehicleTypes.add(vt1);
        vehicleTypes.add(vt2);
        vehicleTypes.add(vt3);

        return vehicleTypes;
    }
}
