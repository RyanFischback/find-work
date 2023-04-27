package com.example.quickcash.employers;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.Job;
import com.example.quickcash.JobAdapter;
import com.example.quickcash.PayPalConfig;
import com.example.quickcash.R;
import com.example.quickcash.dao.DAOJobs;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;


import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * This class is the activity for the job management page used by Employers to interact with their jobs
 */
public class EmployerCompleted extends AppCompatActivity implements EmployerPayEmployeeJobBox.Listener{

    private String activeUser;
    private ArrayList<Job> activeUserJobs;
    private static PayPalConfiguration config;
    ActivityResultLauncher activityResultLauncher;
    private Job job;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_posted);
        if(getIntent().getExtras() != null)
        {
            activeUser = getIntent().getExtras().getString("activeUser");
        }
        if(activeUserJobs == null)
        {
            activeUserJobs = (ArrayList<Job>) getIntent().getSerializableExtra("activeUserJobs");
        }
        ListView activeUserJobsView = findViewById(R.id.activeUserJobs);
        ArrayAdapter<Job> arrayAdapter = new JobAdapter(
                this,
                activeUserJobs);
        activeUserJobsView.setAdapter(arrayAdapter);
        activeUserJobsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                job = activeUserJobs.get(position);
                if(!job.getJobStatus().contains("Paid"))
                {
                    //Pay the employee the job payment amount
                    EmployerPayEmployeeJobBox employerPayEmployeeJobBox = new EmployerPayEmployeeJobBox();
                    Bundle jobParams = new Bundle();
                    jobParams.putString("jobName",  job.getJobName());
                    jobParams.putString("jobDesc",  job.getJobDescription());
                    jobParams.putString("jobPay",  job.getJobPaymentAmount());
                    jobParams.putString("jobStatus",  job.getJobStatus());
                    jobParams.putString("jobEmployee",  job.getJobEmployee());
                    employerPayEmployeeJobBox.setArguments(jobParams);
                    employerPayEmployeeJobBox.show(getSupportFragmentManager(),"Pay Employee for Job");
                }
                else
                {
                    Toast.makeText(getBaseContext(), "This Job Is Already Paid For", Toast.LENGTH_LONG).show();
                }
            }
        });
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_setting:
                        Intent setting = new Intent(getApplicationContext(), EmployerAccountActivity.class);
                        setting.putExtra("activeUser", activeUser);
                        startActivity(setting);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ic_home:
                        Intent home = new Intent(getApplicationContext(), EmployerMainActivity.class);
                        home.putExtra("activeUser", activeUser);
                        startActivity(home);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ic_jobs:
                        Intent jobs = new Intent(getApplicationContext(), EmployerJobsActivity.class);
                        jobs.putExtra("activeUser", activeUser);
                        startActivity(jobs);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(PayPalConfig.PAYPAL_CLIENT_ID);
        initializeActivityLauncher();
    }

    private void initializeActivityLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if (confirmation != null) {
                        try {
                            String paymentDetails = confirmation.toJSONObject().toString(4);
                            JSONObject payObj = new JSONObject(paymentDetails);
                            String payID = payObj.getJSONObject("response").getString("id");
                            String state = payObj.getJSONObject("response").getString("state");
                            Toast.makeText(getBaseContext(), "Payment " + state + " \nwith payment id is " + payID, Toast.LENGTH_LONG).show();
                            //Update job status complete and paid.
                            DAOJobs daoJobs = new DAOJobs();
                            daoJobs.UpdateJob(job.getJobName(), job.getJobDescription(), "Completed & Paid", job.getJobOwner(), job.getJobName(), job.getJobEmployee(), job.getJobPaymentAmount());
                            Intent intent = new Intent(getBaseContext(), EmployerCompleted.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            Log.e("Error", "an extremely unlikely failure occurred: ", e);
                        }
                    }
                } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID){
                    Log.d(TAG,"Launcher Result Invalid");
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Log.d(TAG, "Launcher Result Cancelled");
                }
            }
        });
    }

    @Override
    public void getInfo(String employee, String payment) {
        //Take from activeUser account and pay employee with payment
        String rawPayment = payment.replace("$", "");
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(rawPayment)),"CAD","Complete Job: Employee Fee:",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        activityResultLauncher.launch(intent);
    }
}
