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
public class EmployerAddJobBox extends AppCompatDialogFragment {

    EditText jobName;
    EditText jobDescription;
    EditText jobPaymentAmount;
    private  Listener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.adding_job, null);
        builder.setTitle("Post a New Job in your Area");
        builder.setView(view);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
                String name = jobName.getText().toString();
                String description = jobDescription.getText().toString();
                String payment = jobPaymentAmount.getText().toString();
                listener.getInfo(name,description, payment);
            }
        });

        jobName = view.findViewById(R.id.newJobName);
        jobDescription = view.findViewById(R.id.newJobDesc);
        jobPaymentAmount = view.findViewById(R.id.newJobPaymentAmount);
        return builder.create();
    }


    public interface Listener{
        void getInfo(String name, String description, String payment);
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
