package com.example.dinda.Model;

import com.example.dinda.Libraries.ApiStatus;
import com.google.gson.annotations.SerializedName;

public class PostRegister {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    String mData;

    public String getStatus() {
        switch (status) {
            case "success":
                status = ApiStatus.register_success;
                break;
            case "invalid":
                status = ApiStatus.register_invalid;
                break;
            case "imei_ganda":
                status = ApiStatus.imei_ganda;
                break;
            default:
                status = "ok";
        }
        return status;
    }

    public String getData() {
        return mData;
    }

//    public void setStatus(String status) {
//        this.status = status;
//    }
//    public Kontak getKontak() {
//        return mKontak;
//    }
//    public void setKontak(Kontak Kontak) {
//        mKontak = Kontak;
//    }


}
