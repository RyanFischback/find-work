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
public class JobAdapter extends ArrayAdapter<Job>
{
    private Context context;
    private ArrayList<Job> jobs;

    /**
     * Constructor method for JobAdapter using super for ArrayAdapter
     * @param context context of the activity
     * @param jobs List of Jobs to display
     */
    public JobAdapter(Context context, ArrayList<Job> jobs)
    {
        super(context, R.layout.employer_posted, jobs);
        this.context = context;
        this.jobs = jobs;
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
            view = vi.inflate(R.layout.row_item, null);
        }
        Job job = jobs.get(position);
        view.setId(position);
        if (job != null){
            EditText name = (EditText) view.findViewById(R.id.jobName);
            name.setText(job.getJobName());
            EditText jobDesc = (EditText) view.findViewById(R.id.jobDescription);
            jobDesc.setText(job.getJobDescription());
            EditText jobStatus = (EditText) view.findViewById(R.id.jobStatus);
            jobStatus.setText(job.getJobStatus());
            EditText jobPayment = (EditText) view.findViewById(R.id.jobPaymentAmount);
            jobPayment.setText("$" + job.getJobPaymentAmount());
            EditText jobEmployee = (EditText) view.findViewById(R.id.jobEmployee);
            jobEmployee.setText(job.getJobEmployee());
            EditText jobOwner = (EditText) view.findViewById(R.id.jobOwner);
            jobOwner.setText(job.getJobOwner());
        }
        return view;
    }
}
