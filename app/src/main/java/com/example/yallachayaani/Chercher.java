package com.example.yallachayaani;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Chercher extends AppCompatActivity {
EditText dp,ds;
DatePicker date;
Button chercher ;
ListView l;
    MyDB db = new MyDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chercher);
        dp=findViewById(R.id.dep);
        ds=findViewById(R.id.des);
        chercher=findViewById(R.id.cherch);
        l=findViewById(R.id.list);
        date=findViewById(R.id.date);
        int   day  = date.getDayOfMonth();
        int   month= date.getMonth() + 1;
        int   year = date.getYear();
        final String d=day+"/"+month+"/"+year;


        chercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> res = db.ChercherOffre(dp.getText().toString(),ds.getText().toString(),d);
                ArrayAdapter ad=new ArrayAdapter(Chercher.this,android.R.layout.simple_list_item_1,res);
                l.setAdapter(ad);
            }
        });
    }
}
