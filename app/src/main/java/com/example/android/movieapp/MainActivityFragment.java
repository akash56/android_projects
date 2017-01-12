package com.example.android.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater){
        menuInflater.inflate(R.menu.updatefragment, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.action_refresh){
            updateMovie();
        return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private AndroidFlavourAdapter adapt;

    private ArrayList<AndroidFlavour> flavourList;

    List<AndroidFlavour>  data=new ArrayList<AndroidFlavour>();
    AndroidFlavour[] dtta = {new AndroidFlavour("nightScene", "No.1", "kal"),
            new AndroidFlavour("loopHole", "No.2", "kal"),
            new AndroidFlavour("pieces", "No.3", "kal"),
            new AndroidFlavour("lake", "No.4", "kal"),
            new AndroidFlavour("scsad", "walkingman1", "No.5"),
            new AndroidFlavour("walkingman2", "No.6", "kal"),
            new AndroidFlavour("phonyman", "No.7", "kal"),
            new AndroidFlavour("corridor", "No.8", "kal"),
            new AndroidFlavour("dark", "No.9", "kal"),
            new AndroidFlavour("dog", "No.10", "kal")

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey("flavour")) {

            flavourList = new ArrayList<AndroidFlavour>(Arrays.asList(dtta));
        }

    else {
            flavourList=savedInstanceState.getParcelableArrayList("flavour");
        }
    setHasOptionsMenu(true);
    }

    @Override
public void onSaveInstanceState(Bundle outState){
outState.putParcelableArrayList("flavour", flavourList);
super.onSaveInstanceState(outState);
}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        adapt = new AndroidFlavourAdapter(getActivity(), data) ;


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.flavours_grid);
        gridView.setAdapter(adapt);


       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               AndroidFlavour flav = (AndroidFlavour) adapt.getItem(position);
               //int i=image.image;
               adapt.notifyDataSetChanged();

               Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra("p", flav);
               // int im = image.getImage();
               //intent.putExtra("img",im);
               //Toast.makeText(getContext(),"love is life"+, Toast.LENGTH_SHORT).show();
               startActivity(intent);
           }
       });

        return rootView;
    }


    //String category = "top_rated";
    public void updateMovie(){
        MyTask as = new MyTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String category1 = prefs.getString(getString(R.string.pref_category_key),"top_rated");
        as.execute(category1);

    }
    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    public class MyTask extends AsyncTask<String, Void, List<AndroidFlavour>> {

        private List<AndroidFlavour> getMovieDataFromJson(String category) throws JSONException {

            JSONObject movieJson = new JSONObject(category);
            JSONArray arr = movieJson.getJSONArray("results");
            List<AndroidFlavour> mv=new ArrayList<AndroidFlavour>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject movieObject = arr.getJSONObject(i);
                String plot = movieObject.getString("overview");
                String title = movieObject.getString("title");
                String releaseDate = movieObject.getString("release_date");
                double voteAverage = movieObject.getDouble("vote_average");
                String moviePoster = movieObject.getString("poster_path");

                mv.add(new AndroidFlavour(title, plot, moviePoster));
                //Log.d("json maa bigriyo ki",title);
                //data.add(title,plot,moviePoster);
                //mv.ad d(moviePoster);

            }

            return mv;

        }

        @Override
        protected List<AndroidFlavour> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String jsonValue = "";
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/" + params[0] + "?";
                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon().appendQueryParameter("api_key", BuildConfig.MOVIE_API_KEY).build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();

                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");

                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.

                    return null;
                }
                //Log.d("merobuffer", buffer.toString());
                jsonValue = buffer.toString();
                Log.e("k vayo vayo", jsonValue);

            } catch (MalformedURLException e) {

                Log.e("PlaceholderFragment", "Error ", e);
            } catch (IOException e) {
                Log.e("network error", "did something2", e);

            }


            try {
                return getMovieDataFromJson(jsonValue);
            } catch (JSONException e) {
                //Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<AndroidFlavour> arrayList) {
            if (arrayList != null) {
                adapt.clear();
                for (AndroidFlavour dayForecastStr : arrayList) {
                    adapt.add(dayForecastStr);
                }
            }
        }
    }
}






