package com.example.yallachayaani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MesReservations extends AppCompatActivity {
    int userid;
    MyDB db=new MyDB(this);
    ListView l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_reservations);

        userid = getIntent().getIntExtra("userId", 0);
        l=findViewById(R.id.list);
        final ArrayList<Reservation> res = db.getMesRes(userid);
        ArrayAdapter<Reservation> adapter = new reservationArrayAdapter(this, android.R.layout.simple_list_item_1, res);
        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(MesReservations.this,Details.class);
                i.putExtra("off",res.get(position).getIdOff());
                i.putExtra("userOff",res.get(position).getIdUser());
                i.putExtra("currentUser",userid);
                startActivity(i);
            }
        });
        }

}
