package com.alex.test;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

//import com.squareup.okhttp.HttpLoggingInterceptor;


public class Connector {

    private static GitApiInterface gitApiInterface ;
    public static final String URL = "https://revelreports.mera.ru";

    public static GitApiInterface conn() {
        if (gitApiInterface == null) {



            OkHttpClient okClient =getUnsafeOkHttpClient() ;
            okClient.interceptors().add((Interceptor) new LoggingInterceptor());



            okClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());

                    return response;
                }
            });

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(okClient)

                    //нащёл в интернете, но куча ошибок https://github.com/square/retrofit/issues/1554
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //.addConverter(String.class, new ToStringConverter())

            gitApiInterface = client.create(GitApiInterface.class);
        }
        return gitApiInterface ;
    }

    public interface GitApiInterface {

       @FormUrlEncoded
       @POST("/reporting/rest/saiku/session")
       Call<Void> login(@FieldMap Map<String, String> map);

        @GET("/reporting/rest/saiku/session")
        Call<Object> getSessionInfo();

/*
        @Headers("User-Agent: Retrofit2.0Tutorial-App")
        @GET("/search/users")
        Call<GitResult> getUsersNamedTom(@Query("q") String name);

        @POST("/user/create")
        Call<Item> createUser(@Body Item user);
        //Call<GitResult> createUser(@Field("name") String name, @Field("email") String email);
        // Call<Item> createUser(@Body String name, @Body String email);

        @PUT("/user/{id}/update")
        Call<Item> updateUser(@Path("id") String id , @Body Item user);
*/
    }
    public static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);



            OkHttpClient okHttpClient = new OkHttpClient();
           // okHttpClient.interceptors().add(new LoggingInterceptor(),interceptor);



                     //не понимаю что за logger      https://github.com/square/okhttp/wiki/Interceptors
                    //первый вариант вроде без ошибок, но не работает   https://github.com/square/retrofit/issues/1072
                    okHttpClient.interceptors().add((Interceptor) new LoggingInterceptor());

                    //как вариант не знаю почему не работает
                    //okHttpClient.interceptors().add(interceptor);
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    // TODO Auto-generated method stub
                    System.out.println("hostname = [" + hostname + "], session = [" + session + "]");
                    if (URL.contains(hostname))
                        return true;
                    else
                        return false;
                }
            });

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}


