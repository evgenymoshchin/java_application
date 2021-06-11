package com.gmail.evgenymoshchin.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ArticleSummaryMakerImplTest {

    @InjectMocks
    ArticleSummaryMakerImpl summaryMaker;

    @Test
    void shouldReturnNotNullSummary() {
        String articleBody = "body";
        String summary = summaryMaker.getSummaryOfArticle(articleBody);

        assertNotNull(summary);
    }

    @Test
    void shouldReturnCorrectSummary() {
        String articleBody = "body";
        String summary = summaryMaker.getSummaryOfArticle(articleBody);

        assertEquals(articleBody, summary);
    }

    @Test
    void shouldNotReturnSummaryWithInvalidBody() {
        String articleBody = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
        String summary = summaryMaker.getSummaryOfArticle(articleBody);

        assertNotEquals(articleBody, summary);
    }

}