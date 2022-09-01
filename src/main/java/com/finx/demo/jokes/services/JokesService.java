package com.finx.demo.jokes.services;

import javax.inject.Inject;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JokesService {

    private final JokesProvider jokesProvider;

    @Inject
    public JokesService(JokesProvider jokesProvider) {
        this.jokesProvider = jokesProvider;
    }

    public List<String> findJokes(String keyword) {
        List<String> jokes = jokesProvider.getJokes(keyword);
        Pattern keywordPattern = Pattern.compile(String.format("\\b%s\\b", keyword));
        return jokes.stream().filter(e -> keywordPattern.matcher(e).find()).collect(Collectors.toList());
    }

}

