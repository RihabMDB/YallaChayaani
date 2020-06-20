package com.example.yallachayaani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Ajouter extends AppCompatActivity {
    public EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11;
    CheckBox c,f;
   public Button ajout;
   public Button annuler;

     MyDB db = new MyDB(this);
    private static final String TAG = "Ajouter";
    TimePickerDialog timePickerDialog;
    EditText chooseTime;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;
    private EditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);
       e1=findViewById(R.id.e1);
        e2=findViewById(R.id.e2);
        e3=findViewById(R.id.e3);
        e4=findViewById(R.id.e4);
        e5=findViewById(R.id.e5);
        e6 =findViewById(R.id.e6);
        e7=findViewById(R.id.e7);
        e8=findViewById(R.id.e8);
        e9=findViewById(R.id.e9);
        e10=findViewById(R.id.e10);
        e11=findViewById(R.id.e11);
        c=findViewById(R.id.cli);
        f=findViewById(R.id.fum);
        annuler=findViewById(R.id.an);
      ajout=findViewById(R.id.aj);
      userid=getIntent().getIntExtra("userId",0);

      // Date Picker
        mDisplayDate = (EditText) findViewById(R.id.e3);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Ajouter.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
//TimePicker
         chooseTime = findViewById(R.id.e4);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(Ajouter.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        chooseTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });


        // Remplisage de bd
        ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           // getcheckbox
                String cl,fu;
               //get climatisée
                if (c.isChecked())
                    cl="Voiture climatisée";
                else cl="Voiture non climatisée";
                if (f.isChecked())
                    fu="Choufeur fumeur";
                else fu="Choufeur non fumeur";

                boolean    isInserted = db.addOffre(e1.getText().toString(), e2.getText().toString(),e3.getText().toString(),
                        e4.getText().toString(),e5.getText().toString(), e6.getText().toString(), e7.getText().toString()
                        , e8.getText().toString(), e9.getText().toString(), e10.getText().toString(), e11.getText().toString(),cl,fu,userid);
                if (isInserted==true)
                    Toast.makeText(Ajouter.this, "offre ajouter avec success", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Ajouter.this, "echec d'ajout d'offre ", Toast.LENGTH_SHORT).show();

            }
        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Ajouter.this,MesOffres.class);
                startActivity(i);
            }
        });
    }
    }

