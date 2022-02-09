package com.example.musicmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivityTab1 extends AppCompatActivity {
    TextView tvMaBh,tvTenBh,tvLoiBh,tvTacGia;
    ImageButton btn_sub_thich;
    Button btnExit;
    boolean love=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tab1);

        tvMaBh=findViewById(R.id.txt_sub_maBH);
        tvTenBh=findViewById(R.id.txt_sub_tenBH);
        tvLoiBh=findViewById(R.id.txt_sub_loiBh);
        tvTacGia=findViewById(R.id.txt_sub_tacGia);
        btn_sub_thich=findViewById(R.id.btn_sub_thich);
        btnExit=findViewById(R.id.btnBack);



        Intent subIntent=getIntent();
        Bundle bundle= subIntent.getBundleExtra("myBundle");
        String idSong=bundle.getString("maBaiHat");
        Log.e("MASo",idSong);
        tvMaBh.setText(idSong);
        Cursor cursor= MainActivity.database.query(MainActivity.TABLE_NAME,null,
                "MABH=?",new String[]{idSong},null,
                null,null);
//        Cursor cursor=MainActivity.database.rawQuery("SELECT * FROM ArirangSongList  WHERE MABH LIKE'"+
//                idSong+"'", null);
        cursor.moveToFirst();
            tvTenBh.setText(cursor.getString(2));
            tvLoiBh.setText(cursor.getString(3));
            tvTacGia.setText(cursor.getString(4));
            if (cursor.getInt(6)==0){
                btn_sub_thich.setImageResource(R.drawable.unlike);
                love=false;
            }
            else{
                btn_sub_thich.setImageResource(R.drawable.favourite);
            }

        cursor.close();


        btn_sub_thich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor=MainActivity.database.query(MainActivity.TABLE_NAME,
                        null,"MABH=?",new String[]{idSong},
                        null,null,null);
                cursor.moveToFirst();
                int tam=cursor.getInt(6);
                Log.e("Click:",tam+"");
                cursor.close();
                if (tam==1){
                    ContentValues contentValues= new ContentValues();
                    contentValues.put("YEUTHICH",0);
                    MainActivity.database.update(MainActivity.TABLE_NAME,
                            contentValues,"MABH=?",new String[]{idSong});
                    btn_sub_thich.setImageResource(R.drawable.unlike);
                }
                else{
                    ContentValues contentValues= new ContentValues();
                    contentValues.put("YEUTHICH",1);
                    MainActivity.database.update(MainActivity.TABLE_NAME,
                            contentValues,"MABH=?",new String[]{idSong});
                    btn_sub_thich.setImageResource(R.drawable.favourite);
                }
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailActivityTab1.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}