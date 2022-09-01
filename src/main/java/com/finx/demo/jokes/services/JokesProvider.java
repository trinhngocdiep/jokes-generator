package com.finx.demo.jokes.services;

import com.google.inject.ImplementedBy;

import java.util.List;

@ImplementedBy(ChuckNorrisJokesProvider.class)
public interface JokesProvider {

    /**
     * @param keyword the search keyword
     * @param limit the max number of jokes to be returned
     * @return a list of jokes related to the given keyword
     */
    List<String> getJokes(String keyword, int limit);

}
