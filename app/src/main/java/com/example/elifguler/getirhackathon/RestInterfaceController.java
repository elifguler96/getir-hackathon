package com.example.elifguler.getirhackathon;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by elifguler on 28.01.2018.
 */

public interface RestInterfaceController {

    @POST("/searchRecords")
    Call<Response> request(@Body RequestBody body);
}