package com.example.yallachayaani;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Authentifier extends AppCompatActivity {
    private EditText editText1, editText2;
    private Button button1, button2;
    TextView t;
    MyDB db = new MyDB(this);
    Cursor id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentifier);

            //Start Accueil activity


               /*
                try {
                    activityClass = Class.forName(
                            prefs.getString("lastActivity", Accueil.class.getName()));
                } catch (ClassNotFoundException e) {
                    activityClass = Accueil.class;
                }  Intent i=new Intent(this, activityClass);
       // i.putExtra("userId",id.getInt(0));
        startActivity(i);
                */

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        t = findViewById(R.id.text);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Authentifier.this, Inscrit.class);
                startActivity(in);
            }
        });
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        if (prefs.getInt("user_id",-1)>0) {
             startActivity(new Intent(this,Accueil.class));
        }else {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.getText().toString().isEmpty())
                 editText1.setError("Valider votre email !");
               else if (editText2.getText().toString().isEmpty())
                    editText2.setError("Valider votre mots de passe !");
                else {id=db.verifAuth(editText1.getText().toString(), editText2.getText().toString());
               if (id!=null)
              {
                  SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
                  SharedPreferences.Editor editor = prefs.edit();
                  editor.putString("lastActivity", getClass().getName());
                  editor.putInt("user_id",id.getInt(0));
                  editor.commit();
                    Intent i = new Intent(Authentifier.this, Accueil.class);
                    startActivity(i);
                  finish();}

                else   Toast.makeText(Authentifier.this,"echec de connexion",Toast.LENGTH_SHORT).show();
           }}
        });}
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Authentifier.this);
                builder
                        .setTitle("Are u sure to exit this app")
                        .setMessage("click yes to confirm")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               finish();
                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
    }

}