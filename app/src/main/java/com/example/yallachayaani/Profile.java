package com.example.yallachayaani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Profile extends AppCompatActivity {
MyDB db=new MyDB(this);
ListView l;
    Drawable d;
    int id , idcurrent;
    ImageView i;
    Bitmap bitmap;
    byte[] img=null;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        i=findViewById(R.id.pic);
        // Get user id
        id =getIntent().getIntExtra("userId",0);
        idcurrent=getIntent().getIntExtra("currentUser",0);
        bitmap=db.getImage(id);
        i.setImageBitmap(bitmap);
        // Show profile in listview
        final ArrayList<String> ld = db.getProfile(id);
        ArrayAdapter ad = new ArrayAdapter(Profile.this, android.R.layout.simple_list_item_1, ld);
        l=findViewById(R.id.profiledes);
        l.setAdapter(ad);
    }

    public static Bitmap bytesToArray(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
