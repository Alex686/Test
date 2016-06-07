package com.alex.test.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alex.test.History.History;
import com.alex.test.Login.MainActivity;
import com.alex.test.R;
import com.alex.test.Repository.JCR_Activity;

public class Menu extends AppCompatActivity {


    private ListView listView1;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Menu_Item menuItem_data[] = new Menu_Item[]
                {
                        new Menu_Item(R.drawable.folder_multiple, "Repository"),
                        new Menu_Item(R.drawable.auto_fix, "Constructor query"),
                        new Menu_Item(R.drawable.calendar_clock, "History"),
                        new Menu_Item(R.drawable.exit_to_app, "Logout"),

                };

        MenuAdapter adapter = new MenuAdapter(this,
                R.layout.menu_item, menuItem_data);

        listView1 = (ListView)findViewById(R.id.list);

        View header = (View)getLayoutInflater().inflate(R.layout.menu_head, null);
        listView1.addHeaderView(header);

        listView1.setAdapter(adapter);




        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

                switch (position) {
                    case 1:
                        intent = new Intent(Menu.this, JCR_Activity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(),
                                "This feature is still in development", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        intent = new Intent(Menu.this, History.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(Menu.this, MainActivity.class);
                        startActivity(intent);
                        break;

                }


            }
        });


    }


}




