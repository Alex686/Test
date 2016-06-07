package com.alex.test.Login;


import android.annotation.SuppressLint;

import com.alex.test.Jackson.JacksonConverterFactory;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

//import com.squareup.okhttp.HttpLoggingInterceptor;


public class Connector {

    public static final String COOKIE = "Cookie";
    private static GitApiInterface gitApiInterface;
    public static final String URL = "https://revelreports.mera.ru";

    public static GitApiInterface conn(final String sessionId) {
        if (gitApiInterface == null) {


            OkHttpClient okClient = getUnsafeOkHttpClient(sessionId);
//            okClient.interceptors().add((Interceptor) new LoggingInterceptor());


            Retrofit client = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(okClient)


                    //нащёл в интернете, но куча ошибок https://github.com/square/retrofit/issues/1554
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();


            gitApiInterface = client.create(GitApiInterface.class);
        }
        return gitApiInterface;
    }

    public interface GitApiInterface {

        @FormUrlEncoded
        @POST("/reporting/rest/saiku/session")
        Call<Void> login(@FieldMap Map<String, String> map);

        @GET("/reporting/rest/saiku/session")
        Call<Map<String, Object>> getSessionInfo();
    }

    public static OkHttpClient getUnsafeOkHttpClient(final String sessionId) {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.sslSocketFactory(sslSocketFactory);
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return URL.contains(hostname);
                }
            });
            okHttpClientBuilder.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request.Builder request = originalRequest.newBuilder();
                    String cookie = originalRequest.header(COOKIE);
                    if (cookie != null && !cookie.trim().isEmpty()) {
                        cookie = MainActivity.JSESSIONID + "=" + sessionId + ";" + cookie;
                    } else {
                        cookie = MainActivity.JSESSIONID + "=" + sessionId + ";";
                    }
                    request.addHeader(COOKIE, cookie);
                    Request build = request.build();
                    System.out.println("build = " + build);
                    return chain.proceed(build);
                }
            });
            return okHttpClientBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}


