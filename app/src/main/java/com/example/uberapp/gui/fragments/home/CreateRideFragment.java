package com.example.uberapp.gui.fragments.home;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.uberapp.R;
import com.example.uberapp.core.LocalSettings;
import com.example.uberapp.core.dto.LocationDTO;
import com.example.uberapp.core.dto.PathDTO;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.dto.RideRequestDTO;
import com.example.uberapp.core.dto.UserSimpleDTO;
import com.example.uberapp.core.dto.VehicleTypeDTO;
import com.example.uberapp.core.model.LocationInfo;
import com.example.uberapp.core.model.VehicleCategory;
import com.example.uberapp.core.model.VehicleType;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.ImageService;
import com.example.uberapp.core.services.RideService;
import com.example.uberapp.core.services.VehicleTypeService;
import com.example.uberapp.core.auth.TokenManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;


public class CreateRideFragment extends DialogFragment implements
        CreateRideSubfragment01.OnRouteChangedListener,
        CreateRideSubfragment02.OnRidePropertiesChangedListener,
        CreateRideSubfragment03.OnDateTimeChangedListener,
        CreateRideSubfragment04.OnAcceptRideListener {

    private int currentSubfragment;
    private CreateRideSubfragment01 subFrag01;
    private CreateRideSubfragment02 subFrag02;
    private CreateRideSubfragment03 subFrag03;
    private CreateRideSubfragment04 subFrag04;
    private CreateRideLoader createRideLoader;

    private FloatingActionButton buttonNext;
    private FloatingActionButton buttonPrev;

    private VehicleTypeService vehicleTypeService;
    private ImageService imageService;
    private RideService rideService;

    private LocationInfo destination;
    private LocationInfo departure;
    private boolean isPetTransport;
    private boolean isBabyTransport;
    private VehicleType vehicleType;
    private LocalDateTime dateTime;
    private double totalPrice;
    private Road road;

    private StompClient mStompClient;

    public static String TAG = "CreateRideFragmentDialog";

    public CreateRideFragment() {
        // Required empty public constructor
        super(R.layout.fragment_create_ride);
        currentSubfragment = 0;
    }


    public void buttonPrevOnClick(){
        switch (currentSubfragment){
            case 1:
                getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, subFrag01).commit();
                buttonPrev.setVisibility(View.GONE);
                break;
            case 2:
                getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, subFrag02).commit();
                break;
            case 3:
                getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, subFrag03).commit();
                buttonPrev.setVisibility(View.VISIBLE);
                buttonNext.setVisibility(View.VISIBLE);
                break;
        }
        currentSubfragment--;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vehicleTypeService = APIClient.getClient().create(VehicleTypeService.class);
        imageService = APIClient.getClient().create(ImageService.class);
        rideService = APIClient.getClient().create(RideService.class);
        this.subFrag01 = new CreateRideSubfragment01();
        this.subFrag02 = new CreateRideSubfragment02();
        this.subFrag03 = new CreateRideSubfragment03();
        this.subFrag04 = new CreateRideSubfragment04();
        this.createRideLoader = new CreateRideLoader();
        Single<List<VehicleType>> result = vehicleTypeService.getVehicleTypes()
                .flatMapIterable(vehicleTypeDTOS -> vehicleTypeDTOS)
                .flatMap(vehicleTypeDTO -> fetchImage(vehicleTypeDTO).subscribeOn(Schedulers.io())).toList();
        result.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(vehicleTypes -> {
                    subFrag02.vehicleTypes = vehicleTypes;
                });

        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://" + LocalSettings.localIP + ":9000/api/socket/websocket");

        mStompClient.topic("/ride-topic/notify-passenger/"+ TokenManager.getUserId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(topicMessage -> {
                    Gson gson= new Gson();
                    JsonObject jsonObject=gson.fromJson(topicMessage.getPayload(), JsonObject.class);
                    Integer rideID=jsonObject.getAsJsonPrimitive("rideID").getAsInt();
                    rideService.getRide(rideID).enqueue(new Callback<RideDetailedDTO>() {
                        @Override
                        public void onResponse(Call<RideDetailedDTO> call, Response<RideDetailedDTO> response) {
                            createRideLoader.changeLoadingStatus(response.body());
                        }

                        @Override
                        public void onFailure(Call<RideDetailedDTO> call, Throwable t) {

                        }
                    });
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        System.out.println(throwable.getMessage());
                    }
                });
        mStompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {

                case OPENED:
                    System.out.println("OPENED");
                    break;

                case ERROR:
                    System.out.println(lifecycleEvent.getException().getMessage());
                    break;

                case CLOSED:
                    System.out.println("CLOSED");
                    break;
            }
        });

        mStompClient.connect();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_ride, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        buttonNext = view.findViewById(R.id.nextSubfragmentButton);
        buttonNext.setEnabled(false);
        buttonPrev = view.findViewById(R.id.previouosSubfragmentButton);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentSubfragment){
                    case 0:
                        buttonNext.setEnabled(false);
                        getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, subFrag02).commit();
                        buttonPrev.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, subFrag03).commit();
                        break;
                    case 2:
                        getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, subFrag04).commit();
                        buttonNext.setVisibility(View.GONE);
                        buttonPrev.setVisibility(View.GONE);
                        break;
                }
                currentSubfragment++;
            }
        });


        buttonPrev.setVisibility(View.GONE);
        buttonPrev.setOnClickListener(view1 -> buttonPrevOnClick());
        getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, new CreateRideSubfragment01()).commit();
        return view;

    }

    @Override
    public void onRideRouteChanged(LocationInfo departure, LocationInfo destination) {
        this.departure = departure;
        this.destination = destination;
        this.buttonNext.setEnabled(departure != null && destination != null);
        if(departure != null && destination != null){
            subFrag04.departure = departure;
            subFrag04.destination = destination;
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                RoadManager roadManager = new OSRMRoadManager(getContext(), "Pera");
                ArrayList<GeoPoint> track = new ArrayList<>();
                GeoPoint startPoint = new GeoPoint(departure.getLatitude(), departure.getLongitude());
                GeoPoint endPoint = new GeoPoint(destination.getLatitude(), destination.getLongitude());
                track.add(startPoint);
                track.add(endPoint);
                Road rd = roadManager.getRoad(track);
                getActivity().runOnUiThread(() -> {
                    road = rd;
                    subFrag02.setRoad(rd);
                });
            });
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mStompClient.disconnect();

    }
    @Override
    public void onVehicleTypeChanged(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        if(vehicleType != null){
            buttonNext.setEnabled(true);
            totalPrice = Math.round(vehicleType.getPricePerUnit() * road.mLength * 100) / 100.0;
            subFrag04.totalPrice = totalPrice;
            subFrag04.vehicleType = vehicleType;
        }
    }

    @Override
    public void onPetTransportChanged(boolean isPetTransport) {
        this.isPetTransport = isPetTransport;
        subFrag04.isPetTransport = isPetTransport;
    }

    @Override
    public void onBabyTransportChanged(boolean isBabyTransport) {
        this.isBabyTransport = isBabyTransport;
        subFrag04.isBabyTransport = isBabyTransport;
    }

    @Override
    public void onDateTimeChanged(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        subFrag04.rideDateTime = dateTime;
    }

    @Override
    public void onAcceptRide() {
        RideRequestDTO ride = new RideRequestDTO();
        ride.babyTransport = isBabyTransport;
        ride.dateTime = dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        ride.petTransport = isPetTransport;
        ride.vehicleType = vehicleType.getVehicleCategory().toString();
        List<PathDTO> locations = new ArrayList<>();
        PathDTO path = new PathDTO();
        path.setDeparture(new LocationDTO(departure));
        path.setDestination(new LocationDTO(destination));
        locations.add(path);
        ride.locations = locations;
        List<UserSimpleDTO> passengers = new ArrayList<>();
        UserSimpleDTO passenger = new UserSimpleDTO();
        passenger.id = TokenManager.getUserId();
        passenger.email = "NA";
        passengers.add(passenger);
        ride.passengers = passengers;
        rideService.createRide(ride).enqueue(new Callback<RideDetailedDTO>() {
            @Override
            public void onResponse(Call<RideDetailedDTO> call, Response<RideDetailedDTO> response) {
                createRideLoader.changeLoadingStatus(response.body());
            }

            @Override
            public void onFailure(Call<RideDetailedDTO> call, Throwable t) {

            }
        });
        getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, createRideLoader).commit();
    }
}