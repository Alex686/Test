package com.alex.test;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import retrofit.Converter;

public final class ToStringConverter implements Converter<String> {


    @Override
    public String fromBody(ResponseBody body) throws IOException {
        if (body.contentLength() == 0) return null;
        return body.toString();


    }

    @Override
    public RequestBody toBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }
}