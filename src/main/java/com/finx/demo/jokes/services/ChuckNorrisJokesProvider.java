package com.finx.demo.jokes.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finx.demo.jokes.config.AppConfig;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

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
    private static final ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    @Inject
    public ChuckNorrisJokesProvider(AppConfig config, Environment environment) {
        httpClient = new HttpClientBuilder(environment).using(config.getHttpClientConfiguration()).build("httpClient");
    }

    @Override
    public List<String> getJokes(String keyword) {
        try {
            URI uri = new URIBuilder("https://api.chucknorris.io/jokes/search").setParameter("query", keyword).build();
            HttpResponse response = httpClient.execute(new HttpGet(uri));
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            ChuckNorrisJokesResponse jokesResponse = objectMapper.readValue(json, ChuckNorrisJokesResponse.class);
            return jokesResponse.result.stream().map(e -> e.value).collect(Collectors.toList());
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
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
