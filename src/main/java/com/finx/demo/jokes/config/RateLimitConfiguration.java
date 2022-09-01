package com.finx.demo.jokes.config;

public class RateLimitConfiguration {
    private int keywordRepeatCount;
    private int keywordRepeatWindowInSeconds;

    public int getKeywordRepeatCount() {
        return keywordRepeatCount;
    }

    public void setKeywordRepeatCount(int keywordRepeatCount) {
        this.keywordRepeatCount = keywordRepeatCount;
    }

    public int getKeywordRepeatWindowInSeconds() {
        return keywordRepeatWindowInSeconds;
    }

    public void setKeywordRepeatWindowInSeconds(int keywordRepeatWindowInSeconds) {
        this.keywordRepeatWindowInSeconds = keywordRepeatWindowInSeconds;
    }
}
