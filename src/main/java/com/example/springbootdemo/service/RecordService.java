package com.example.springbootdemo.service;

import com.example.springbootdemo.dto.RecordDTO;
import com.example.springbootdemo.model.RecordContentModel;
import com.example.springbootdemo.model.RecordModel;
import com.example.springbootdemo.repository.RecordContentRepository;
import com.example.springbootdemo.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final RecordContentRepository recordContentRepository;

    public List<RecordDTO> getContent(Date startDate, Date endDate){
        List<RecordContentModel> recordContentEntities = recordContentRepository.select();
        return recordRepository.select(startDate, endDate).stream()
            .map(item -> recordContentEntities.stream()
                .filter(r -> Objects.equals(r.getId(), item.getRecordContentId()))
                .findFirst()
                .map(r -> new RecordDTO(
                    item.getId(),
                    item.getRecordContentId(),
                    r.getRecordContentName(),
                    item.getRecordDate(),
                    item.getRequiredTime(),
                    item.getMemo(),
                    item.getCreateUserId(),
                    item.getIsDone()))
            )
            .flatMap(Optional::stream)
            .toList();
    }

    @Transactional
    public void createContent(RecordModel recordModel){
        recordRepository.insertToRecord(recordModel);
        recordRepository.insertToRecordMemo(recordModel);
    }

    @Transactional
    public void deleteContent(Long recordId)
    {
        recordRepository.deleteRecordMemo(recordId);
        recordRepository.deleteRecord(recordId);
    }

    public void updateIsDone(Boolean isDone, Integer recordId){
        recordRepository.updateIsDone(isDone, recordId);
    }
}
