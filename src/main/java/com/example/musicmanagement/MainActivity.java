package com.example.musicmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    EditText edttim;
    ListView lv1,lv2,lv3;
    TabHost tab;
    ArrayList<Item> list1, list2,list3;
    ImageButton btnXoa;
    MyArrayAdapter myarray1,myarray2,myarray3;

    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database=null;
    public static String DATABASE_NAME="arirang.sqlite";
    public static String TABLE_NAME="ArirangSongList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processCopy();
        database = openOrCreateDatabase("arirang.sqlite", MODE_PRIVATE, null);
        addControl();
        addTim();
        addEvents();
      //  addFindNameSong();
    }
    private void addEvents() {
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {

                if(s.equalsIgnoreCase("t2"))
                {
                    addDanhsach();
                    Log.e("Error","tab2");
                }
                if(s.equalsIgnoreCase("t3"))
                {
                    addYeuthich();
                    Log.e("Error","tab3");
                }

            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edttim.setText("");
            }
        });



    }

        private void addYeuthich() {
        myarray3.clear();
        Cursor cursor= MainActivity.database.query(TABLE_NAME,
                null,"YEUTHICH=?",new String[]{"1"},null,null,null);
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
            list3.add(new Item(cursor.getString(1),cursor.getString(2),cursor.getInt(6)));
            cursor.moveToNext();
        }
        cursor.close();
        myarray3.notifyDataSetChanged();
    }

    private void addDanhsach(){
        myarray2.clear();
        Cursor cursor= database.query(TABLE_NAME,
                null,null,null,null,null,null);
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
            list2.add(new Item(cursor.getString(1),cursor.getString(2),cursor.getInt(6)));
            cursor.moveToNext();
        }
        cursor.close();
        myarray2.notifyDataSetChanged();
    }

        private void addTim() {
        edttim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String findWord=edttim.getText().toString();
                myarray1.clear();
                if (findWord.compareTo("")!=0){
                    Cursor cursor=MainActivity.database.
                            rawQuery("SELECT * FROM ArirangSongList WHERE TENBH1 LIKE '"+"%"+findWord+"%"+"' OR MABH LIKE '"+"%"+findWord+"%"+"'", null);
                    cursor.moveToFirst();
                    while (cursor.isAfterLast()==false){
                        list1.add(new Item(cursor.getString(1),cursor.getString(2),cursor.getInt(6)));
                        cursor.moveToNext();
                    }
                    cursor.close();
                    myarray1.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void processCopy() {
        File dbFile= getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()){
            try{
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;}
    private void CopyDataBaseFromAsset() {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            String outFileName=getDatabasePath();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void addControl() {
        btnXoa =(ImageButton) findViewById(R.id.btnXoa);
        tab=(TabHost)findViewById(R.id.tabhost); tab.setup();
        TabHost.TabSpec tab1=tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("",getResources().getDrawable(R.drawable.search));
        tab.addTab(tab1);
        TabHost.TabSpec tab2=tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("",getResources().getDrawable(R.drawable.list));
        tab.addTab(tab2);
        TabHost.TabSpec tab3=tab.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("",getResources().getDrawable(R.drawable.favourite));
        tab.addTab(tab3);
        edttim =(EditText) findViewById(R.id.edttim);
        lv1 = (ListView) findViewById(R.id.lv1);
        lv2 =(ListView) findViewById(R.id.lv2);
        lv3 = (ListView) findViewById(R.id.lv3);
        list1 =new ArrayList<Item>();
        list2 =new ArrayList<Item>();
        list3 =new ArrayList<Item>();
        myarray1 = new MyArrayAdapter(MainActivity.this, R.layout.my_list_view, list1);
        myarray2 = new MyArrayAdapter(MainActivity.this,R.layout.my_list_view, list2);
        myarray3 = new MyArrayAdapter(MainActivity.this,R.layout.my_list_view, list3);
        lv1.setAdapter(myarray1);
        lv2.setAdapter(myarray2);
        lv3.setAdapter(myarray3);



    }
}