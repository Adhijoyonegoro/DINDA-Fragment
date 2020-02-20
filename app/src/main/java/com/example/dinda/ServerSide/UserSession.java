package com.example.dinda.ServerSide;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Created by user on 18-Aug-17.
 */

public class UserSession {

//    private final static UserSession instance;

    static {
//        instance = new UserSession();
    }

//    public static UserSession getInstance() {
//        return instance;
//    }

    private final SharedPreferences appPreference;

    public UserSession(Context context) {
        appPreference = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setSession(String key, String value) {
        SharedPreferences.Editor editor = appPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void clearSession() {
        SharedPreferences.Editor editor = appPreference.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }

    public String getSession(String key) {
        String value = appPreference.getString(key, "");
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        return value;
    }

    public void setIsLogin(boolean stat) {
        SharedPreferences.Editor editor = appPreference.edit();
        editor.putBoolean("IS_LOGIN", stat);
        editor.apply();
    }

    public boolean getIsLogin() {
        boolean isLogin = appPreference.getBoolean("IS_LOGIN", false);
        return isLogin;
    }

    public void setNama(String nama) {
        setSession("nama", nama);
    }

    public String getNama(){
        return getSession("nama");
    }

    public void setSite(String site) {
        setSession("site", site);
    }

    public String getSite(){
        return getSession("site");
    }

    public void setJabatan(String jabatan) {
        setSession("jabatan", jabatan);
    }

    public String getJabatan() {
        return getSession("jabatan");
    }

    public void setUserId(String userId) {
        setSession("userId", userId);
    }

    public String getUserId(){
        return getSession("userId");
    }

    public void setPass(String pass) {
        setSession("pass", pass);
    }

    public String getPass(){
        return getSession("pass");
    }

    public void setPhoto(String photo) {
        setSession("photo", photo);
    }

    public String getPhoto(){
        return getSession("photo");
    }

//    public void setEmpNo(String empNo) {
//        setSession("empNo", empNo);
//    }
//
//    public String getEmpNo(){
//        return getSession("empNo");
//    }
//
//
//
//    public void setCompanyId(String site) {
//        setSession("companyID", site);
//    }
//
//    public String getCompanyId(){
//        return getSession("companyID");
//    }
//
//
//
//    public void setKdKaryawan(String kdKaryawan) {
//        setSession("kdKaryawan", kdKaryawan);
//    }
//
//    public String getKdKaryawan(){
//        return getSession("kdKaryawan");
//    }






}


