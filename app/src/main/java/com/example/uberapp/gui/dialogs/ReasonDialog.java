package com.example.uberapp.gui.dialogs;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.ReasonDTO;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.RideService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReasonDialog extends Dialog implements android.view.View.OnClickListener {
    private Integer rideID;
    private String type;
    private Button submit,cancel;
    Activity a;
    private TextView reasonTb;
    public static String TAG = "ReasonDialog";
    RideService rideService = APIClient.getClient().create(RideService.class);


    public ReasonDialog(Activity a,Integer rideID, String type){
        super(a);
        this.a=a;
        this.rideID=rideID;
        this.type=type;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);
        setContentView(R.layout.dialog_reason);
        reasonTb=(TextView) findViewById(R.id.reasonReasonDialog);
        reasonTb.onEditorAction(EditorInfo.IME_ACTION_DONE);
        submit= (Button) findViewById(R.id.submitReason);
        cancel= (Button) findViewById(R.id.cancelReason);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitReason:
                ReasonDTO reasonDTO=new ReasonDTO(reasonTb.getText().toString());
                if (type=="REJECTION"){

                    Call<RideDetailedDTO> call = rideService.rejectRide(rideID,reasonDTO);
                    call.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<RideDetailedDTO> call, @NonNull Response<RideDetailedDTO> response) {
                            if (response.code()==200)
                            {
                                Toast.makeText(getContext(), "Ride Rejected!", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                            else if(response.code()==404) {
                                Toast.makeText(getContext(), "Enter reason", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(), "Something went wrong" , Toast.LENGTH_SHORT).show();
                                dismiss();

                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<RideDetailedDTO> call, @NonNull Throwable t) {
                            System.out.println(t.getMessage());
                            Toast.makeText(getContext(), "NESTO PRSAVA!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else{
                    Call<RideDetailedDTO> call = rideService.addPanic(rideID,reasonDTO);
                    call.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<RideDetailedDTO> call, @NonNull Response<RideDetailedDTO> response) {
                            if (response.code() == 204) {
                                dismiss();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<RideDetailedDTO> call, @NonNull Throwable t) {

                        }
                    });
                }

                break;
            case R.id.cancelReason:
                dismiss();
                break;


        }
    }
}
