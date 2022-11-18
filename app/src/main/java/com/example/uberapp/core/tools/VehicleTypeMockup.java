package com.example.uberapp.core.tools;

import com.example.uberapp.R;
import com.example.uberapp.core.model.VehicleCategory;
import com.example.uberapp.core.model.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class VehicleTypeMockup {
    public static List<VehicleType> getVehicleTypes(){
        List<VehicleType> vehicleTypes = new ArrayList<>();

        //Bitmap icon1 = BitmapFactory.decodeFile("./../../../../../../res/drawable/image_car_model_01.png");
        VehicleType vt1 = new VehicleType("001", 5.0, VehicleCategory.Standard, R.drawable.image_car_model_01);

        VehicleType vt2 = new VehicleType("002", 7.5, VehicleCategory.Lux, R.drawable.image_car_model_02);

        VehicleType vt3 = new VehicleType("003", 6, VehicleCategory.Van, R.drawable.image_car_model_03);

        vehicleTypes.add(vt1);
        vehicleTypes.add(vt2);
        vehicleTypes.add(vt3);

        return vehicleTypes;
    }
}
