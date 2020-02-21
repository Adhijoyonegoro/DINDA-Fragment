package com.example.dinda.Model;

import com.google.gson.annotations.SerializedName;

public class PostRegister {
    @SerializedName("status")
    String status;
//    @SerializedName("result")
//    Kontak mKontak;
//    @SerializedName("message")
//    String message;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
