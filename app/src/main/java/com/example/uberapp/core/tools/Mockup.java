package com.example.uberapp.core.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.uberapp.core.model.VehicleCategory;
import com.example.uberapp.core.model.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class Mockup {
    public static List<VehicleType> getVehicleTypes(){
        List<VehicleType> vehicleTypes = new ArrayList<>();

        Bitmap icon1 = BitmapFactory.decodeFile("https://github.com/vukasinb7/UberApp/blob/5d17a2ef3d3fcb9315bc11ea12e3c4268b85ec96/app/src/main/res/drawable/car_model.png");
        VehicleType vt1 = new VehicleType("001", 5.0, VehicleCategory.Standard, icon1);

        vehicleTypes.add(vt1);
        return vehicleTypes;
    }
}
