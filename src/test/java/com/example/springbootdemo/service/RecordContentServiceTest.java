package com.example.springbootdemo.service;

import com.example.springbootdemo.model.RecordContentModel;
import com.example.springbootdemo.repository.RecordContentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecordContentServiceTest {

    @InjectMocks
    private RecordContentService recordContentService;

    @Mock
    private RecordContentRepository recordContentRepository;

    @Test
    public void getContent() {
        var recordContentRecord = new RecordContentModel(1,"record1");
        var recordContentRecordList = new ArrayList<>(List.of(recordContentRecord));
        Mockito.doReturn(recordContentRecordList).when(recordContentRepository).select();

        var expected = new ArrayList<>(List.of(new RecordContentModel(1, "record1")));

        assertEquals(expected, recordContentService.getContent());
    }

    @Test
    void createContent() {
        //リソース作成のみなのでテスト省略
    }

    @Test
    void deleteContent_要素あり() {
        //リソース削除のみなのでテスト省略
    }

    @Test
    void deleteContent_要素なし() {
        Mockito.doReturn(Optional.empty()).when(recordContentRepository).selectByRecordContentName("test");

        assertThrows(NoSuchElementException.class, () -> recordContentService.deleteContent("test"));
    }
}
