package com.patel.akif.meat_o.aws;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.mobileconnectors.cognito.DefaultSyncCallback;
import com.amazonaws.mobileconnectors.cognito.exceptions.DataStorageException;
import com.amazonaws.regions.Regions;
import com.patel.akif.meat_o.dto.AppUser;

import java.util.List;

/**
 * Created by akif_p on 17/06/2017.
 */

public class AwsIDHelper {
    private static final String AWS_IDENTITY_POOL_ID = "ap-south-1:f5c356c1-46bf-4c01-86d3-2f9ffab8d527";
    private static final String AWS_IDENTITY_DATASET = "DS_MEAT_O";
    private static final String AWS_IDENTITY_DATASET_KEY = "User";
    private static final Regions AWS_REGION = Regions.AP_SOUTH_1;

    public static CognitoCachingCredentialsProvider getCredentialsProvider(Context context) {
        return
            new CognitoCachingCredentialsProvider(
                context,
                AWS_IDENTITY_POOL_ID,
                    Regions.AP_SOUTH_1
            );
    }

    public static void addNewUserToIdentityPool(Context context, AppUser appUser) {
        CognitoSyncManager syncClient = new CognitoSyncManager(
                context,
                AWS_REGION, // Region
                getCredentialsProvider(context));

        Dataset dataset = syncClient.openOrCreateDataset(AWS_IDENTITY_DATASET);
        dataset.put(AWS_IDENTITY_DATASET_KEY, appUser.toString());
        dataset.synchronize(new DefaultSyncCallback() {
            @Override
            public void onFailure(DataStorageException dse) {
                super.onFailure(dse);
                throw dse;
            }
        });
    }
}
