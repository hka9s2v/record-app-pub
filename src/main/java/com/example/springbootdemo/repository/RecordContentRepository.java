package com.example.springbootdemo.repository;

import com.example.springbootdemo.model.RecordContentModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface RecordContentRepository {
    @Select("SELECT id, recordContentName FROM RecordContent")
    List<RecordContentModel> select();

    @Select("SELECT id, recordContentName FROM RecordContent WHERE recordContentName = #{recordContentName} LIMIT 1")
    Optional<RecordContentModel> selectByRecordContentName(String recordContentName);

    @Insert("INSERT INTO RecordContent(recordContentName, createUserId, isValid) VALUES (#{recordContentName}, #{createUserId}, true)")
    void insert(String recordContentName, int createUserId);

    @Delete("DELETE FROM RecordContent WHERE recordContentName = #{recordContentName}")
    void delete(String recordContentName);
}
