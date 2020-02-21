package com.example.dinda.Rest;

import com.example.dinda.Model.GetData;
import com.example.dinda.Model.PostRegister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {
//    @GET("profile_template_clean")
//    Call<GetData> getData();
//    Call<List<Kontak>> getKontak();
    @FormUrlEncoded
    @POST("register_clean")
    Call<PostRegister> postRegister(
        @Field("npk") String npk,
        @Field("dob") String dob,
        @Field("imei") String imei
    );
}