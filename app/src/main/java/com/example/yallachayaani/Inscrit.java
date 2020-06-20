package com.example.yallachayaani;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Inscrit extends AppCompatActivity {
    byte[] imageBytes = null;
    Uri uri;
    ImageView img;
    boolean open=false;
    String  genre;
    RadioButton homme , femme;
    public EditText editText1,editText2,editText3,editText4,editText5,editText6;
    public Button b1,b2;
    MyDB db = new MyDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscrit);
        img       = findViewById(R.id.img);
        editText1=findViewById(R.id.mail);
        editText2=findViewById(R.id.name);
        editText3=findViewById(R.id.fullname);
        editText4=findViewById(R.id.numtel);
        editText5=findViewById(R.id.password);
        editText6=findViewById(R.id.confirmpassword);
        homme =findViewById(R.id.homme);
        femme =findViewById(R.id.femme);

        b1=findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                addUSer();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        b2=findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Inscrit.this,Authentifier.class);
                startActivity(i);
            }
        });
    }

    // ouvrir gallery pour choisir la photo
    public void openGallery( ){
        open=true;
        Intent intgallery=new Intent(Intent.ACTION_GET_CONTENT);
        intgallery.setType("image/");
        startActivityForResult(intgallery,100);
    }


    //changer ImageView par la photo choisi apatrir de galerie
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==100) {
            uri=data.getData();
            Bitmap decodestream = null;
            try{ InputStream inputStream= getContentResolver().openInputStream(uri);
                 decodestream= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(decodestream);
                Bitmap image = ((BitmapDrawable) img.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                imageBytes = stream.toByteArray();
                //imgString= Base64.encodeToString(imageBytes,Base64.DEFAULT);
                 }
            catch (Exception ex){
                Log.e("ex",ex.getMessage());}
        }}
    @SuppressLint("NewApi")
    public void addUSer(){
        if (editText1.getText().toString().isEmpty())
        editText1.setError(" Entrer votre email !");
        else if (editText2.getText().toString().isEmpty())
            editText2.setError(" Entrer votre nom !");
        else if (editText3.getText().toString().isEmpty())
            editText3.setError(" Entrer votre prénom !");
        else if (!homme.isChecked()&& !femme.isChecked())
            homme.setError("Choisir votre sexe !");
        else if (editText4.getText().toString().isEmpty())
          editText4.setError( "Entrer votre numéro du téléphone !");
         else if (editText4.getText().toString().trim().length()!=8)
            editText4.setError( "numéro du téléphone doit contient 8 nombre!");
        else if (editText5.getText().toString().isEmpty())
            editText5.setError( "Entrer votre mots de passe !");
        else if (editText6.getText().toString().isEmpty())
            editText6.setError( "Confirmer votre mots de passe !");
        else if (!editText6.getText().toString().equals(editText5.getText().toString()))
            editText6.setError( "Valider votre mots de passe !");

        else {
            if (!open){ imageBytes=getLocalPic(getDrawable(R.drawable.user));}
            if (homme.isChecked())   genre="Homme";
            else genre="Femme";
            boolean isInserted= db.addUser(editText1.getText().toString(),editText2.getText().toString(),editText3.getText().toString(),
                    editText4.getText().toString(),editText5.getText().toString(),imageBytes,genre);
            Cursor c=db.verifAuth(editText1.getText().toString(),editText5.getText().toString());

            if (isInserted && c!=null)
            { Toast.makeText(Inscrit.this, "inscription avec  success", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Inscrit.this, Accueil.class);
                i.putExtra("pic",imageBytes);
                SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("user_id",c.getInt(0));
                editor.commit();
                startActivity(i);}
            else
                Toast.makeText(Inscrit.this, " inscription failed ", Toast.LENGTH_SHORT).show();
        }
    }
    byte[] getLocalPic(Drawable d)
    {
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }
}
