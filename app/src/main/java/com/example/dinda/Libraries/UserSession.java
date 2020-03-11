package com.example.dinda.Libraries;

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

    public void setCompanyOffice(String companyOffice) { setSession("companyOffice", companyOffice); }
    public String getCompanyOffice(){
        return getSession("companyOffice");
    }

    public void setNPK(String npk) {
        setSession("npk", npk);
    }
    public String getNPK() {
        return getSession("npk");
    }

    public void setPosCode(String posCode) {
        setSession("posCode", posCode);
    }
    public String getPosCode(){
        return getSession("posCode");
    }

    public void setPosTitle(String posTitle) {
        setSession("posTitle", posTitle);
    }
    public String getPosTitle(){
        return getSession("posTitle");
    }
}


