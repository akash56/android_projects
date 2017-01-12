package com.example.android.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    Context context;
    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null){// & intent.hasExtra("p")) {
           AndroidFlavour flintent=(AndroidFlavour) intent.getExtras().getParcelable("p");
            ImageView ic= (ImageView) rootView.findViewById(R.id.image_detail);
            TextView tx=(TextView) rootView.findViewById(R.id.title_detail);
            TextView pl=(TextView) rootView.findViewById(R.id.plot_detail);
            tx.setText(flintent.getTitle());
            pl.setText(flintent.getPlot());
            Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" + flintent.getImage()).into(ic);       }
        return rootView;
    }


}
