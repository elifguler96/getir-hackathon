package com.example.elifguler.getirhackathon;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by elifguler on 28.01.2018.
 */

public class RequestBody implements Serializable {
    @SerializedName("startDate")
    String startDate;

    @SerializedName("endDate")
    String endDate;

    @SerializedName("minCount")
    Integer minCount;

    @SerializedName("maxCount")
    Integer maxCount;
}
