package com.example.quickcash;

import java.io.Serializable;

/**
 * This class is used to store data for our users
 */
public class User implements Serializable
{
    private String email;
    private String password;
    private String userType;
    private String latitude;
    private String longitude;


    public User()
    {

    }

    /**
     * Constructor for user
     * @param email email used
     * @param password password used
     * @param userType user type (employee/employer)
     * @param latitude
     * @param longitude
     */
    public User(String email, String password, String userType, String latitude, String longitude)
    {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Set the userType
     * @param userType
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * set the user password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * set the user email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get the user type
     * @return
     */
    public String getUserType() {
        return userType;
    }

    /**
     * get the user password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * get the user email
     * @return
     */
    public String getEmail() {
        return email;
    }
    /**
     * get the user latitude
     * @return latitude
     */
    public String getLatitude() {
        return latitude;
    }
    /**
     * get the user longitude
     * @return longitude
     */
    public String getLongitude() {
        return longitude;
    }
    /**
     * set the user latitude
     * @param latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    /**
     * set the user longitude
     * @param longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
