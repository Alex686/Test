package com.alex.test;

import java.lang.reflect.Type;

import retrofit.Converter;

public class NullOnEmptyConverterFactory implements Converter.Factory {
    @Override
    public Converter<?> get(Type type) {
        return null;
    }

    /*@Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.converterFactories(this, type, annotations);//nextResponseBodyConverter(this, type, annotations);
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody body) throws IOException {
                if (body.contentLength() == 0) return null;
                return delegate.convert(body);                }
        };
    }*/
}