package com.example.springbootdemo.service;

import com.example.springbootdemo.model.RecordContentModel;
import com.example.springbootdemo.repository.RecordContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RecordContentService {

    private final RecordContentRepository recordContentRepository;

    public List<RecordContentModel> getContent(){
        return recordContentRepository.select();
    }

    public void createContent(String recordContentName, int createUserId){
        recordContentRepository.insert(recordContentName, createUserId);
    }

    public void deleteContent(String recordContentName)
    {
        // optional型の扱い方これでいいか？
        recordContentRepository.selectByRecordContentName(recordContentName)
                .orElseThrow(NoSuchElementException::new);
        recordContentRepository.delete(recordContentName);
    }


}
