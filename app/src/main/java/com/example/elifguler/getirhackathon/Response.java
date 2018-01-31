package com.example.elifguler.getirhackathon;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by elifguler on 28.01.2018.
 */

public class Response implements Serializable {
    @SerializedName("code")
    int code;

    @SerializedName("msg")
    String message;

    @SerializedName("records")
    List<Record> records;
}

class Record implements Serializable {
    @SerializedName("_id")
    Id id;

    @SerializedName("totalCount")
    int totalCount;
}

class Id implements Serializable {
    @SerializedName("_id")
    String id;

    @SerializedName("key")
    String key;

    @SerializedName("value")
    String value;

    @SerializedName("createdAt")
    String createdAt;
}
