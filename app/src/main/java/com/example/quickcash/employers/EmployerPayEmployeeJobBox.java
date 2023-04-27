package com.example.quickcash.employers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.quickcash.R;

/**
 * This class is the activity for the job management page used by Employers to add a new job
 */
public class EmployerPayEmployeeJobBox extends AppCompatDialogFragment {

    EditText jobName;
    EditText jobDescription;
    EditText jobPaymentAmount;
    EditText jobStatus;
    EditText jobEmployee;
    private  Listener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.employer_paying_job, null);
        builder.setTitle("Pay Employee for Completing Job");
        builder.setView(view);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setPositiveButton("Pay Now", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
                String employee = jobEmployee.getText().toString();
                String payment = jobPaymentAmount.getText().toString();
                listener.getInfo(employee, payment);
            }
        });

        jobName = view.findViewById(R.id.payJobName);
        jobName.setText(getArguments().getString("jobName"));
        jobDescription = view.findViewById(R.id.payJobDesc);
        jobDescription.setText(getArguments().getString("jobDesc"));
        jobPaymentAmount = view.findViewById(R.id.payJobPay);
        jobPaymentAmount.setText("$" + getArguments().getString("jobPay"));
        jobStatus = view.findViewById(R.id.payJobStatus);
        jobStatus.setText(getArguments().getString("jobStatus"));
        jobEmployee = view.findViewById(R.id.payEmployee);
        jobEmployee.setText(getArguments().getString("jobEmployee"));
        return builder.create();
    }


    public interface Listener{
        void getInfo(String employee, String payment);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "implement Listener");
        }
    }

}
