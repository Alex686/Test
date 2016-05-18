package com.alex.test;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;
import retrofit.Retrofit;


public class NullOnEmptyConverterFactory implements Converter.Factory {
    @Override
    public Converter<?> get(Type type) {
        return null;
    }


    public Converter<?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //final Converter<?> delegate = retrofit.converterFactories(this, type, annotations);//nextResponseBodyConverter(this, type, annotations);
        return new Converter<String>() {
            @Override
            public String fromBody(ResponseBody body) throws IOException {
                if (body.contentLength() == 0) return null;
                else {return body.toString();}
            }

            @Override
            public RequestBody toBody(String value) {
                return null;
            }


            public Object convert(ResponseBody body) throws IOException {
                if (body.contentLength() == 0) return null;
                return body.toString();}
                //delegate.convert(body);
        };
    }
}