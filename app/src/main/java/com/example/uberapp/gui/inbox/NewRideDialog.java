package com.example.uberapp.gui.inbox;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.uberapp.R;

public class NewRideDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    public Button yes, no;

    public NewRideDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.card_acceptance_ride_item);
        yes = (Button) findViewById(R.id.accept);
        no = (Button) findViewById(R.id.decline);
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