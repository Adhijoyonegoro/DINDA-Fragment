package com.example.dinda.ServerSide;

public class URLApk {
    public static final String root = "http://dinda.astra-agro.co.id/siska/API/";
    public static final String root1 = "http://10.23.0.165/siska/API/";
    public static final String insertDataJson = root+"V1/index_post";
    public static final String getDataSupplier = root+"V1/index_get?SITECODE=";
    public static final String getUpdateVersi = root+"/transaksiJSON";
    public static final String saveFileJson = root+"transaksiUpload/index_post/";
    public static final String saveJson = root+"transaksiTes/index_post";
    public static final String saveTest = root+"TransaksiNew/index_post";
    public static final int TIMEOUT_ACCESS = 10000;
}