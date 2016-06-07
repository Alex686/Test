package com.alex.test.Login;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.test.Menu.Menu;
import com.alex.test.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    public static final String JSESSIONID = "JSESSIONID";
    public static final String PREFS_NAME = "AdvancedReportingMobile";
    public static final String APP_PREFERENCES = "RepositoryHistory";

    private TextView tv;
    boolean isOnline;
    private EditText username;
    private EditText password;
    private String log,pass;
    private Map<String, String> mapJson = new HashMap<>();
    public String[] sessionId;
    SharedPreferences.Editor editor;
    SharedPreferences mSettings;



    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = mSettings.edit();
        editor.remove(APP_PREFERENCES);
        editor.commit();

        tv = (TextView) findViewById(R.id.tv);
        final Button jcr = (Button) findViewById(R.id.button);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        password.setOnKeyListener(new View.OnKeyListener() {
                                      public boolean onKey(View v, int keyCode, KeyEvent event) {
                                          if (event.getAction() == KeyEvent.ACTION_DOWN &&
                                                  (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                              jcr.requestFocus();
                                          }
                                          return false;
                                      }
                                  }
        );

         sessionId = getSessionId();

        //System.out.println("!!!" + sessionId[0]);
        final Connector.GitApiInterface service = Connector.conn(sessionId[0]);
        assert jcr != null;
        jcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cs = Context.CONNECTIVITY_SERVICE;
                ConnectivityManager cm = (ConnectivityManager)
                        getSystemService(cs);
                if (cm.getActiveNetworkInfo() == null) {
                    isOnline = false;
                } else {
                    isOnline = true;
                }
                if (!isOnline) {
                    Toast.makeText(getApplicationContext(),
                            "No internet connection!", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    log = username.getText().toString().trim();
                    pass = password.getText().toString().trim();


                    if (Objects.equals(log, "globaladmin") && Objects.equals(pass, "admin")) {
                        mapJson.put("username", log);
                        mapJson.put("password", pass);

                        Call<Map<String, Object>> sessionInfo = service.getSessionInfo();
                        final boolean[] isSessionActive = {false};
                        sessionInfo.enqueue(new Callback<Map<String, Object>>() {
                            @Override
                            public void onResponse(Call<Map<String, Object>> string, Response<Map<String, Object>> response) {
                                Map<String, Object> text = response.body() == null ? null : response.body();
                                if (text != null && text.size() > 1) {
                                    isSessionActive[0] = true;
                                }
                                System.out.println("99999999999999999999999999999999999" + response.body());
                            }


                            @Override
                            public void onFailure(Call<Map<String, Object>> string, Throwable t) {
                                //System.out.println("!!!" + t.getLocalizedMessage());

                            }
                        });

                        Call<Void> call = service.login(mapJson);
                        call.enqueue(new Callback<Void>() {
                            @TargetApi(Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onResponse(Call<Void> obj, Response<Void> response) {
                                List<String> strings = response.headers().toMultimap().get("Set-Cookie");

                                if (isSessionActive[0]) {
                                    tv.setText("Session id " + sessionId[0] + " exsists and active");
                                    //System.out.println("Session id " + sessionId[0] + " exsists and active");

                                    return;
                                }
                                if (strings == null) {
                                    return;
                                }
                                SESSION:
                                for (String string : strings) {
                                    if (!string.trim().isEmpty()) {
                                        String[] split = string.split(";");
                                        for (String s : split) {
                                            if (s != null && s.trim().startsWith(JSESSIONID) && s.contains("=")) {
                                                sessionId[0] = s.trim().split("=")[1];
                                                try (FileOutputStream fos = openFileOutput(PREFS_NAME, Context.MODE_PRIVATE)) {
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
                                //System.out.println("SessionId" +sessionId[0]);
                            }

                            @Override
                            public void onFailure(Call<Void> obj, Throwable t) {
                                tv.setText(t.getLocalizedMessage());
                                t.printStackTrace();
                            }


                        });

                        Intent intent = new Intent(MainActivity.this, Menu.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Your login or password was entered incorrectly!", Toast.LENGTH_LONG).show();
                    }

                }


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

