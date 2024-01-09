package com.example.springbootdemo.model;

import lombok.Value;
import java.sql.Date;

@Value
public class RecordModel {
    Long id;
    Integer recordContentId;
    Date recordDate;
    Integer requiredTime;
    String memo;
    Boolean isDone;
    Integer createUserId;
}
