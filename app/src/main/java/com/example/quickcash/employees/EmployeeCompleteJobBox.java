package com.example.quickcash.employees;


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
 * This class is the activity for the job search page used by Employees to signup for a new job
 */
public class EmployeeCompleteJobBox extends AppCompatDialogFragment {

    EditText jobName;
    EditText jobDesc;
    EditText jobStatus;
    EditText jobOwner;
    EditText jobPayment;
    private  Listener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.viewing_job, null);
        builder.setTitle("Complete This Job?");
        builder.setView(view);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setPositiveButton("Complete", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
                String name = jobName.getText().toString();
                String owner = jobOwner.getText().toString();
                String payment = jobPayment.getText().toString();
                listener.getInfo(name,owner,payment);
            }
        });
        jobName = view.findViewById(R.id.viewJobName);
        jobName.setText(getArguments().getString("jobName"));
        jobDesc = view.findViewById(R.id.viewJobDesc);
        jobDesc.setText(getArguments().getString("jobDesc"));
        jobStatus = view.findViewById(R.id.viewJobStatus);
        jobStatus.setText(getArguments().getString("jobStatus"));
        jobOwner = view.findViewById(R.id.viewJobOwner);
        jobOwner.setText(getArguments().getString("jobOwner"));
        jobPayment = view.findViewById(R.id.viewJobPay);
        jobPayment.setText(getArguments().getString("jobPay"));
        return builder.create();
    }


    public interface Listener{
        void getInfo(String name, String owner, String payment);
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
