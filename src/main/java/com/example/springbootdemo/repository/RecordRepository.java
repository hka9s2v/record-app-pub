package com.example.springbootdemo.repository;

import com.example.springbootdemo.model.RecordModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@Mapper
public interface RecordRepository{
    @Select("""
            SELECT Record.id, recordContentId, recordDate, requiredTime, memo, isDone, createUserId
            FROM Record LEFT OUTER JOIN RecordMemo ON Record.id = RecordMemo.recordId
            WHERE recordDate BETWEEN #{startDate} AND #{endDate}
            """)
    List<RecordModel> select(Date startDate, Date endDate);

    @Insert("""
            INSERT INTO Record(recordContentId, recordDate, requiredTime, createUserId, isDone, isValid) 
            VALUES (#{re.recordContentId}, #{re.recordDate}, #{re.requiredTime}, #{re.createUserId}, false, true)
            """)
    @Options(useGeneratedKeys = true, keyProperty = "re.id", keyColumn = "id")
    void insertToRecord(@Param("re") RecordModel recordModel);

    @Insert("INSERT INTO RecordMemo(recordId, memo) VALUES (#{re.id}, #{re.memo})")
    void insertToRecordMemo(@Param("re") RecordModel recordModel);

    @Update("UPDATE Record SET isDone = #{isDone} WHERE id = #{recordId}")
    void updateIsDone(Boolean isDone, Integer recordId);

    @Delete("DELETE FROM Record WHERE id = #{recordId}")
    void deleteRecord(Long recordId);

    @Delete("DELETE FROM RecordMemo WHERE recordId = #{recordId}")
    void deleteRecordMemo(Long recordId);
}
