package com.example.dinda.Model;

import com.google.gson.annotations.SerializedName;

public class Employee {
    @SerializedName("COMPANY_OFFICE")
    private String COMPANY_OFFICE;
    @SerializedName("EMP_NO")
    private String EMP_NO;
    @SerializedName("NAMA")
    private String NAMA;
    @SerializedName("POS_CODE")
    private String POS_CODE;
    @SerializedName("POS_TITLE")
    private String POS_TITLE;
    @SerializedName("imei")
    private String imei;

    public Employee() {
    }

    public Employee(
            String COMPANY_OFFICE,
            String EMP_NO,
            String NAMA,
            String POS_CODE,
            String POS_TITLE,
            String imei
    ) {
        this.COMPANY_OFFICE = COMPANY_OFFICE;
        this.EMP_NO = EMP_NO;
        this.NAMA = NAMA;
        this.POS_CODE = POS_CODE;
        this.POS_TITLE = POS_TITLE;
        this.imei = imei;
    }

}
