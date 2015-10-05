package com.example.itunesfeedrssapp;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailedMediaActivity extends AppCompatActivity {

//    String title, artist, duration, category, release_Date, link, summary;
//    int price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_media);

        TextView artistText = (TextView) findViewById(R.id.artist_value);
        TextView titleText = (TextView) findViewById(R.id.app_title);
        TextView linkText = (TextView) findViewById(R.id.app_link_value);
        ImageView largeImage = (ImageView) findViewById(R.id.detailed_act_image_view);

        Log.d("demo", "inside of detailed on create");
        SharedPreferences settings = getSharedPreferences(MediaListActivity.PREFS_NAME, 0);
        String sharedPrefEntry = settings.getString("ENTRY_JSON", "nothing to see in entry json");
        Log.d("demo", "sharedPrefEntry: " + sharedPrefEntry);
        int intentIndex = this.getIntent().getIntExtra("INDEX", -1);

        try {
            JSONArray jsonEntry = new JSONArray(sharedPrefEntry);
            String title = jsonEntry.getJSONObject(intentIndex).getJSONObject("title").getString("label");
            titleText.setText(title);
            String artist = jsonEntry.getJSONObject(intentIndex).getJSONObject("im:artist").getString("label");
            artistText.setText(artist);
            String image_url = jsonEntry.getJSONObject(intentIndex).getJSONArray("im:image").getJSONObject(2).getString("label");
            Picasso.with(DetailedMediaActivity.this).load(image_url).into(largeImage);
            setLinkField(jsonEntry, intentIndex);

            setDurationField(jsonEntry, intentIndex);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setLinkField(JSONArray jsonEntry, int intentIndex) throws JSONException{

        TextView linkText = (TextView) findViewById(R.id.app_link_value);
        String link = "no link provided";

        Object linkObj = jsonEntry.getJSONObject(intentIndex).get("link");
        if (linkObj instanceof JSONArray) {
            Log.d("demo", "is a json array");
            link = jsonEntry.getJSONObject(intentIndex).getJSONArray("link").getJSONObject(0).getJSONObject("attributes").getString("href");
        } else if (linkObj instanceof JSONObject) {
            Log.d("demo", "is json object");
            link = jsonEntry.getJSONObject(intentIndex).getJSONObject("link").getJSONObject("attributes").getString("href");
        } else {
            Log.d("demo", "whoops!");
        }

        linkText.setText(link);
    }

    public void setDurationField(JSONArray jsonEntry, int intentIndex) throws JSONException {
        if(jsonEntry.getJSONObject(intentIndex).getJSONArray("link").getJSONObject(1).has("im:duration")) {
            String duration = jsonEntry.getJSONObject(intentIndex).getJSONArray("link").getJSONObject(1).getJSONObject("im:duration").getString("label");
            Log.d("demo", "duration: " + duration);
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.detailed_act_layout);
//        tv.setText(result.get(i));
//        horizontalLinearLayout.addView(tv);
            TextView durationView = new TextView(DetailedMediaActivity.this);
            durationView.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
            durationView.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
            durationView.setText("Duration: " + duration);
            layout.addView(durationView);

//            TextView durationText = new TextView(DetailedMediaActivity.this);
//            durationText.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
//            durationText.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
//            durationText.setText(duration);
//            layout.addView(durationText);
        }
    }

    public boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
