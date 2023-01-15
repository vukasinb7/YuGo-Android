package com.example.uberapp.gui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.ReasonDTO;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.RideService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReasonDialog extends DialogFragment implements android.view.View.OnClickListener {
    private Integer rideID;
    private String type;
    private Button submit;
    private TextView reasonTb;
    public static String TAG = "ReasonDialog";
    RideService rideService = APIClient.getClient().create(RideService.class);


    public ReasonDialog(Integer rideID,String type){
        this.rideID=rideID;
        this.type=type;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_reason, null, false);
        reasonTb=(TextView) view.findViewById(R.id.reasonReasonDialog);
        submit= (Button) view.findViewById(R.id.submitReason);
        submit.setOnClickListener(this);
        builder.setView(view);
        return builder.create();
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
                            if (response.code() == 200) {
                                dismiss();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<RideDetailedDTO> call, @NonNull Throwable t) {

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

        }
    }
}
