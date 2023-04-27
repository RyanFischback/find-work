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
 * This class is the activity for the job management page used by Employers to edit their jobs
 */
public class EmployerEditJobBox extends AppCompatDialogFragment {

    EditText jobName;
    EditText jobDescription;
    EditText jobPayment;
    private String originalName;
    private  Listener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.editing_job, null);
        builder.setTitle("Edit Job");
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = jobName.getText().toString();
                String description = jobDescription.getText().toString();
                String status = jobPayment.getText().toString();
                listener.getInfo(name,description, status, originalName, 0);
            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
                String name = jobName.getText().toString();
                String description = jobDescription.getText().toString();
                String payment = jobPayment.getText().toString();
                listener.getInfo(name,description, originalName, payment, 1);
            }
        });

        jobName = view.findViewById(R.id.existingJobName);
        jobName.setText(getArguments().getString("jobName"));
        jobDescription = view.findViewById(R.id.existingJobDesc);
        jobDescription.setText(getArguments().getString("jobDesc"));
        jobPayment = view.findViewById(R.id.existingJobPay);
        jobPayment.setText(getArguments().getString("jobPay"));
        originalName = getArguments().getString("jobOriginalName");
        return builder.create();
    }


    public interface Listener{
        void getInfo(String name, String description, String originalName, String payment, int activity);
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
