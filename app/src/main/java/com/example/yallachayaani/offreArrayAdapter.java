package com.example.yallachayaani;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class offreArrayAdapter  extends ArrayAdapter {
    private Context context;
    private List<Offre> rentalProperties;

    //constructor, call on creation
    public offreArrayAdapter(Context context, int resource, ArrayList<Offre> objects) {
        super(context, resource, objects);

        this.context = context;
        this.rentalProperties = objects;
    }

    //called when rendering the list
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the property we are displaying
        Offre property = rentalProperties.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.content_off_layout, null);

        TextView chouffeur = (TextView) view.findViewById(R.id.chouffeur);
        TextView dep = (TextView) view.findViewById(R.id.depart);
        TextView des = (TextView) view.findViewById(R.id.des);
       ImageView image = (ImageView) view.findViewById(R.id.image);

        //set address and description
        String completeAddress = property.getName() + " " + property.getDep() + ", " + property.getDes() ;
       // address.setText(completeAddress);

        //display trimmed excerpt for description
      //  int descriptionLength = property.getDescription().length();
      //  if(descriptionLength >= 100){
         /*   String descriptionTrim = property.getDescription().substring(0, 100) + "...";
            description.setText(descriptionTrim);
        }else{
            description.setText(property.getDescription());
        }*/

        //set price and rental attributes
        chouffeur.setText("Covoitureur : " + property.getName());
        dep.setText("Depart : " + property.getDep());
       des.setText("Destination: " + property.getDes());
       // carspot.setText("Car: " + String.valueOf(property.getCarspots()));

        //get the image associated with this property
        //int imageID = context.getResources().getIdentifier(property.getImg(), "drawable", context.getPackageName());

        byte [] imageID=property.getImg();
        //setImageViewWithByteArray(image,imageID);
       if(imageID!=null){
        Bitmap bm = BitmapFactory.decodeByteArray(imageID, 0, imageID.length);
        DisplayMetrics dm = new DisplayMetrics();
        //activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        image.setImageBitmap(bm);}
       else {

          image.setImageResource(R.drawable.ic_account_circle_black_24dp);
        }


    //    image.setImageResource(imageID);
// Convert bytes data into a Bitmap

        return view;
    }
    public static void setImageViewWithByteArray(ImageView view, byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        view.setImageBitmap(bitmap);
    }
}
