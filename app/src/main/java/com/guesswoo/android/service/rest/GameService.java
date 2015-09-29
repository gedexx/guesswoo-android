package com.guesswoo.android.service.rest;

import com.guesswoo.android.service.rest.response.GameResponse;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@Rest(rootUrl = "http://api.guesswoo.com/s/games", converters = {FormHttpMessageConverter.class,
        MappingJackson2HttpMessageConverter.class})
public interface GameService extends RestClientErrorHandling {

    @Get("/")
    @Accept(MediaType.APPLICATION_JSON)
    @RequiresHeader("X-Token")
    List<GameResponse> getGames();

    void setHeader(String name, String value);
}

