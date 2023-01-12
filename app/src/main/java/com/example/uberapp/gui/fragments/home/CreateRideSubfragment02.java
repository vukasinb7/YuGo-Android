package com.example.uberapp.gui.fragments.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.VehicleTypeDTO;
import com.example.uberapp.core.model.VehicleCategory;
import com.example.uberapp.core.model.VehicleType;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.ImageService;
import com.example.uberapp.core.services.VehicleTypeService;
import com.example.uberapp.gui.adapters.VehicleTypeAdapter;

import java.util.List;

import io.reactivex.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;


public class CreateRideSubfragment02 extends Fragment {

    VehicleTypeService vehicleTypeService;
    ImageService imageService;
    ListView listView;

    public static CreateRideSubfragment02 newInstance() {
        CreateRideSubfragment02 fragment = new CreateRideSubfragment02();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vehicleTypeService = APIClient.getClient().create(VehicleTypeService.class);
        imageService = APIClient.getClient().create(ImageService.class);
    }
    private Observable<VehicleType> fetchImage(VehicleTypeDTO vehicleTypeDTO){
        return this.imageService.getImage(vehicleTypeDTO.imgPath)
                .flatMap(responseBody ->{
                    VehicleType vehicleType = new VehicleType();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(responseBody.bytes(), 0, responseBody.bytes().length);
                    vehicleType.setIcon(bitmap);
                    vehicleType.setVehicleCategory(VehicleCategory.valueOf(vehicleTypeDTO.vehicleType));
                    vehicleType.setPricePerUnit(vehicleTypeDTO.pricePerKm);
                    vehicleType.setId(vehicleTypeDTO.id);
                    return Observable.just(vehicleType);
                }).subscribeOn(AndroidSchedulers.mainThread());
    }
    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ride_subfragment02, container, false);
        listView = view.findViewById(R.id.listViewVehicleType);

         vehicleTypeService.getVehicleTypes()
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap(vehicleTypeDTOs -> Observable.fromIterable(vehicleTypeDTOs)
                        .flatMap(this::fetchImage))
                .observeOn(AndroidSchedulers.mainThread())
                .toList().subscribe(new BiConsumer<List<VehicleType>, Throwable>() {
                    @Override
                    public void accept(List<VehicleType> vehicleTypes, Throwable throwable) throws Exception {
                        VehicleTypeAdapter adapter = new VehicleTypeAdapter((Activity) getContext(), vehicleTypes);
                        listView.setAdapter(adapter);
                    }
                });

        return view;
    }
}