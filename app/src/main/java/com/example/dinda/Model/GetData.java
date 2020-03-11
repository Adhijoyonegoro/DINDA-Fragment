package com.example.dinda.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetData {
    @SerializedName("status")
    String status;
    @SerializedName("template")
    List<Template> listTemplate;
    @SerializedName("profile")
    List<Profile> listProfile;
//    public String getStatus() {
//        return status;
//    }
//    public void setStatus(String status) {
//        this.status = status;
//    }
//    public String getMessage() {
//        return message;
//    }
//    public void setMessage(String message) {
//        this.message = message;
//    }
    public List<Profile> getListProfile() {
        return listProfile;
    }
    public List<Template> getListTemplate() {
        return listTemplate;
    }
//    public void setListDataKontak(List<Kontak> listDataKontak) {
//        this.listDataKontak = listDataKontak;
//    }
}
