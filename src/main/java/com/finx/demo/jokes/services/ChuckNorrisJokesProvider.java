package com.finx.demo.jokes.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finx.demo.jokes.config.AppConfig;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class ChuckNorrisJokesProvider implements JokesProvider {

    private final HttpClient httpClient;
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private static final URIBuilder BASE_URI;
    private static final String QUERY_PARAM = "query";
    private static final Logger LOGGER = LoggerFactory.getLogger(ChuckNorrisJokesProvider.class);

    static {
        try {
            BASE_URI = new URIBuilder("https://api.chucknorris.io/jokes/search");
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to construct base uri", e);
        }
    }

    @Inject
    public ChuckNorrisJokesProvider(AppConfig config, Environment environment) {
        httpClient = new HttpClientBuilder(environment).using(config.getHttpClientConfiguration()).build("httpClient");
    }

    @Override
    public List<String> getJokes(String keyword, int limit) {
        try {
            URI uri = BASE_URI.setParameter(QUERY_PARAM, keyword).build();
            HttpResponse apiResp = httpClient.execute(new HttpGet(uri));
            String json = EntityUtils.toString(apiResp.getEntity(), StandardCharsets.UTF_8);
            ChuckNorrisJokesResponse jokesResponse = objectMapper.readValue(json, ChuckNorrisJokesResponse.class);
            return jokesResponse.result.stream().limit(limit).map(e -> e.value).collect(Collectors.toList());
        } catch (URISyntaxException | IOException e) {
            LOGGER.error("Chuck Norris API did not response", e);
            return Collections.emptyList();
        }
    }

    private static class ChuckNorrisJokesResponse {
        public List<ChuckNorrisJoke> result;
    }

    private static class ChuckNorrisJoke {
        public String value;
    }

}
