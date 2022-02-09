package com.example.musicmanagement;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Item> {

    Activity context = null;
    ArrayList<Item> myArray = null;
    int idLayout;


    public MyArrayAdapter(@NonNull Activity context, int idLayout, @NonNull ArrayList<Item> arr) {
        super(context, idLayout, arr);
        this.context=context;
        this.idLayout=idLayout;
        this.myArray=arr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(idLayout,null);

        final Item myItem = myArray.get(position);

        final TextView tieude = convertView.findViewById(R.id.txttieude);
        tieude.setText(myItem.getTieude());

        final TextView maso = convertView.findViewById(R.id.txtmaso);
        maso.setText(myItem.getMaso());

        final ImageView btnlike = convertView.findViewById(R.id.btnUnlike);

        int thich = myItem.getThich();
        if (thich==0)
        {
            btnlike.setImageResource(R.drawable.unlike);
        }else
        {
            btnlike.setImageResource(R.drawable.love);
        }
        btnlike.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Cursor cursor=MainActivity.database.query(MainActivity.TABLE_NAME,
                        null,"MABH=?",new String[]{maso.getText().toString()},
                        null,null,null);
                cursor.moveToFirst();
                int tam=cursor.getInt(6);
                String s_tam=tam+"";
                Log.e("click:",s_tam);

                // nếu đang thích
                if ( myItem.getThich()==1){
                    ContentValues contentValues= new ContentValues();
                    contentValues.put("YEUTHICH",0);
                    myItem.setThich(0);
                    btnlike.setImageResource(R.drawable.unlike);
                    MainActivity.database.update(MainActivity.TABLE_NAME,contentValues,
                            "MABH=?",new String[]{maso.getText().toString()});
                }
                else{
                    ContentValues contentValues= new ContentValues();
                    contentValues.put("YEUTHICH",1);
                    myItem.setThich(1);
                    btnlike.setImageResource(R.drawable.love);
                    MainActivity.database.update(MainActivity.TABLE_NAME,contentValues,
                            "MABH=?",new String[]{maso.getText().toString()});
                }
            }
        });

    tieude.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tieude.setTextColor(Color.RED);
            maso.setTextColor(Color.RED);

            Intent intent= new Intent(context,DetailActivityTab1.class);
            Bundle myBundle= new Bundle();
            myBundle.putString("maBaiHat",maso.getText().toString());
            intent.putExtra("myBundle",myBundle);
            context.startActivity(intent);
        }
    });

        return convertView;



    }
}

