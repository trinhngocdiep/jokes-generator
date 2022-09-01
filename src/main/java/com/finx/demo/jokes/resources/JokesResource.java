package com.finx.demo.jokes.resources;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.finx.demo.jokes.config.AppConfig;
import com.finx.demo.jokes.api.JokesResponse;
import com.finx.demo.jokes.services.ApiHitCounter;
import com.finx.demo.jokes.services.JokesService;
import io.dropwizard.jersey.caching.CacheControl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Path("/jokes")
@Produces(MediaType.APPLICATION_JSON)
public class JokesResource {

    private final JokesService jokesService;
    private final ApiHitCounter counter;
    private final AppConfig appConfig;

    @Inject
    public JokesResource(JokesService jokesService, ApiHitCounter counter, AppConfig appConfig) {
        this.jokesService = jokesService;
        this.counter = counter;
        this.appConfig = appConfig;
    }

    @GET
    @Timed(name = "get-requests-timed")
    @Metered(name = "get-requests-metered")
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    public JokesResponse getJokes(@QueryParam("keyword") String keyword) {
        if (counter.increase(keyword) > appConfig.getRateLimitConfiguration().getKeywordRepeatCount()) {
            throw new BadRequestException("You have been telling the same jokes too many times. Try another one!");
        }
        List<String> jokes = jokesService.findJokes(keyword);
        return new JokesResponse(jokes);
    }

}
