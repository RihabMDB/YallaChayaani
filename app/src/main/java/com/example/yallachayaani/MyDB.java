package com.example.yallachayaani;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "chayaanidb.db";
    public static final Integer DB_Version = 1;
   // Table offre
    public static final String TABLE_Off = "Offre";
    public static final String IDOff = "id";
    public static final String DEBUTOff = "debut";
    public static final String FINOff = "fin";
    public static final String DATEOff = "date";
    public static final String TIMEOff = "time";
    public static final String PRIXOff = "prix";
    public static final String NBPLACEOff = "nbplace";
    public static final String MARQUEOff = "marque";
    public static final String MATRICULEOff = "matricule";
    public static final String VITESSEOff = "vitesse";
    public static final String  ESCALEOff ="escale";
    public static final String BAGAGEOff = "bagage";
    public static final String CLIMATOff = "climat";
    public static final String FUMEUROff = "fumeur";

    //Table Reservation
    public static final String TABLE_Res= "reservation";
    public static final String IDRes = "idres";
    public static final String NBPLACERes = "nbplaceres";

    //table user
    public static final String IDUser = "id_user";
    public static final String Num_Tel = "num_tel";
    public static final String TABLE_User ="User";
    public static final String Name = "name";
    public static final String FullName = "fullname";
    public static final String Mail = "mail";
    public static final String Password = "password";
    public static final String Image = "image";
    public static final String Sexe = "sexe";

    public MyDB(Context context) {
        super(context, DB_NAME, null, DB_Version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_Off  +"( "+IDOff+" Integer primary key AUTOINCREMENT, "+DEBUTOff+" TEXT, "+FINOff+" TEXT, "+DATEOff+" TEXT, "+TIMEOff+" TEXT, "+PRIXOff+" TEXT, "+NBPLACEOff+" TEXT, "+MARQUEOff+" TEXT, "+MATRICULEOff+" TEXT, "+VITESSEOff+" TEXT, "+ESCALEOff+" TEXT, "+BAGAGEOff+" TEXT, "+CLIMATOff+" TEXT, "+FUMEUROff+" TEXT, "+IDUser+" Integer)");
        db.execSQL("create table "+ TABLE_Res +"( "+IDRes+" Integer primary key AUTOINCREMENT, "+NBPLACERes+" TEXT, "+IDOff+" Integer, "+IDUser+" Integer)");
        db.execSQL("create table " + TABLE_User + "( "+IDUser+" Integer primary key AUTOINCREMENT, "+Mail+" VARCHAR  UNIQUE, " +Name+" Text, "+FullName +" TEXT, "+Num_Tel+ " Text, " + Password + " TEXT, "+ Image  +" BLOB, "+ Sexe +" Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Off);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Res);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_User);
        onCreate(db);
    }

    public boolean addOffre(String debut, String fin, String d, String t, String prix, String nbplace, String mrq, String matricule, String es,String bagage ,String vitesse, String c,String f,int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEBUTOff, debut);
        contentValues.put(FINOff, fin);
        contentValues.put(DATEOff, d);
        contentValues.put(TIMEOff, t);
        contentValues.put(PRIXOff, prix);
        contentValues.put(NBPLACEOff, nbplace);
        contentValues.put(MARQUEOff, mrq);
        contentValues.put(MATRICULEOff, matricule);
        contentValues.put(ESCALEOff, es);
        contentValues.put(BAGAGEOff, bagage);
        contentValues.put(VITESSEOff, vitesse);
        contentValues.put(CLIMATOff, c);
        contentValues.put(FUMEUROff, f);
        contentValues.put(IDUser, userid);
        long result = db.insert(TABLE_Off, null, contentValues);
        if (result == -1) return false;
        else return true;
    }
    public boolean addRes(String nb,Integer idOf, Integer idUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NBPLACERes, nb);
        contentValues.put(IDOff, idOf);
        contentValues.put(IDUser, idUser);
        long result = db.insert(TABLE_Res, null, contentValues);
        if (result == -1) return false;
        else return true;
    }
    public boolean addUser(String mail,String nom, String prenom, String tel, String mdp, byte[] img,String g) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Mail, mail);
        contentValues.put(Name, nom);
        contentValues.put(FullName, prenom);
        contentValues.put(Num_Tel, tel);
        contentValues.put(Password, mdp);
        contentValues.put(Image, img);
        contentValues.put(Sexe, g);
        long result = db.insert(TABLE_User, null,contentValues);
        if (result == -1) return false;
        else return true;
    }
    public Cursor verifAuth(String e,String p) {SQLiteDatabase db = this.getReadableDatabase();

        Cursor res=db.rawQuery("select "+IDUser+" from "+TABLE_User +" where mail='"+e+"'"+" and password='"+p+"'",null);
        if (res.moveToFirst())
            return  res;
        else return null;
    }
    public ArrayList ChercherOffre(String d,String f,String dat) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor res=db.rawQuery("select*from "+TABLE_Off +" Where 'debut' Like '"+d+"' and 'fin' Like '"+f+"' and 'date' Like '"+dat+"'",null);
        res.moveToFirst();
        while (res.isAfterLast()==false)
        { String t1 = res.getString(1);
            String t2 = res.getString(2);
            String t3 = res.getString(3);
            String t4 = res.getString(4);
            arrayList.add("Depart: "+t1+" Destination: "+t2+" Date de sortir: "+t3+" Temps: "+t4+"  Clicker pour voir plus de details !");
            res.moveToNext();}
        return arrayList;
    }

    public ArrayList<Offre> getListOff() {
        ArrayList<Offre> ittoentity = new ArrayList<Offre>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select*from "+TABLE_Off ;
        Log.d("rawquery", "inputs"+selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {int id=cursor.getInt(14);
                Cursor ch=db.rawQuery("select name,fullname , image from "+TABLE_User +" Where id_user="+id,null);
                ch.moveToFirst();
                String n=ch.getString(0);
                String fn=ch.getString(1);
                byte[] img=ch.getBlob(ch.getColumnIndex(Image));
                Offre itto = new Offre(img,n+" "+fn,cursor.getString(1),cursor.getString(2));
                itto.setIdOff(cursor.getInt(0));
                itto.setIdUser(id);
                ittoentity.add(itto);
            } while (cursor.moveToNext());
        }

        return ittoentity;
    }

    public ArrayList getDetailsOffr(int i) { SQLiteDatabase db = this.getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor res=db.rawQuery("select*from "+TABLE_Off +" Where id="+i,null);
        res.moveToFirst();
        while (res.isAfterLast()==false)
        { String t1 = res.getString(1);
            String t2 = res.getString(2);
            String t3 = res.getString(3);
            String t4 = res.getString(4);
            String t5 = res.getString(5);
            String t6 = res.getString(6);
            String t7 = res.getString(7);
            String t8 = res.getString(8);
            String t9 = res.getString(9);
            String t10 = res.getString(10);
            String t11 = res.getString(11);
            String t12 = res.getString(12);
            String t13 = res.getString(13);
            int t14 = res.getInt(14);

            Cursor ch=db.rawQuery("select name,fullname from "+TABLE_User +" Where id_user="+t14,null);
            ch.moveToFirst();
            String n=ch.getString(0);
            String fn=ch.getString(1);
            arrayList.add(" Choufeur :  "+n+" "+fn+"\n Depart :   "+t1+"\n Destination : "+t2+"\n Date de sortir: "+t3+"\n Temps :   "+t4+"\n Nombre de places disponible : "+t6+"\n Marque de voiture : "+t7+"\n Matricule :   "+t8+"\n Vitesse de choufeure : "+t9+"\n Nombre d'excale : "+t10+"\n Quantité de bagage : "+t11+"\n "+t12+"\n "+t13+"\n Tarif :   "+t5);
            res.moveToNext();}

        return arrayList;
    }

    public ArrayList getProfile(int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor res=db.rawQuery("select id_user, name, fullname, num_tel, mail, sexe  from "+TABLE_User+" where id_user="+i ,null);
        res.moveToFirst();
        while (res.isAfterLast()==false)
        {
            String t0 = res.getString(0);
            String t1 = res.getString(1);
            String t2 = res.getString(2);
            String t3 = res.getString(3);
            String t4 = res.getString(4);
            String t5 = res.getString(5);
           arrayList.add("\nNom : "+t1+"\n \nPrénom : "+t2+"\n \nSexe : "+t5+"\n \nEmail : "+t4+"\n \nNuméro du téléphone : "+t3);
            res.moveToNext();
        }
        return arrayList;
    }

    public Bitmap getImage(int i) {
        byte[] image ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res=db.rawQuery("select "+ Image+" from "+TABLE_User+" where "+IDUser+"="+i  ,null);
       if( res.moveToFirst())
        { image = res.getBlob(0);
            ByteArrayInputStream imageStream = new ByteArrayInputStream(image);
        res.close();
            return BitmapFactory.decodeStream(imageStream);
        }
        else return null ;
    }

    public ArrayList<Offre> getMesOffres(int userid) {
        ArrayList<Offre> ittoentity = new ArrayList<Offre>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select*from "+TABLE_Off +" where "+IDUser+"="+userid,null);
        if (cursor.moveToFirst()) {
            do {
                Cursor ch=db.rawQuery("select name,fullname , image from "+TABLE_User +" Where id_user="+userid,null);
                ch.moveToFirst();
                String n=ch.getString(0);
                String fn=ch.getString(1);
                byte[] img=ch.getBlob(ch.getColumnIndex(Image));
                Offre itto = new Offre(img,n+" "+fn,cursor.getString(1),cursor.getString(2));
                itto.setIdOff(cursor.getInt(0));
                itto.setIdUser(userid);
                ittoentity.add(itto);
            } while (cursor.moveToNext());
        }

        return ittoentity;
    }

    public ArrayList<Reservation> getMesRes(Integer userid) {
        ArrayList<Reservation> ittoentity = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select*from "+TABLE_Res +" where "+IDUser+"="+userid,null);
        if (cursor.moveToFirst()) {
            do {Cursor off=db.rawQuery("select "+DEBUTOff+" , "+ FINOff+","+IDUser+" from "+TABLE_Off +" Where id="+cursor.getInt(2),null);
                off.moveToFirst();
                int iduser=off.getInt(2);
                Cursor ch=db.rawQuery("select name,fullname , image from "+TABLE_User +" Where id_user="+iduser,null);
                ch.moveToFirst();
                String n=ch.getString(0);
                String fn=ch.getString(1);

                byte[] img=ch.getBlob(ch.getColumnIndex(Image));
                Reservation itto = new Reservation(img,n+" "+fn,off.getString(0),off.getString(1),cursor.getInt(1));
                itto.setIdOff(cursor.getInt(0));
                itto.setIdUser(iduser);
                itto.setIdOff(cursor.getInt(2));
                itto.setIdResr(cursor.getInt(0));
                ittoentity.add(itto);
            } while (cursor.moveToNext());
        }

        return ittoentity;
    }

    public boolean updateUser(int n){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("update "+TABLE_User+" set "+Name+"="+n);
        return true;
    }

    public boolean reserver(int n){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor nb=db.rawQuery("select "+NBPLACEOff+" from "+TABLE_Off,null);
        nb.moveToFirst();
        int nbr=nb.getInt(0)-n;
        //Cursor res=db.rawQuery(,null);
        db.execSQL("update "+TABLE_Off+" set "+NBPLACEOff+"="+nbr);
        return true;
    }
}