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
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.UserService;
import com.example.uberapp.gui.validators.TextValidator;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    public Button yes;
    public TextInputLayout email;
    UserService userService = APIClient.getClient().create(UserService.class);

    public ForgotPasswordDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_forgot_password);
        yes = (Button) findViewById(R.id.sendForgotPasswordButton);
        email=findViewById(R.id.emailForgotPassword);
        email.getEditText().addTextChangedListener(new TextValidator(email.getEditText()){
            @Override
            public void validate(TextView textView, String text) {
                if (email.getEditText().getText().toString().isEmpty()){
                    email.setError("Field is necessary!");
                }
                else if (!email.getEditText().getText().toString().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
                    email.setError("Not a valid email!");
                }
                else{
                    email.setError(null);
                }
            }
        });
        yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        userService.sendResetCode(email.getEditText().getText().toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code()==204){
                    dismiss();
                    Toast.makeText(getContext(), "Email sent", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getContext(), "Email is not valid!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }
}