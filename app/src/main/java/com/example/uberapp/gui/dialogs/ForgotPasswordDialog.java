package com.example.uberapp.gui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.uberapp.R;

public class ForgotPasswordDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    public Button yes;

    public ForgotPasswordDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.item_forgot_password);
        yes = (Button) findViewById(R.id.sendForgotPasswordButton);
        yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}