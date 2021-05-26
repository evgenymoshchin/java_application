package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.service.ArticleSummaryMaker;
import org.springframework.stereotype.Component;

import static com.gmail.evgenymoshchin.service.constants.ArticleSummaryMakerConstants.MAX_SUMMARY_CHARS_VALUE;
import static com.gmail.evgenymoshchin.service.constants.ArticleSummaryMakerConstants.MIN_SUMMARY_CHARS_VALUE;

@Component
public class ArticleSummaryMakerImpl implements ArticleSummaryMaker {

    @Override
    public String getSummaryOfArticle(String articleBody) {
        if (articleBody.chars().count() <= MAX_SUMMARY_CHARS_VALUE) {
            return articleBody;
        } else {
            return articleBody.subSequence(MIN_SUMMARY_CHARS_VALUE, MAX_SUMMARY_CHARS_VALUE).toString();
        }
    }
}
