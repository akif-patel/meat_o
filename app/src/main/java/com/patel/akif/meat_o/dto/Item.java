package com.patel.akif.meat_o.dto;

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIgnore;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.patel.akif.meat_o.aws.AwsDBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by akif_p on 28/06/2017.
 */

@DynamoDBTable(tableName = "MS_ITEMS")
public class Item {
    private String code;
    private String name;
    private String productCode;
    private String description;
    private SaleUnit saleUnit;
    private String brand;
    private String mUnit;
    private int rate;
    private int rateQuantity;
    private int sortOrder = 0;
    private int quantityCode = 0;

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

    @DynamoDBRangeKey(attributeName = "PRODUCT_CODE")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @DynamoDBAttribute(attributeName = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBIgnore
    public SaleUnit getSaleUnit() {
        saleUnit = SaleUnit.valueOf(mUnit);
        return saleUnit;
    }

    public void setSaleUnit(SaleUnit saleUnit) {
        this.saleUnit = saleUnit;
    }

    @DynamoDBAttribute(attributeName = "BRAND")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @DynamoDBAttribute(attributeName = "SALE_UNIT")
    public String getmUnit() {
        return mUnit;
    }

    public void setmUnit(String mUnit) {
        this.mUnit = mUnit;
    }

    @DynamoDBAttribute(attributeName = "RATE")
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @DynamoDBAttribute(attributeName = "RATE_QUANTITY")
    public int getRateQuantity() {
        return rateQuantity;
    }

    public void setRateQuantity(int rateQuantity) {
        this.rateQuantity = rateQuantity;
    }

    @DynamoDBAttribute(attributeName = "SORT_ORDER")
    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @DynamoDBAttribute(attributeName = "QUANTITY_CODE")
    public int getQuantityCode() {
        return quantityCode;
    }

    public void setQuantityCode(int quantityCode) {
        this.quantityCode = quantityCode;
    }

    public static List<Item> getItemsByProduct(Context context, String productCode) {
        List<Item> items;

        Map<String, AttributeValue> scanMap = new HashMap<String, AttributeValue>();
        scanMap.put(":val1", new AttributeValue().withS(productCode));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("PRODUCT_CODE = :val1")
                    .withExpressionAttributeValues(scanMap);

        items = AwsDBHelper.getDBMapper(context)
                .scan(Item.class, scanExpression);

        return items;
    }
}
