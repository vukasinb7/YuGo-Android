package com.example.uberapp.gui.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uberapp.R;
import com.example.uberapp.core.model.LocationInfo;
import com.example.uberapp.core.model.VehicleType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateRideSubfragment04 extends Fragment {

    VehicleType vehicleType;
    LocalDateTime rideDateTime;
    LocationInfo departure;
    LocationInfo destination;
    double totalPrice;
    boolean isBabyTransport;
    boolean isPetTransport;

    public CreateRideSubfragment04() {
        // Required empty public constructor
    }

    public static CreateRideSubfragment04 newInstance() {
        CreateRideSubfragment04 fragment = new CreateRideSubfragment04();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ride_subfragment04, container, false);
        ImageButton button = view.findViewById(R.id.buttonReturnBack);
        final Boolean[] clicked = {false};
        ImageButton favourites=view.findViewById(R.id.addToFavouritesBtn);
        CreateRideFragment fragment = (CreateRideFragment) getParentFragment();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.buttonPrevOnClick();
            }
        });
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clicked[0]) {
                    favourites.setImageResource(R.drawable.icon_star_filled);
                    clicked[0] =true;
                }
                else {
                    favourites.setImageResource(R.drawable.icon_star_outline);
                    clicked[0] =false;
                }

            }
        });
        View vehicleTypeCard = view.findViewById(R.id.vehicleTypeFinal);

        TextView vehicleCategoryTextView = vehicleTypeCard.findViewById(R.id.textViewVehicleCategory);
        vehicleCategoryTextView.setText(vehicleType.getVehicleCategory().toString());

        TextView vehicleTypePriceTextView = vehicleTypeCard.findViewById(R.id.textViewPrice);
        vehicleTypePriceTextView.setText(String.valueOf(totalPrice));

        ImageView vehicleCategoryImageView = vehicleTypeCard.findViewById(R.id.imageViewVehicle);
        vehicleCategoryImageView.setImageBitmap(vehicleType.getIcon());

        CheckBox isBabyTransportCheckBox = view.findViewById(R.id.checkBoxIncludeBabyFinal);
        isBabyTransportCheckBox.setChecked(isBabyTransport);

        CheckBox isPetTransportCheckBox = view.findViewById(R.id.checkBoxIncludePetsFinal);
        isPetTransportCheckBox.setChecked(isPetTransport);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
        TextView rideDateTextView = view.findViewById(R.id.rideDateFinal);
        String rideDate = "Date: " + dateFormatter.format(rideDateTime);
        rideDateTextView.setText(rideDate);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        TextView rideTimeTextView = view.findViewById(R.id.rideTimeFinal);
        String rideTime = "Time: " + timeFormatter.format(rideDateTime) + "h";
        rideTimeTextView.setText(rideTime);

        EditText departureEditText = view.findViewById(R.id.rideDepartureFinal);
        departureEditText.setText(departure.getAddress());
        departureEditText.setKeyListener(null);

        EditText destinationEditText = view.findViewById(R.id.rideDestinationFinal);
        destinationEditText.setText(destination.getAddress());
        destinationEditText.setKeyListener(null);

        TextView totalPriceTextView = view.findViewById(R.id.totalPriceFinal);
        String price = "Total: $" + totalPrice;
        totalPriceTextView.setText(price);


        return view;
    }
}