package com.example.springbootdemo.controller;

import com.example.springbootdemo.dto.RecordContentDTO;
import com.example.springbootdemo.service.RecordContentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
public class RecordContentController {

    private final RecordContentService recordContentService;

    /**
     * GET /recordContents
     * 記録対象項目の一覧を取得する
     * @return recordContentsDTO (status code 200)
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/recordContents"
    )
    public ResponseEntity<List<RecordContentDTO>> recordContentsGet(){
        List<RecordContentDTO> recordContentDTOList = recordContentService.getContent()
                .stream().map(record -> new RecordContentDTO(record.getId(), record.getRecordContentName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(recordContentDTOList);
    }

    //TODO:BeanValidationでエラーの時、globalExceptionHandlerでエラーメッセージを投げる
    /**
     * POST /recordContents
     * 記録対象項目を登録する
     * @param recordContentName 記録対象項目名
     * @param createUserId　作成者ID
     * @return void (status code 201)
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/recordContent"
    )
    public ResponseEntity<Void> recordContentPost(@NotNull @Size(min=1, max=256) @Valid @RequestParam("recordContentName") String recordContentName, @NotNull @Valid @RequestParam("createUserId") int createUserId){
        recordContentService.createContent(recordContentName, createUserId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * DELETE /recordContents
     * 記録対象項目を削除する
     * @return void (status code 200)
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/recordContent/{recordContentName}"
    )
    public ResponseEntity<Void> recordContentDelete(@NotNull @Size(min=1, max=256) @Valid @PathVariable("recordContentName") String recordContentName){
        recordContentService.deleteContent(recordContentName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
