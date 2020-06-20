package com.example.yallachayaani;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MesOffres extends AppCompatActivity {
int userid;
    MyDB db=new MyDB(this);
    ListView l;
Cursor res;
    ArrayList al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_offres);
        userid = getIntent().getIntExtra("userId", 0);
        l = findViewById(R.id.list);
        final ArrayList<Offre> of = db.getMesOffres(userid);
        ArrayAdapter<Offre> adapter = new offreArrayAdapter(this, 0, of);
        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(MesOffres.this,Details.class);
                i.putExtra("off",of.get(position).getIdOff());
                i.putExtra("userOff",userid);
                i.putExtra("currentUser",userid);
                startActivity(i);
            }
        });
     }
}
