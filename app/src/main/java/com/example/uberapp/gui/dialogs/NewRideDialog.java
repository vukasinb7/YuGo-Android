package com.example.uberapp.gui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.uberapp.R;

public class NewRideDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    public Button yes, no;
    TextView price,distance,numOfPerson,startLocation,endLocation;
    public Integer rideID;

    public NewRideDialog(Activity a, Integer rideID) {
        super(a);
        this.c = a;
        this.rideID=rideID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.list_item_acceptance_ride);
        yes = (Button) findViewById(R.id.accept);
        no = (Button) findViewById(R.id.decline);
        price =(TextView) findViewById(R.id.priceRideOffer);
        distance=(TextView) findViewById(R.id.remainingDistRideOffer);
        numOfPerson=(TextView) findViewById(R.id.personNumRideOffer);
        startLocation=(TextView) findViewById(R.id.startDestRideOffer);
        endLocation=(TextView) findViewById(R.id.endDestRideOffer);
        
        price.setText("AAA");
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.accept:
                dismiss();
                break;
            case R.id.decline:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}