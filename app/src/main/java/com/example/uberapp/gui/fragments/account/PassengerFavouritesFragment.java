package com.example.uberapp.gui.fragments.account;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.FavoritePathDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.RideService;
import com.example.uberapp.gui.adapters.FavoritePathAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerFavouritesFragment extends Fragment {


    RideService rideService = APIClient.getClient().create(RideService.class);
    public PassengerFavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_passenger_favourites, container, false);
        ListView listView=(ListView) v.findViewById(R.id.favoritesList);
        Call<List<FavoritePathDTO>> getFavorites=rideService.getFavoritePath();
        getFavorites.enqueue(new Callback<List<FavoritePathDTO>>() {
            @Override
            public void onResponse(Call<List<FavoritePathDTO>> call, Response<List<FavoritePathDTO>> response) {
                List<FavoritePathDTO> favorites=response.body();
                FavoritePathAdapter favoritePathAdapter= new FavoritePathAdapter(((Activity)v.getContext()),favorites);
                listView.setAdapter(favoritePathAdapter);


            }

            @Override
            public void onFailure(Call<List<FavoritePathDTO>> call, Throwable t) {
                Toast.makeText(v.getContext(), "Ups, something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}