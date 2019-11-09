package com.parthjadav.passwordmanager.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PreferenceManager {

    public static PreferenceManager preferenceManager;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public static PreferenceManager getInstance() {
        return preferenceManager;
    }

    @SuppressLint("CommitPrefEdits")
    public PreferenceManager(Context context) {
        preferenceManager = this;
        mSharedPreferences = context.getSharedPreferences(VariableBag.PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void clearPreferences() {
        mEditor.clear();
        mEditor.commit();
    }

    public void removePref(Context context, String keyToRemove) {
        mSharedPreferences = context.getSharedPreferences(VariableBag.PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.remove(keyToRemove);
        mEditor.apply();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        mEditor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        mEditor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return mSharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    /*set preference for registration*/

    public String getRegisteredUserId() {
        return mSharedPreferences.getString(VariableBag.USER_ID, "");
    }

    public void setRegisteredUserId(String strUserId) {
        mEditor.putString(VariableBag.USER_ID, strUserId).commit();
    }

    public String getFCMToken() {
        return mSharedPreferences.getString(VariableBag.FCM_TOKEN, "");
    }

    public void setFCMToken(String fcmToken) {
        mEditor.putString(VariableBag.FCM_TOKEN, fcmToken).commit();
    }

    public void setLoginSession() {
        mEditor.putBoolean(VariableBag.LOGIN, true);
    }

    public boolean getLoginSession() {
        return mSharedPreferences.getBoolean(VariableBag.LOGIN, false);
    }

    public void setKeyValueString(String key, String value) {
        mEditor.putString(key, value).commit();
    }

    public void setKeyValueInt(String key, int value) {
        mEditor.putInt(key, value).commit();
    }

    public boolean setKeyValueBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value).commit();
        return value;
    }

    public String getKeyValueString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public int getKeyValueInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public boolean getKeyValueBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public void setObject(String key, Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        Log.e("**** Json Object - ",json);
        mSharedPreferences.edit().putString(key, json).apply();
    }

    public <GenericClass> GenericClass getObject(String key, Class<GenericClass> object) {
        try {
            Gson gson = new Gson();
            String json = mSharedPreferences.getString(key,"");
            return gson.fromJson(json, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setJSONPref(String key, String json) {
        mSharedPreferences.edit().putString(key, json).apply();
    }

    public JSONObject getJSONObject(String key) {
        try {
            return new JSONObject(mSharedPreferences.getString(key, ""));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getJSONArray(String key) {
        try {
            return new JSONArray(mSharedPreferences.getString(key, ""));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getJSONKeyString(String objKey, String stringKey) {
        JSONObject obj = getJSONObject(objKey);
        if (objKey != null) {
            try {
                return obj.getString(stringKey);
            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }
}


