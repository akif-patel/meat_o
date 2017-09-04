package com.patel.akif.meat_o.dto;

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.patel.akif.meat_o.aws.AwsDBHelper;
import com.patel.akif.meat_o.utils.Constants;

/**
 * Created by akif_p on 20/06/2017.
 */

@DynamoDBTable(tableName = "AD_DELIVERY_ADDRESS")
public class DeliveryAddress {
    private String code;
    private String label;
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String state;
    private String city;
    private String country;
    private String pincode;
    private String user;
    private boolean isDefault;

    @DynamoDBHashKey(attributeName = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @DynamoDBAttribute(attributeName = "ADDRESS_LINE_1")
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @DynamoDBAttribute(attributeName = "ADDRESS_LINE_2")
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @DynamoDBAttribute(attributeName = "LANDMARK")
    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    @DynamoDBAttribute(attributeName = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @DynamoDBAttribute(attributeName = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @DynamoDBAttribute(attributeName = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @DynamoDBRangeKey(attributeName = "PIN_CODE")
    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @DynamoDBAttribute(attributeName = "LABEL")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @DynamoDBAttribute(attributeName = "USER_ID")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @DynamoDBAttribute(attributeName = "DEFAULT")
    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public DeliveryAddress() {
        this.label = Constants.ADDRESS_LABEL_HOME;
        this.country = Constants.COUNTRY_CODE_IND;
        this.state = Constants.STATE_CODE_MH;
        this.city = Constants.CITY_CODE_MIRA;
    }

    public void addDeliveryAddressToDB(Context context) {
        AwsDBHelper.saveDBObject(context, this);
    }
}
