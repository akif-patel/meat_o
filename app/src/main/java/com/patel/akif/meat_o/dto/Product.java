package com.patel.akif.meat_o.dto;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by akif_p on 28/06/2017.
 */

@DynamoDBTable(tableName = "MS_PRODUCTS")
public class Product {
    private String code;
    private String name;

    @DynamoDBHashKey(attributeName = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @DynamoDBAttribute(attributeName = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
