package com.guesswoo.android.service.rest;

import com.guesswoo.android.service.rest.response.LoginResponse;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;

@Rest(rootUrl = "http://api.guesswoo.com/s/user", converters = {FormHttpMessageConverter.class,
        MappingJackson2HttpMessageConverter.class})
public interface UserService extends RestClientErrorHandling {

    @Post("/login/")
    @Accept(MediaType.APPLICATION_JSON)
    LoginResponse login(MultiValueMap<String, String> formData);

}

