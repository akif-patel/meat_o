package com.patel.akif.meat_o.dto;

/**
 * Created by akif_p on 24/05/2017.
 */

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.patel.akif.meat_o.aws.AwsDBHelper;
import com.patel.akif.meat_o.dto.marshallers.StatusMarshaller;
import com.patel.akif.meat_o.utils.Constants;
import com.patel.akif.meat_o.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName = "AD_APP_USERS")
public class AppUser {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Status status;
    private List<DeliveryAddress> deliveryAddresses = new ArrayList<>();

    @DynamoDBHashKey(attributeName = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBAttribute(attributeName = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @DynamoDBAttribute(attributeName = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @DynamoDBAttribute(attributeName = "PHONE_NUMBER")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @DynamoDBMarshalling(marshallerClass = StatusMarshaller.class)
    @DynamoDBAttribute(attributeName = "STATUS")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @DynamoDBIgnore
    public List<DeliveryAddress> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(List<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }

    @Override
    public String toString() {
        String token = this.getId();

        if(token == null || token.isEmpty())
            token = this.getEmail() + " | " +
                this.getFirstName() + " | " + this.getLastName();

        return token;
    }

    @DynamoDBIgnore
    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    public void addUserToDB(Context context) {
        AwsDBHelper.saveDBObject(context, this);

        PreferenceHelper.setUserLoggedIn();
        PreferenceHelper.setLoggedInUserID(this.getId());
        PreferenceHelper.setLoggedInUserName(this.getFullName());

        if(deliveryAddresses != null && deliveryAddresses.size() > 0) {
            for (DeliveryAddress address: deliveryAddresses) {
                address.addDeliveryAddressToDB(context);

                PreferenceHelper.setDefaultLocationCode(Constants.PREF_VAL_DEFAULT_LOC_CODE);
                PreferenceHelper.setDefaultLocationCity(Constants.PREF_VAL_DEFAULT_LOC_CITY);
                PreferenceHelper.setDefaultLocationName(Constants.PREF_VAL_DEFAULT_LOC_NAME);
                PreferenceHelper.setDefaultLocationPin(Constants.PREF_VAL_DEFAULT_LOC_PIN);
            }
        }
    }

    public static AppUser getUserByID(Context context, String userID) {
        return AwsDBHelper.getDBMapper(context).load(AppUser.class, userID);
    }

    public void deleteUser(Context context) {
        AwsDBHelper.getDBMapper(context).delete(this);
    }

    public void deleteUserByID(Context context, String userID) {
        AppUser appUser = AppUser.getUserByID(context, userID);
        appUser.deleteUser(context);
    }

    public void addAddress(DeliveryAddress address) {
        address.setCode(this.getId() + "_" + address.getLabel());
        address.setUser(this.getId());

        if(this.deliveryAddresses.size() == 0)
            address.setDefault(true);

        this.deliveryAddresses.add(address);
    }
}
