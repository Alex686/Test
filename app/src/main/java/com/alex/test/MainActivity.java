package com.alex.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    public static final String JSESSIONID = "JSESSIONID";
    public static final String PREFS_NAME = "AdvancedReportingMobile";
    private TextView tv;



    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        final String[] sessionId = getSessionId();

        System.out.println("!!!" + sessionId[0]);
        Connector.GitApiInterface service = Connector.conn(sessionId[0]);

        Call<Map<String, Object>> sessionInfo = service.getSessionInfo();
        final boolean[] isSessionActive = {false};
        sessionInfo.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> string, Response<Map<String, Object>> response) {
                Map<String, Object> text = response.body()==null? null: response.body();
                if (text != null && text.size()>1) {
                    isSessionActive[0] = true;
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> string, Throwable t) {
                System.out.println("!!!" + t.getLocalizedMessage());

            }
        });
            Map<String, String> mapJson = new HashMap<>();
            mapJson.put("username", "globaladmin");
            mapJson.put("password", "admin");
            Call<Void> call = service.login(mapJson);
            call.enqueue(new Callback<Void>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<Void> obj, Response<Void> response) {
                    List<String> strings = response.headers().toMultimap().get("Set-Cookie");

                    if(isSessionActive[0]) {
                        tv.setText("Session id " + sessionId[0] + " exsists and active");
                        return;
                    }
                    if (strings== null ){
                        return;
                    }
                    SESSION:
                    for (String string : strings) {
                        if (!string.trim().isEmpty()) {
                            String[] split = string.split(";");
                            for (String s : split) {
                                if (s != null && s.trim().startsWith(JSESSIONID) && s.contains("=")) {
                                    sessionId[0] = s.trim().split("=")[1];
                                    try (FileOutputStream fos = openFileOutput(PREFS_NAME, Context.MODE_PRIVATE)){
                                        fos.write(sessionId[0].getBytes());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    break SESSION;
                                }
                            }
                        }
                    }
                    tv.setText(sessionId[0]);
                }

                @Override
                public void onFailure(Call<Void> obj, Throwable t) {
                    tv.setText(t.getLocalizedMessage());
                    t.printStackTrace();
                }


            });


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String[] getSessionId() {
        final String[] sessionId = {null};
        boolean isExists = false;
        for (String s : fileList()) {
            if(PREFS_NAME.equals(s)){
                isExists = true;
                break;
            }
        }
        if (!isExists) {
            return new String[1];
        }
        try (FileInputStream fileInputStream = openFileInput(PREFS_NAME)){
            byte[] buffer = new byte[9000];
            int lenght = fileInputStream.read(buffer);
            sessionId[0] = new String(buffer, 0, lenght);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionId;
    }
}

