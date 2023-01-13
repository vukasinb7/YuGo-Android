package com.example.uberapp.gui.fragments.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.VehicleTypeDTO;
import com.example.uberapp.core.model.VehicleCategory;
import com.example.uberapp.core.model.VehicleType;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.ImageService;
import com.example.uberapp.core.services.VehicleTypeService;
import com.example.uberapp.gui.adapters.VehicleTypeAdapter;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class CreateRideSubfragment02 extends Fragment {

    VehicleTypeService vehicleTypeService;
    ImageService imageService;
    ListView listView;
    VehicleTypeAdapter adapter;
    View view;
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
                    byte[] bytes = responseBody.bytes();
                    responseBody.close();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    vehicleType.setIcon(bitmap);
                    vehicleType.setVehicleCategory(VehicleCategory.valueOf(vehicleTypeDTO.vehicleType));
                    vehicleType.setPricePerUnit(vehicleTypeDTO.pricePerKm);
                    vehicleType.setId(vehicleTypeDTO.id);
                    return Observable.just(vehicleType);
                });
    }
    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_ride_subfragment02, container, false);
        listView = view.findViewById(R.id.listViewVehicleType);
        listView.setOnItemClickListener((parent, v, position, id) -> {
            for(int i = 0; i < parent.getCount(); i++){
                parent.getChildAt(i).findViewById(R.id.vehicleTypeCardBackgroundHolder).setBackgroundResource(0);
            }
            v.findViewById(R.id.vehicleTypeCardBackgroundHolder).setBackgroundResource(R.drawable.vehicle_type_card_shape_selected);
        });

        Single<List<VehicleType>> result = vehicleTypeService.getVehicleTypes()
                .flatMapIterable(vehicleTypeDTOS -> vehicleTypeDTOS)
                .flatMap(vehicleTypeDTO -> fetchImage(vehicleTypeDTO).subscribeOn(Schedulers.io())).toList();
        result.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(vehicleTypes -> {
                    adapter = new VehicleTypeAdapter((Activity) getContext(), vehicleTypes);
                    listView.setAdapter(adapter);
                });

        return view;
    }
}