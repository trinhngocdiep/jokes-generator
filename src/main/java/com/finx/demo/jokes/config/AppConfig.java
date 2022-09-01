package com.finx.demo.jokes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AppConfig extends Configuration {

    private int maxJokesCount = 3;

    @Valid
    @NotNull
    private HttpClientConfiguration httpClient = new HttpClientConfiguration();

    private RateLimitConfiguration rateLimitConfiguration = new RateLimitConfiguration();

    @JsonProperty("httpClient")
    public HttpClientConfiguration getHttpClientConfiguration() {
        return httpClient;
    }

    @JsonProperty("httpClient")
    public void setHttpClientConfiguration(HttpClientConfiguration httpClient) {
        this.httpClient = httpClient;
    }

    @JsonProperty("rateLimit")
    public RateLimitConfiguration getRateLimitConfiguration() {
        return rateLimitConfiguration;
    }

    @JsonProperty("rateLimit")
    public void setRateLimitConfiguration(RateLimitConfiguration rateLimitConfiguration) {
        this.rateLimitConfiguration = rateLimitConfiguration;
    }

    public int getMaxJokesCount() {
        return maxJokesCount;
    }

    public void setMaxJokesCount(int maxJokesCount) {
        this.maxJokesCount = maxJokesCount;
    }
}
