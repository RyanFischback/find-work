package com.example.quickcash;

import java.io.Serializable;

/**
 * This class represents the firebase entity Job
 */
public class Job implements Serializable {

    private String jobName;
    private String jobDescription;
    private String jobStatus;
    private String jobOwner;
    private String jobEmployee;
    private String jobPaymentAmount;
    private String jobLatitude;
    private String jobLongitude;

    /**
     * Empty constructor
     */
    public Job()
    {

    }

    /**
     * Constructor for Job object created with a Name, Description, Status, and Owner.
     * @param jobName
     * @param jobDescription
     * @param jobStatus
     * @param jobOwner
     */
    public Job(String jobName, String jobDescription, String jobStatus, String jobOwner, String jobEmployee, String jobPaymentAmount, String jobLatitude, String jobLongitude){
        this.jobName = jobName;
        this.jobDescription = jobDescription;
        this.jobStatus = jobStatus;
        this.jobOwner = jobOwner;
        this.jobEmployee = jobEmployee;
        this.jobPaymentAmount = jobPaymentAmount;
        this.jobLatitude = jobLatitude;
        this.jobLongitude = jobLongitude;
    }
    @Override
    public String toString()
    {
        return "JobName: " + this.jobName + "\nJobDescription: " + this.jobDescription + "\n" +
                "jobStatus: " + this.jobStatus +"\nJobOwner: " + this.jobOwner + "\nJobPay: " +
                this.jobPaymentAmount + "\nJobEmployee: " + this.jobEmployee;
    }
    /**
     * autogenerated get method
     * @return jobOwner
     */
    public String getJobOwner() {
        return jobOwner;
    }
    /**
     * autogenerated set method
     * @return jobOwner
     */
    public void setJobOwner(String jobOwner) {
        this.jobOwner = jobOwner;
    }
    /**
     * autogenerated get method
     * @return jobDescription
     */
    public String getJobDescription() {
        return jobDescription;
    }
    /**
     * autogenerated get method
     * @return jobName
     */
    public String getJobName() {
        return jobName;
    }
    /**
     * autogenerated get method
     * @return jobStatus
     */
    public String getJobStatus() {
        return jobStatus;
    }
    /**
     * autogenerated set method
     * @return jobDescription
     */
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
    /**
     * autogenerated set method
     * @return jobName
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    /**
     * autogenerated set method
     * @return jobStatus
     */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }
    /**
     * autogenerated get method
     * @return jobEmployee
     */
    public String getJobEmployee() {
        return jobEmployee;
    }
    /**
     * autogenerated set method
     * @return jobEmployee
     */
    public void setJobEmployee(String jobEmployee) {
        this.jobEmployee = jobEmployee;
    }
    /**
     * autogenerated get method
     * @return jobPaymentAmount
     */
    public String getJobPaymentAmount() {
        return jobPaymentAmount;
    }
    /**
     * autogenerated set method
     * @return jobPaymentAmount
     */
    public void setJobPaymentAmount(String jobPaymentAmount) {
        this.jobPaymentAmount = jobPaymentAmount;
    }
    /**
     * autogenerated get method
     * @return jobLatitude
     */
    public String getJobLatitude() {
        return jobLatitude;
    }
    /**
     * autogenerated get method
     * @return jobLongitude
     */
    public String getJobLongitude() {
        return jobLongitude;
    }
    /**
     * autogenerated set method
     * @return jobLatitude
     */
    public void setJobLatitude(String jobLatitude) {
        this.jobLatitude = jobLatitude;
    }
    /**
     * autogenerated set method
     * @return jobLongitude
     */
    public void setJobLongitude(String jobLongitude) {
        this.jobLongitude = jobLongitude;
    }
}
