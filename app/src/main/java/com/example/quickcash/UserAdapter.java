package com.example.quickcash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Custom adapter class used to represent a Job into a list view (Used by employees and employers)
 */
public class UserAdapter extends ArrayAdapter<User>
{
    private Context context;
    private ArrayList<User> users;

    /**
     * Constructor method for JobAdapter using super for ArrayAdapter
     * @param context context of the activity
     * @param users List of Jobs to display
     */
    public UserAdapter(Context context, ArrayList<User> users)
    {
        super(context, R.layout.employee_edit_profile, users);
        this.context = context;
        this.users = users;
    }

    /**
     * Custom getView method from ArrayAdapter to interact with our xml objects for visual representation
     * @param position used to iterate through job list
     * @param convertView view
     * @param parent parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if (view == null){
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.row_item_user, null);
        }
        User user = users.get(position);
        view.setId(position);
        if (user != null){
            EditText name = (EditText) view.findViewById(R.id.userName);
            name.setText(user.getEmail());
            EditText password = (EditText) view.findViewById(R.id.userPassword);
            password.setText(user.getPassword());
        }
        return view;
    }
}
