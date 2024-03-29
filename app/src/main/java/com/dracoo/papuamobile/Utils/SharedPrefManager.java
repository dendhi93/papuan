package com.dracoo.papuamobile.Utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefManager {

    public static final String SP_BALIEMS_APP = "PapuanApp";
    public static final String SP_USER_NAME = "spUserName";
    public static final String SP_ID = "spId";
    public static final String SP_NIK = "spNIK";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_USER_SUDAH_LOGIN = "spUserSudahLogin";

    public static final String SP_VID = "VID";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_BALIEMS_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.apply();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.apply();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.apply();
    }

    public String getSpNik() {
        return sp.getString(SP_NIK,"");
    }

    public String getSpVid() {
        return sp.getString(SP_VID, "");
    }

    public String getSPUserName(){
        return sp.getString(SP_USER_NAME, "");
    }

    public String getId(){
        return sp.getString(SP_ID, "");
    }

    public String getSpEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public Boolean getSPUserSudahLogin(){
        return sp.getBoolean(SP_USER_SUDAH_LOGIN, false);
    }
}
