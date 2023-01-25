package com.example.uberapp.gui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.FavoritePathDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.RideService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToFavoritesDialog extends Dialog{
    public Activity c;
    public FavoritePathDTO favoritePathDTO;
    private Button add;
    private TextInputEditText name;
    RideService rideService = APIClient.getClient().create(RideService.class);

    public AddToFavoritesDialog(Activity a, FavoritePathDTO favoritePathDTO) {
        super(a);
        this.c = a;
        this.favoritePathDTO=favoritePathDTO;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_add_favorite_path);
        add =findViewById(R.id.createFavoriteBtn);
        name=findViewById(R.id.nameFavoriteDialogEditText);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString()!=""){
                    favoritePathDTO.setFavoriteName(name.getText().toString());
                    Call<FavoritePathDTO> addFavoritePath=rideService.addFavoritePath(favoritePathDTO);
                    addFavoritePath.enqueue(new Callback<FavoritePathDTO>() {
                        @Override
                        public void onResponse(Call<FavoritePathDTO> call, Response<FavoritePathDTO> response) {
                            Toast.makeText(getContext(), "Favorite path added!", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<FavoritePathDTO> call, Throwable t) {
                            Toast.makeText(getContext(), "Ups, something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
                else
                    Toast.makeText(getContext(), "Name cannot be blank!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
