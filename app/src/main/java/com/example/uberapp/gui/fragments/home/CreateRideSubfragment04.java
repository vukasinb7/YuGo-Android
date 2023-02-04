package com.example.uberapp.gui.fragments.home;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.FavoritePathDTO;
import com.example.uberapp.core.dto.LocationDTO;
import com.example.uberapp.core.dto.PathDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.dto.UserSimpleDTO;
import com.example.uberapp.core.dto.UserSimplifiedDTO;
import com.example.uberapp.core.model.LocationInfo;
import com.example.uberapp.core.model.User;
import com.example.uberapp.core.model.VehicleType;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.UserService;
import com.example.uberapp.gui.dialogs.AddToFavoritesDialog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRideSubfragment04 extends Fragment {

    VehicleType vehicleType;
    LocalDateTime rideDateTime;
    LocationInfo departure;
    LocationInfo destination;
    double totalPrice;
    boolean isBabyTransport;
    boolean isPetTransport;
    UserService userService = APIClient.getClient().create(UserService .class);

    public CreateRideSubfragment04() {
        // Required empty public constructor
    }

    public interface OnAcceptRideListener{
        void onAcceptRide();
    }
    OnAcceptRideListener acceptRideListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        acceptRideListener = (OnAcceptRideListener) getParentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ride_subfragment04, container, false);
        ImageButton button = view.findViewById(R.id.buttonReturnBack);
        final Boolean[] clicked = {false};
        Button favourites=view.findViewById(R.id.buttonAddToFavorites);
        CreateRideSheet fragment = (CreateRideSheet) getParentFragment();
        button.setOnClickListener(view12 -> fragment.buttonPrevOnClick());
        favourites.setOnClickListener(view1 -> {
            PathDTO path = new PathDTO();
            LocationDTO departureDTO = new LocationDTO();
            departureDTO.setAddress(departure.getAddress());
            departureDTO.setLatitude(departure.getLatitude());
            departureDTO.setLongitude(departure.getLongitude());
            LocationDTO destinationDTO = new LocationDTO();
            destinationDTO.setAddress(destination.getAddress());
            destinationDTO.setLatitude(destination.getLatitude());
            destinationDTO.setLongitude(destination.getLongitude());
            path.setDeparture(departureDTO);
            path.setDestination(destinationDTO);
            UserSimplifiedDTO user = new UserSimplifiedDTO();
            user.setId(TokenManager.getUserId());
            user.setEmail(TokenManager.getEmail());
            FavoritePathDTO favorite = new FavoritePathDTO("", List.of(path), List.of(user), vehicleType.getVehicleCategory().toString(), isBabyTransport, isPetTransport);
            Dialog dialog = new AddToFavoritesDialog(getActivity(), favorite);
            dialog.show();

        });
        View vehicleTypeCard = view.findViewById(R.id.vehicleTypeFinal);

        TextView vehicleCategoryTextView = vehicleTypeCard.findViewById(R.id.textViewVehicleCategory);
        vehicleCategoryTextView.setText(vehicleType.getVehicleCategory().toString());

        TextView vehicleTypePriceTextView = vehicleTypeCard.findViewById(R.id.textViewPrice);
        vehicleTypePriceTextView.setText("$" + String.valueOf(totalPrice));

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

        Button acceptRideBtn = view.findViewById(R.id.buttonAcceptRide);
        acceptRideBtn.setOnClickListener(v -> acceptRideListener.onAcceptRide());

        return view;
    }
}