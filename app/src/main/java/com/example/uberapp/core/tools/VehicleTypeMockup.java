package com.example.uberapp.core.tools;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.example.uberapp.R;
import com.example.uberapp.core.model.VehicleCategory;
import com.example.uberapp.core.model.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class VehicleTypeMockup {
    public static List<VehicleType> getVehicleTypes(){
        List<VehicleType> vehicleTypes = new ArrayList<>();

        //Bitmap icon1 = BitmapFactory.decodeFile("./../../../../../../res/drawable/car_model_01.png");
        VehicleType vt1 = new VehicleType(1, 5.0, VehicleCategory.STANDARD, null);

        VehicleType vt2 = new VehicleType(2, 7.5, VehicleCategory.LUX, null);

        VehicleType vt3 = new VehicleType(3, 6, VehicleCategory.VAN, null);

        vehicleTypes.add(vt1);
        vehicleTypes.add(vt2);
        vehicleTypes.add(vt3);

        return vehicleTypes;
    }
}
