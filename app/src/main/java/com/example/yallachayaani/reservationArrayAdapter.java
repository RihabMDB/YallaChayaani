package com.example.yallachayaani;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class reservationArrayAdapter extends ArrayAdapter {

    private Context context;
    private List<Reservation> rentalProperties;

    //constructor, call on creation
    public reservationArrayAdapter(Context context, int resource, ArrayList<Reservation> objects) {
        super(context,resource,objects);

        this.context = context;
        this.rentalProperties = objects;
    }

    //called when rendering the list
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the property we are displaying
        Reservation property = rentalProperties.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.content_reservation, null);

        TextView chouffeur = (TextView) view.findViewById(R.id.chouffeur);
        TextView dep = (TextView) view.findViewById(R.id.depart);
        TextView des = (TextView) view.findViewById(R.id.des);
        TextView nb = (TextView) view.findViewById(R.id.nb);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        //set price and rental attributes
        chouffeur.setText("Chouffeur : " + property.getName());
        dep.setText("Depart : " + property.getDep());
        des.setText("Destination: " + property.getDes());
        nb.setText("Nombre de place reservé :  " + property.getNb());
        byte [] imageID=property.getImg();
        if(imageID!=null){
            Bitmap bm = BitmapFactory.decodeByteArray(imageID, 0, imageID.length);
            image.setImageBitmap(bm);}
        else {

            image.setImageResource(R.drawable.ic_account_circle_black_24dp);
        }

        return view;
    }

}
