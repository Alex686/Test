package com.alex.test.History;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alex.test.R;
import com.alex.test.forsaiku.IRepositoryObject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    List<IRepositoryObject> iRepositoryObjects = new ArrayList<>();//содержимое истории
    IRepositoryObject iRepositor;
    public static final String APP_PREFERENCES = "RepositoryHistory";
    SharedPreferences mSettings;
    HistoryListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);




        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if(mSettings.contains(APP_PREFERENCES)) {
            iRepositoryObjects = new Gson().fromJson(mSettings.getString(APP_PREFERENCES, ""), ArrayList.class);
            System.out.println("iRepositoryhistory:" +iRepositoryObjects);
        }









        listAdapter = new HistoryListAdapter(this, (ArrayList<IRepositoryObject>) iRepositoryObjects);




        final ListView lvMain = (ListView) findViewById(R.id.listhistory);
        assert lvMain != null;


        lvMain.setAdapter(listAdapter);



        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() { //7
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listAdapter.clickOnItem(position); //8

            }
        });

    }
}
