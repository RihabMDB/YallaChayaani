package com.example.yallachayaani;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;



import java.util.ArrayList;

public class Details extends AppCompatActivity implements ConfirmReseravtion.ConfirmReservationListener {
ListView l;
int idOff,iduserOff,currentUser;
    MyDB db = new MyDB(this);
    Button res;
    Button b;
    ImageView pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
       //Afficher les informations
        l=findViewById(R.id.list);
        b=findViewById(R.id.res);
        pic=findViewById(R.id.pic);

        idOff=getIntent().getIntExtra("off",0);
        iduserOff=getIntent().getIntExtra("userOff",0);
        currentUser=getIntent().getIntExtra("currentUser",0);
        pic.setImageBitmap(db.getImage(iduserOff));
        if (iduserOff==currentUser)  b.setVisibility(View.INVISIBLE);
        else  b.setVisibility(View.VISIBLE);
        ArrayList<String> ld =db.getDetailsOffr(idOff);
        ArrayAdapter ad = new ArrayAdapter(Details.this, android.R.layout.simple_list_item_1, ld);
        l.setAdapter(ad);


       //Reserver
        res=findViewById(R.id.res);

        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //Afficher une alter dialog
           ConfirmReseravtion cr=new ConfirmReseravtion();
           cr.show(getSupportFragmentManager(),"dialog");
            }
        });
    }
    //Fonction de boutton reserver dans alert dialog
    public void applyTexts(String nb) {
        boolean    isInserted = db.addRes(nb,idOff,currentUser);
        boolean inc=db.reserver(Integer.parseInt(nb));
        if (isInserted && inc)
            Toast.makeText(Details.this, "reservation with success", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(Details.this, "not reserved ", Toast.LENGTH_SHORT).show();

    }


    public void showprofile(View view) {
        Intent i=new Intent(Details.this,Profile.class);
        i.putExtra("userId",iduserOff);
        i.putExtra("currentuser",currentUser);
        startActivity(i);
    }
}
