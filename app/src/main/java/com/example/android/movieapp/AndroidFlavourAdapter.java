package com.example.android.movieapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by akash on 11/3/16.
 */
public class AndroidFlavourAdapter extends ArrayAdapter<AndroidFlavour> {

   Context context;
    public AndroidFlavourAdapter(Activity context, List<AndroidFlavour> androidFlavours) {
        super(context, 0, androidFlavours);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AndroidFlavour androidFlavour = getItem(position);
        if (convertView == null) {
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.list_item_flavour,parent,false);

        }

        //TextView textView=(TextView) convertView.findViewById(R.id.text_flavor);
        ImageView iconView=(ImageView) convertView.findViewById(R.id.flavour_image);
        //textView.setText("love");///+androidFlavour.getImage());

        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+androidFlavour.getImage()).into(iconView);

        //Picasso.with(getContext()).load(androidFlavour.getImage()).into(iconView);
        //iconView.setImageResource(androidFlavour.image);
        //TextView textView=(TextView) convertView.findViewById(R.id.flavour_text);
        //textView.setText(androidFlavour.versionname+" - "+androidFlavour.versionnumber);
    return convertView;
    }
}
