package com.example.yallachayaani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import static com.example.yallachayaani.R.layout.*;

public class Accueil extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    int id;
    NavigationView navigationView;SharedPreferences prefs;
    ActionBarDrawerToggle toggle;
    ListView l;
    MyDB db = new MyDB(this);byte[] imgbyte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_accueil);
        imgbyte=getIntent().getByteArrayExtra("pic");
        // Afffichage des offres
        l=findViewById(R.id.list);
       final ArrayList<Offre> of = db.getListOff();
        ArrayAdapter<Offre> adapter = new offreArrayAdapter(this, android.R.layout.simple_list_item_1, of);
        l.setAdapter(adapter);

        // Get user id
         prefs = getSharedPreferences("X", MODE_PRIVATE);
         //  id=getIntent().getIntExtra("userId",0);
            id=prefs.getInt("user_id",0);

            drawerLayout = findViewById(R.id.drawer);
            toolbar = findViewById(R.id.toolbar);
         // Set header layout of nav bar Infos
            navigationView = findViewById(R.id.navigationView);
            View header=navigationView.getHeaderView(0);
            TextView name = (TextView) header.findViewById(R.id.name);
            ImageView i = (ImageView) header.findViewById(R.id.profilePic);
            name.setText(db.getUserName(id));
            Bitmap bitmap=db.getImage(id);
            i.setImageBitmap(bitmap);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);

            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);


        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idl) {
                Intent i=new Intent(Accueil.this,Details.class);
              i.putExtra("off",of.get(position).getIdOff());
                i.putExtra("userOff",of.get(position).getIdUser());
                i.putExtra("currentUser",id);
                startActivity(i);
            }
        });
    }
        // Menu
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.deconnecter:
                 {   saveUser(0);
                      //prefs = getSharedPreferences("X", MODE_PRIVATE);
                     Intent i= new Intent(Accueil.this,Authentifier.class);
                     startActivity(i);
                     finish();
                     prefs.edit().clear().commit();
                 }
                    break;
                case R.id.chercher:
                { Intent i= new Intent(Accueil.this,Chercher.class);
                    i.putExtra("userId",id);
                    startActivity(i);}
                    break;
                case R.id.ajouter:
                {Intent i= new Intent(Accueil.this,Ajouter.class);
                    i.putExtra("userId",id);
                startActivity(i);}
                    break;
                case R.id.mesres:
                {Intent i= new Intent(Accueil.this,MesReservations.class);
                    i.putExtra("userId",id);
                    startActivity(i);}
                    break;
                case R.id.mesoff:
                {Intent i= new Intent(Accueil.this,MesOffres.class);
                    i.putExtra("userId",id);
                    startActivity(i);}
                    break;
                case R.id.profile:
                {   Intent i= new Intent(Accueil.this,Profile.class);
                    i.putExtra("userId",id);
                    i.putExtra("pic",imgbyte);
                    startActivity(i);
                }
                break;
                case R.id.aboutt:
                {
                Intent i2= new Intent(Accueil.this, About.class);
                    startActivity(i2);
                }
                break;
                default:
                    break;
            }
            return false;
    }
    //Lancer l'app dans la page d'accueil
   /* @Override
    protected void onPause() {
        super.onPause();
        saveUser(id);
    }*/
    void saveUser(Integer id)
    {
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.putInt("user_id",id);
      //  editor.putBoolean("savedUser",true)
        editor.commit();
    }
}
