package com.example.dinda.Libraries;

public class Config {
    public static final String ENV_STATUS = "DEVELOPMENT";
/*PROD
    public static final String ApiBaseURL = "http://dinda.astra-agro.co.id/dinda/api/mobile/";
//*/
//*DEV
    public static final String ApiBaseURL = "http://10.23.0.165/dinda/api/mobile/";
//*/
    public static final String ApiURLRegister = ApiBaseURL+"register_clean";
    public static final String ApiURLProfileTemplate = ApiBaseURL+"profile_template_clean";

//    public static final String insertDataJson = root+"V1/index_post";
//    public static final String getDataSupplier = root+"V1/index_get?SITECODE=";
//    public static final String getUpdateVersi = root+"/transaksiJSON";
//    public static final String saveFileJson = root+"transaksiUpload/index_post/";
//    public static final String saveJson = root+"transaksiTes/index_post";
//    public static final String saveTest = root+"TransaksiNew/index_post";
    public static final int TIMEOUT_ACCESS = 10000;

}