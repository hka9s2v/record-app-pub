package com.example.springbootdemo.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class RecordRequest {

    @JsonProperty("recordContentId")
    int recordContentId;

    @JsonProperty("recordDate")
    String recordDate;

    @JsonProperty("requiredTime")
    int requiredTime;

    @JsonProperty("memo")
    String memo;

    @JsonProperty("createUserId")
    int createUserId;
}
