package com.ranisaurus.newtorklayer.models;

/**
 * Created by muzammilpeer on 9/27/15.
 */
public class Response {
    private String lastName;

    private String responseCode;

    private String userId;

    private String subscriptionType;

    private String Token;

    private String firstName;

    private String customerID;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "ClassPojo [lastName = " + lastName + ", responseCode = " + responseCode + ", userId = " + userId + ", subscriptionType = " + subscriptionType + ", Token = " + Token + ", firstName = " + firstName + ", customerID = " + customerID + "]";
    }
}
