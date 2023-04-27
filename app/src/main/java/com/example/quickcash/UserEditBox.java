package com.example.quickcash;

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
 * This class is the activity for editing an employees information
 */
public class UserEditBox extends AppCompatDialogFragment {

    EditText userName;
    EditText userPassword;
    private String originalName;
    private Listener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.editing_user, null);
        builder.setTitle("Edit User");
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
                String username = userName.getText().toString();
                String password = userPassword.getText().toString();
                listener.getInfo(username, password, originalName);
            }
        });

        userName = view.findViewById(R.id.editUsername);
        userName.setText(getArguments().getString("userName"));
        userPassword = view.findViewById(R.id.editUserPassword);
        userPassword.setText(getArguments().getString("userPass"));
        originalName = getArguments().getString("userOriginalName");
        return builder.create();
    }


    public interface Listener{
        void getInfo(String username, String password, String originalUsername);
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
