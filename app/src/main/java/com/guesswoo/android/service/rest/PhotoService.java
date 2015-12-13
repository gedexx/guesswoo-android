package com.guesswoo.android.service.rest;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = "http://api.guesswoo.com/photo", converters = {FormHttpMessageConverter.class,
        MappingJackson2HttpMessageConverter.class, ResourceHttpMessageConverter.class})
public interface PhotoService extends RestClientErrorHandling {

    @Get("/thumbnail/{filename}")
    @Accept(MediaType.IMAGE_JPEG)
    Resource getThumbnail(String filename);

    @Get("/{filename}")
    @Accept(MediaType.IMAGE_JPEG)
    Resource getPhoto(String filename);
}
