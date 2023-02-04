package com.example.uberapp.gui.fragments.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.StatisticsDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.PassengerService;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverStatisticsFragment extends Fragment{
    DriverService driverService = APIClient.getClient().create(DriverService.class);

    public DriverStatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_driver_statistics, container, false);
        DecimalFormat format = new DecimalFormat("0.#");
        TextView income=(TextView) v.findViewById(R.id.incomeSumLb);
        TextView rating=(TextView) v.findViewById(R.id.ratingCountLb);
        TextView workHours=(TextView) v.findViewById(R.id.dayCountLb);
        TextView rides=(TextView) v.findViewById(R.id.ridesCountLb);
        TextView distance=(TextView) v.findViewById(R.id.acceptedCountLb);
        TextView passengers=(TextView) v.findViewById(R.id.rejectedCountLb);
        Call<StatisticsDTO> getDriverStatistics= driverService.getDriverStatistics(TokenManager.getUserId());
        getDriverStatistics.enqueue(new Callback<StatisticsDTO>() {
            @Override
            public void onResponse(Call<StatisticsDTO> call, Response<StatisticsDTO> response) {
                StatisticsDTO statistics=response.body();
                String incomeText="$"+format.format(statistics.getTotalIncome());
                if (statistics.getTotalIncome()>=1000){
                    incomeText="$"+format.format(Math.round(statistics.getTotalIncome()/100.0)/10.0)+"K";
                }
                income.setText(incomeText);

                String ridesText=format.format(statistics.getTotalRides());
                if (statistics.getTotalRides()>=1000){
                    ridesText= format.format(Math.round(statistics.getTotalRides() / 100.0) / 10.0) +"K";
                }
                rides.setText(ridesText);

                rating.setText(statistics.getAverageRating()+"/5");

                String workHourText=format.format(statistics.getTotalWorkHours());
                if (statistics.getTotalWorkHours()>=1000){
                    workHourText= format.format(Math.round(statistics.getTotalWorkHours() / 100.0) / 10.0) +"K";
                }
                workHours.setText(workHourText);

                String totalDistanceText=format.format(statistics.getTotalDistance())+"km";
                if (statistics.getTotalDistance()>=1000){
                    totalDistanceText= format.format(Math.round(statistics.getTotalDistance() / 100.0) / 10.0) +"K km";
                }
                distance.setText(totalDistanceText);

                String totalPassengersText=format.format(statistics.getTotalPassengers());
                if (statistics.getTotalPassengers()>=1000){
                    totalPassengersText= format.format(Math.round(statistics.getTotalPassengers() / 100.0) / 10.0) +"K";
                }
                passengers.setText(totalPassengersText);

            }

            @Override
            public void onFailure(Call<StatisticsDTO> call, Throwable t) {

            }
        });

        return v;
    }
}