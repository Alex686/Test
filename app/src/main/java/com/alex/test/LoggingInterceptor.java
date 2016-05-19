package com.alex.test;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import okio.Buffer;


//https://github.com/square/retrofit/issues/1072#

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        String requestLog = String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers());
        //YLog.d(String.format("Sending request %s on %s%n%s",
        //        request.url(), chain.connection(), request.headers()));
        if (request.method().compareToIgnoreCase("post") == 0) {
            requestLog = "\n" + requestLog + "\n" + bodyToString(request);
        }
        Log.d("TAG", "request" + "\n" + requestLog);

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        String responseLog = String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers());

        String bodyString = response.body().string();

        Log.d("TAG", "response log2222222" + "\n" + responseLog + "\n" + "response body1111111111" + bodyString);


/*
        //помещение Jsession id в переменную.
        Map<String, List<String>> map = response.headers().toMultimap();
        String setCookie = map.get("Set-Cookie").toString();
        String Jsessionid = setCookie.substring(12, 44);
        System.out.println("JSESSIONID: " + Jsessionid);
*/



        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
        //return response;
    }



    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }


}



















//

/*
class LoggingInterceptor implements Interceptor {
    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
       // logger.info(String.format("Sending request %s on %s%n%s",
          //      request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
       // logger.info(String.format("Received response for %s in %.1fms%n%s",
            //    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}

*/