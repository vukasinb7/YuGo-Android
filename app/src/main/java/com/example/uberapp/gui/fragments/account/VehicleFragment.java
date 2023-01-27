package com.example.uberapp.gui.fragments.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.LocationDTO;
import com.example.uberapp.core.dto.NewVehicleDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.dto.VehicleDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.VehicleService;
import com.example.uberapp.gui.validators.TextValidator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleFragment extends Fragment {
    UserDetailedDTO user;
    DriverService driverService = APIClient.getClient().create(DriverService.class);
    VehicleService vehicleService = APIClient.getClient().create(VehicleService.class);

    TextInputLayout model;
    TextInputLayout licenseNumber;
    MaterialButtonToggleGroup vehicleTypeGroup;
    MaterialButton luxButton;
    MaterialButton standardButton;
    MaterialButton vanButton;
    MaterialCheckBox babyTransport;
    MaterialCheckBox petTransport;
    Slider passengerSeats;
    MaterialButton cancel;
    MaterialButton editVehicle;
    MaterialButton sendVehicleRequest;
    LinearLayout optionsLayout;
    String LICENSE_NUMBER_REGEX= "[A-Z][A-Z][0-9]*[A-Z][A-Z]";
    public VehicleFragment(UserDetailedDTO user) {
        this.user = user;
    }

    public VehicleFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle, container, false);

        model= view.findViewById(R.id.modelTextField);
        licenseNumber = view.findViewById(R.id.licenseNumberTextField);
        vehicleTypeGroup = view.findViewById(R.id.vehicleTypeGroup);
        luxButton = view.findViewById(R.id.luxButton);
        standardButton = view.findViewById(R.id.standardButton);
        vanButton = view.findViewById(R.id.vanButton);
        babyTransport = view.findViewById(R.id.babyTransport);
        petTransport = view.findViewById(R.id.petTransport);
        passengerSeats = view.findViewById(R.id.passengerSeats);
        optionsLayout = view.findViewById(R.id.optionsLayoutVehicle);
        cancel = view.findViewById(R.id.cancelVehicleChange);
        editVehicle = view.findViewById(R.id.editVehicle);
        sendVehicleRequest = view.findViewById(R.id.sendVehicleRequest);

        this.setupFormValidators();
        this.setupButtons();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        this.loadVehicleData();
    }

    public void setupButtons(){
        this.triggerValidations();
        cancel.setOnClickListener(v -> {
            this.disableAndRefreshForm();
        });

        editVehicle.setOnClickListener(v -> {
            editVehicle.setVisibility(View.GONE);
            optionsLayout.setVisibility(View.VISIBLE);
            model.setEnabled(true);
            licenseNumber.setEnabled(true);
            babyTransport.setEnabled(true);
            petTransport.setEnabled(true);
            passengerSeats.setEnabled(true);
            luxButton.setEnabled(true);
            standardButton.setEnabled(true);
            vanButton.setEnabled(true);
        });

        sendVehicleRequest.setOnClickListener(v -> {
            if (!isFormValid()){
                Toast.makeText(getContext(), "Form is incorrect!", Toast.LENGTH_SHORT).show();
                return;
            }

            String vehicleType = "STANDARD";
            if (vehicleTypeGroup.getCheckedButtonId() == R.id.luxButton){
                vehicleType = "LUX";
            }
            else if (vehicleTypeGroup.getCheckedButtonId() == R.id.vanButton){
                vehicleType = "VAN";
            }

            LocationDTO locationDTO = new LocationDTO("Null island", 0, 0);
            NewVehicleDTO newVehicleDTO = new NewVehicleDTO(vehicleType,
                    model.getEditText().getText().toString(), licenseNumber.getEditText().getText().toString(),
                    locationDTO, (int)passengerSeats.getValue(),
                    babyTransport.isChecked(), babyTransport.isChecked());
            Call<ResponseBody> makeVehicleChangeRequestCall = vehicleService.makeVehicleChangeRequest(user.getId(), newVehicleDTO);
            makeVehicleChangeRequestCall.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        Toast.makeText(getContext(), "Request sent successfully!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });

            disableAndRefreshForm();
        });
    }

    public boolean isFormValid(){
        if (model.getError() == null && licenseNumber.getError() == null){
            return true;
        }
        return false;
    }

    public void triggerValidations(){
        model.getEditText().setText(model.getEditText().getText().toString());
        licenseNumber.getEditText().setText(licenseNumber.getEditText().getText().toString());
    }
    public void disableAndRefreshForm(){
        this.loadVehicleData();
        editVehicle.setVisibility(View.VISIBLE);
        optionsLayout.setVisibility(View.GONE);
        model.setEnabled(false);
        licenseNumber.setEnabled(false);
        babyTransport.setEnabled(false);
        petTransport.setEnabled(false);
        passengerSeats.setEnabled(false);
        luxButton.setEnabled(false);
        standardButton.setEnabled(false);
        vanButton.setEnabled(false);
    }

    public void loadVehicleData(){
        Call<VehicleDTO> vehicleDTOCall = driverService.getVehicle(user.getId());
        vehicleDTOCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<VehicleDTO> call, @NonNull Response<VehicleDTO> response) {
                if (response.code() == 200){
                    VehicleDTO vehicleDTO = response.body();
                    model.getEditText().setText(vehicleDTO.getModel());
                    licenseNumber.getEditText().setText(vehicleDTO.getLicenseNumber());
                    if (vehicleDTO.getVehicleType().equals("LUX")){
                        vehicleTypeGroup.check(R.id.luxButton);
                    }
                    else if (vehicleDTO.getVehicleType().equals("STANDARD")){
                        vehicleTypeGroup.check(R.id.standardButton);
                    }
                    else{
                        vehicleTypeGroup.check(R.id.vanButton);
                    }
                    babyTransport.setChecked(vehicleDTO.getBabyTransport());
                    petTransport.setChecked(vehicleDTO.getPetTransport());
                    passengerSeats.setValue(vehicleDTO.getPassengerSeats());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VehicleDTO> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setupFormValidators(){
        model.getEditText().addTextChangedListener(new TextValidator(model.getEditText()){
            @Override
            public void validate(TextView textView, String text) {
                if (model.getEditText().getText().toString().isEmpty()){
                    model.setError("Field is necessary!");
                }
                else{
                    model.setError(null);
                }
            }
        });

        licenseNumber.getEditText().addTextChangedListener(new TextValidator(licenseNumber.getEditText()){
            @Override
            public void validate(TextView textView, String text) {
                if (licenseNumber.getEditText().getText().toString().isEmpty()){
                    licenseNumber.setError("Field is necessary!");
                }
                else if (!licenseNumber.getEditText().getText().toString().matches(LICENSE_NUMBER_REGEX)){
                    licenseNumber.setError("Not a valid license plate!");
                }
                else{
                    licenseNumber.setError(null);
                }
            }
        });
    }
}