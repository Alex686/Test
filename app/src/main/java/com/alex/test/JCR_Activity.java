package com.alex.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alex.test.forsaiku.AclMethod;
import com.alex.test.forsaiku.IRepositoryObject;
import com.alex.test.forsaiku.RepositoryFileObject;
import com.alex.test.forsaiku.RepositoryFolderObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCR_Activity extends AppCompatActivity {


    public static final String PREFS_NAME = "AdvancedReportingMobile";
    public static final String type = "saiku";
    public static final String uid = UUID.randomUUID().toString();
    //public static final String uid = "1464289742695" 13;

    List<IRepositoryObject> iRepositoryObjects = new ArrayList<>();//содержимое основной директории
   /* ArrayList<IRepositoryObject> iRepositoryObjects2 = new ArrayList<IRepositoryObject>();
    ArrayList<IRepositoryObject> iRepositoryObjects3 = new ArrayList<IRepositoryObject>();
    ArrayList<IRepositoryObject> iRepositoryObjects4 = new ArrayList<IRepositoryObject>();*/
    ListAdapter listAdapter;
    ListAdapter listAdapter2;
    String list, list2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jcr_);


        List<IRepositoryObject> repoObjects1 = new ArrayList<>();//содержимое первой паки
        List<IRepositoryObject> repoObjects2 = new ArrayList<>();//содержимое второй пакм
        List<IRepositoryObject> repoObjects3 = new ArrayList<>();//содержимое третьей папки

        List<AclMethod> aclMethodArrayList = new ArrayList<AclMethod>();
        AclMethod acl1 = AclMethod.GRANT;
        AclMethod acl2 = AclMethod.READ;
        aclMethodArrayList.add(acl1);
        aclMethodArrayList.add(acl2);

               IRepositoryObject file = new RepositoryFileObject("file1","1","type","/folder1",aclMethodArrayList);
        IRepositoryObject file2 = new RepositoryFileObject("file2","2","type","/folder1",aclMethodArrayList);
        IRepositoryObject file3 = new RepositoryFileObject("file3","3","type","/folder2",aclMethodArrayList);
        IRepositoryObject file4 = new RepositoryFileObject("file4","4","type","/folder2",aclMethodArrayList);
        IRepositoryObject file5 = new RepositoryFileObject("file5","5","type","/folder3",aclMethodArrayList);
        IRepositoryObject file6 = new RepositoryFileObject("file6","6","type","/folder3",aclMethodArrayList);
        IRepositoryObject file7 = new RepositoryFileObject("file7","7","type","/folder3",aclMethodArrayList);
        IRepositoryObject file8 = new RepositoryFileObject("file8","8","type","/folder3",aclMethodArrayList);
        IRepositoryObject file9 = new RepositoryFileObject("file9","9","type","/folder3",aclMethodArrayList);


        IRepositoryObject folder2 = new RepositoryFolderObject("folder2","2","forsaiku/folder1", aclMethodArrayList,repoObjects2);
        IRepositoryObject folder1 = new RepositoryFolderObject("folder1","1","forsaiku/", aclMethodArrayList,repoObjects1);
        IRepositoryObject folder3 = new RepositoryFolderObject("folder3","3","forsaiku/", aclMethodArrayList,repoObjects3);





        repoObjects1.add(file);
        repoObjects1.add(file2);
        repoObjects1.add(file6);
        repoObjects1.add(file7);
        repoObjects1.add(file8);
        repoObjects1.add(folder2);
        repoObjects2.add(file3);
        repoObjects2.add(file4);
        repoObjects3.add(file5);


        iRepositoryObjects.add(folder1);
        iRepositoryObjects.add(folder3);
        iRepositoryObjects.add(file9);

        listAdapter = new ListAdapter(this, (ArrayList<IRepositoryObject>) iRepositoryObjects);
       // listAdapter2 = new ListAdapter(this,repoObjects1);


        // настраиваем список
        final ListView lvMain = (ListView) findViewById(R.id.listView);
        assert lvMain != null;
        lvMain.setAdapter(listAdapter);
        //setListAdapter(listAdapter);


        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() { //7
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listAdapter.clickOnItem(position); //8
            }
        });
















































        /*
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
        */
    }

}




