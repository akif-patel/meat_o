package com.patel.akif.meat_o.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by akif_p on 26/08/2017.
 */

public class PreferenceHelper {
    private static SharedPreferences preferences;

    public static void preparePreferences(Context context) {
        preferences = context.getSharedPreferences(Constants.PREFERENCE_KEY,
                Context.MODE_PRIVATE);
    }

    public static void savePreference(String pref_key, String pref_value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(pref_key, pref_value);
        editor.apply();
    }

    public static String getPreference(String pref_key, String pref_default) {
        return preferences.getString(pref_key, pref_default);
    }

    public static boolean getPreferenceBoolean(String pref_key, String pref_default) {
        return Boolean.parseBoolean(
                preferences.getString(pref_key, pref_default));
    }

    public static void setUserLoggedIn() {
        savePreference(Constants.PREF_KEY_LOGGED_IN, Constants.PREF_VAL_TRUE);
    }
    public static void setUserLoggedOff() {
        savePreference(Constants.PREF_KEY_LOGGED_IN, Constants.PREF_VAL_FALSE);
    }

    public static boolean getUserLoggedIn() {
        return getPreferenceBoolean(Constants.PREF_KEY_LOGGED_IN, Constants.PREF_VAL_FALSE);
    }
    public static boolean getUserLoggedOff() {
        return !getUserLoggedIn();
    }

    public static void setLoggedInUserID(String userID) {
        savePreference(Constants.PREF_KEY_USER_ID, userID);
    }
    public static String getLoggedInUserID() {
        return getPreference(Constants.PREF_KEY_USER_ID, Constants.PREF_VAL_USER_ID);
    }

    public static void setLoggedInUserName(String userName) {
        savePreference(Constants.PREF_KEY_USER_NAME, userName);
    }
    public static String getLoggedInUserName() {
        return getPreference(Constants.PREF_KEY_USER_NAME, Constants.PREF_VAL_USER_NAME);
    }

    public static void setDefaultLocationCode(String locationCode) {
        savePreference(Constants.PREF_KEY_DEFAULT_LOC_CODE, locationCode);
    }
    public static String getDefaultLocationCode() {
        return getPreference(Constants.PREF_KEY_DEFAULT_LOC_CODE,
                    Constants.PREF_VAL_DEFAULT_LOC_CODE);
    }

    public static void setDefaultLocationName(String locationName) {
        savePreference(Constants.PREF_KEY_DEFAULT_LOC_NAME, locationName);
    }
    public static String getDefaultLocationName() {
        return getPreference(Constants.PREF_KEY_DEFAULT_LOC_NAME,
                Constants.PREF_VAL_DEFAULT_LOC_NAME);
    }

    public static void setDefaultLocationPin(String locationPin) {
        savePreference(Constants.PREF_KEY_DEFAULT_LOC_PIN, locationPin);
    }
    public static String getDefaultLocationPin() {
        return getPreference(Constants.PREF_KEY_DEFAULT_LOC_PIN,
                Constants.PREF_VAL_DEFAULT_LOC_PIN);
    }

    public static void setDefaultLocationCity(String locationCity) {
        savePreference(Constants.PREF_KEY_DEFAULT_LOC_CITY, locationCity);
    }
    public static String getDefaultLocationCity() {
        return getPreference(Constants.PREF_KEY_DEFAULT_LOC_CITY,
                Constants.PREF_VAL_DEFAULT_LOC_CITY);
    }
}