package com.guesswoo.android.service.rest;

import com.guesswoo.android.GuessWooApplication;
import com.guesswoo.android.service.rest.response.MessageResponseTemp;
import com.guesswoo.api.dto.responses.GameResponse;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Put;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Rest(rootUrl = "http://api.guesswoo.com/s/games", converters = {FormHttpMessageConverter.class,
        MappingJackson2HttpMessageConverter.class})
public interface GameService extends RestClientErrorHandling {

    @Get("/")
    @Accept(MediaType.APPLICATION_JSON)
    @RequiresHeader(GuessWooApplication.X_TOKEN)
    List<GameResponse> getGames();

    @Get("/{username}/messages/")
    @Accept(MediaType.APPLICATION_JSON)
    @RequiresHeader(GuessWooApplication.X_TOKEN)
    List<MessageResponseTemp> getMessagesFromGame(String username);

    @Put("/{username}/messages/")
    @Accept(MediaType.APPLICATION_JSON)
    @RequiresHeader(GuessWooApplication.X_TOKEN)
    MessageResponseTemp sendMessage(String username, MultiValueMap<String, String> formData);

    void setHeader(String name, String value);
}

