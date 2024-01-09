package com.example.springbootdemo.dto;

import lombok.Value;

import java.sql.Date;

@Value
public class RecordDTO {
    Long id;
    Integer recordContentId;
    String recordContentName;
    Date recordDate;
    Integer requiredTime;
    String memo;
    Integer createUserId;
    Boolean isDone;
}
