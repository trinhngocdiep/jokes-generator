package com.finx.demo.jokes.api;

import java.util.Collections;
import java.util.List;

public class JokesResponse {

    private final List<String> jokes;

    public JokesResponse() {
        jokes = Collections.emptyList();
    }

    public JokesResponse(List<String> jokes) {
        this.jokes = jokes;
    }

    public List<String> getJokes() {
        return jokes;
    }
}
