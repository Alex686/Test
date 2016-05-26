package com.alex.test;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JCR_Activity extends AppCompatActivity {


    public static final String PREFS_NAME = "AdvancedReportingMobile";
    public static final String type = "saiku";
    public static final String uid = UUID.randomUUID().toString();
    //public static final String uid = "1464289742695" 13;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jcr_);
        final String[] sessionId = getSessionId();


        final ProgressDialog dialog = ProgressDialog.show(this, "", "loading...");
        JCR_Reques.JCRInterface service = JCR_Reques.getJcrInterface(sessionId[0]);

        Call<Void> call = service.getJCR(type, uid);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {


                    dialog.dismiss();
                System.out.println("JCRActivity!!!!!!!!!!!!!!!!!Status Code = " + response.code());
                    if (response.isSuccessful()) {
                        // request successful (status code 200, 201)
                       // String result = response.body();
                        System.out.println("JCRActivity11111111111111111response = " + response.body());


                    } else {
                        // response received but request not successful (like 400,401,403 etc)
                        //Handle errors
                        System.out.println("JCRActivity!!!!!!!!!!!!!!!!!!!!Status Code=" +response.code());

                    }
                }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                dialog.dismiss();
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




