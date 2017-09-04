package com.patel.akif.meat_o.aws;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.patel.akif.meat_o.R;
import com.patel.akif.meat_o.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by akif_p on 17/06/2017.
 */

public class AwsS3Helper {
    private static CognitoCachingCredentialsProvider credentialsProvider;
    private static AmazonS3Client awsS3Client;
    private static TransferUtility awsTransferUtil;

    private static List<String> listAwsFolders;

    private static void initCredentialsProvider(Context context) {
        if(credentialsProvider == null)
            credentialsProvider = AwsIDHelper.getCredentialsProvider(context);
    }

    public static AmazonS3Client getS3Client(Context context) {
        initCredentialsProvider(context);

        if(awsS3Client == null) {
            awsS3Client = new AmazonS3Client(credentialsProvider);
            awsS3Client.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
        }

        return awsS3Client;
    }

    public static List<String> getS3ObjectsKeys(Context context, String folder) {
        List<String> listObjectKeys = new ArrayList<>();
        List<S3ObjectSummary> s3ObjList =
                getS3Client(context)
                        .listObjects(Constants.MS_BUCKET_NAME, folder)
                            .getObjectSummaries();

        for (S3ObjectSummary summary : s3ObjList) {
            if(!summary.getKey().endsWith("/"))
                listObjectKeys.add(summary.getKey());
        }

        return listObjectKeys;
    }

    public static List<String> getS3ObjectsKeysByIndex(Context context, int index) {
        return getS3ObjectsKeys(context, getS3FolderByIndex(context, index));
    }

    public static TransferUtility getTransferUtil(Context context) {
        initCredentialsProvider(context);

        return new TransferUtility(
                getS3Client(context),
                context
        );
    }

    public static void fillMap(Map<String, Object> map,
            TransferObserver observer, boolean isChecked) {
        int progress = (int) ((double) observer.getBytesTransferred() * 100 / observer
                .getBytesTotal());
        map.put("id", observer.getId());
        map.put("checked", isChecked);
        map.put("fileName", observer.getAbsoluteFilePath());
        map.put("progress", progress);
        map.put("state", observer.getState());
        map.put("percentage", progress + "%");
    }

    public static String getS3FolderByIndex(Context context, int index) {
        if(listAwsFolders == null || listAwsFolders.size() == 0) {
            listAwsFolders = new ArrayList<String>(
                    Arrays.asList(
                            context.getResources().getStringArray(R.array.product_codes)));
        }

        return listAwsFolders.get(index);
    }

    public static String getPresignedUrl(Context context, String objectName) {
        GeneratePresignedUrlRequest urlRequest =
                new GeneratePresignedUrlRequest(Constants.MS_BUCKET_NAME, objectName);

        return getS3Client(context).generatePresignedUrl(urlRequest).toString();
    }

    public static TransferObserver download(Context context, String objKey, File outputFile) {
        return
            getTransferUtil(context).
                download(Constants.MS_BUCKET_NAME, objKey, outputFile);
    }
}
