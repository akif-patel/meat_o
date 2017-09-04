package com.patel.akif.meat_o.aws;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

/**
 * Created by akif_p on 17/06/2017.
 */

public class AwsDBHelper {
    private static CognitoCachingCredentialsProvider credentialsProvider;
    private static DynamoDBMapper dbMapper;
    private static AmazonDynamoDBClient dbClient;

    private static void initCredentialsProvider(Context context) {
        if(credentialsProvider == null)
            credentialsProvider = AwsIDHelper.getCredentialsProvider(context);
    }

    public static DynamoDBMapper getDBMapper(Context context) {
        if(dbMapper == null)
            dbMapper = new DynamoDBMapper(getDBClient(context));

        return dbMapper;
    }

    public static AmazonDynamoDBClient getDBClient(Context context) {
        initCredentialsProvider(context);

        if(dbClient == null) {
            dbClient = new AmazonDynamoDBClient(credentialsProvider);
            dbClient.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
        }

        return dbClient;
    }

    public static void saveDBObject(Context context, Object obj) {
        getDBMapper(context).save(obj);
    }
}
