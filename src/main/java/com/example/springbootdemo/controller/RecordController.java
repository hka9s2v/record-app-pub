package com.example.springbootdemo.controller;

import com.example.springbootdemo.dto.RecordDTO;
import com.example.springbootdemo.controller.request.RecordRequest;
import com.example.springbootdemo.model.RecordModel;
import com.example.springbootdemo.service.RecordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
public class RecordController {
    private final RecordService recordService;

    /**
     * GET /record
     * 日時の範囲を指定して記録を取得する
     * @return recordContentsDTO (status code 200)
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/record"
    )
    public ResponseEntity<List<RecordDTO>> recordGet(@NotNull @RequestParam("startDate") String startDate, @NotNull @RequestParam("endDate") String endDate){

        return ResponseEntity.ok(recordService.getContent(Date.valueOf(startDate), Date.valueOf(endDate)));
    }

    /**
     * POST /record/update
     * タスクの完了状況を更新する
     * @return void (status code 200)
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/record/complete"
    )
    public ResponseEntity<Void> recordComplete(@NotNull @RequestParam("isDone") Boolean isDone, @NotNull @RequestParam("recordId") Integer recordId){
            recordService.updateIsDone(isDone,recordId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST /record
     * 記録を登録する
     * @return void (status code 201)
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/record",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<Void> recordPost(@Valid @RequestBody RecordRequest recordRequest){
        RecordModel recordModel = new RecordModel(
                null,
                recordRequest.getRecordContentId(),
                Date.valueOf(recordRequest.getRecordDate()),
                recordRequest.getRequiredTime(),
                recordRequest.getMemo(),
                false,
                recordRequest.getCreateUserId()
        );
        recordService.createContent(recordModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * DELETE /record
     * 記録を削除する
     * @return void (status code 200)
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/record/{recordId}"
    )
    public ResponseEntity<Void> recordDelete(@NotNull @Valid @PathVariable("recordId") Long recordId){
        recordService.deleteContent(recordId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
