package com.alex.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Gson gson = new GsonBuilder().create();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);

        Connector.GitApiInterface service = Connector.conn();

        Call<Object> sessionInfo = service.getSessionInfo();
        sessionInfo.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Response<Object> response) {
                printResponse(response);

                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + response.toString());

            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + t.getLocalizedMessage());

            }
        });
        Map<String, String> mapJson = new HashMap<>();
//        mapJson.put("language", "ru");
        mapJson.put("username", "globaladmin");
        mapJson.put("password", "admin");
        Call<Void> call = service.login(mapJson);



/*
        try {
            Response<Object> response = call.execute();
            Map<String,String> map =gson.fromJson(response.body().toString(), Map.class);
            for (Map.Entry e :map.entrySet()) {
                System.out.println(e.getKey() + " " + e.getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
            tv.setText(e.getLocalizedMessage());
        }
*/

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response) {
                System.out.println("response = [" + response.headers().toString() + "]");
            }

            @Override
            public void onFailure(Throwable t) {
                tv.setText(t.getLocalizedMessage());
                t.printStackTrace();
            }


        });


    }

    private void printResponse(Response<Object> response) {
        Log.d("MainActivity", "Status Code = " + response.code());
        if (response.isSuccess()) {
            // request successful (status code 200, 201)

           // Map<String, String> map = gson.fromJson(response.body().toString(), Map.class);
            System.out.println(response.body().toString());
            //tv.setText(response.body().toString());
/*
            for (Map.Entry e :map.entrySet()) {
                System.out.println(e.getKey() + " " + e.getValue());
            }*/


        } else {
            // response received but request not successful (like 400,401,403 etc)
            //Handle errors

        }
    }


}

