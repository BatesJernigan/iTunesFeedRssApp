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
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailedMediaActivity extends AppCompatActivity {
    int indexToInsetView = 4;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_media);

        TextView artistText = (TextView) findViewById(R.id.artist_text_view);
        TextView titleText = (TextView) findViewById(R.id.app_title);
        ImageView largeImage = (ImageView) findViewById(R.id.detailed_act_image_view);
        params.setMargins(6, 6, 6, 6);

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
            artistText.setText("By: " + artist);
            String image_url = jsonEntry.getJSONObject(intentIndex).getJSONArray("im:image").getJSONObject(2).getString("label");
            Picasso.with(DetailedMediaActivity.this).load(image_url).into(largeImage);
            setLinkField(jsonEntry, intentIndex);

            // these may or may not be added depending on if the field exists
            setDurationField(jsonEntry, intentIndex);
            setCategoryField(jsonEntry, intentIndex);
            setReleaseDateField(jsonEntry, intentIndex);
            setSummaryField(jsonEntry, intentIndex);
            setPriceField(jsonEntry, intentIndex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setPriceField(JSONArray jsonEntry, int intentIndex) throws JSONException {
        if(jsonEntry.getJSONObject(intentIndex).has("im:price")) {
            String price = jsonEntry.getJSONObject(intentIndex).getJSONObject("im:price").getString("label");
            Log.d("demo", "price: " + price);
            LinearLayout layout = (LinearLayout) findViewById(R.id.detailed_act_layout);
            TextView priceView = new TextView(DetailedMediaActivity.this);
            priceView.setLayoutParams(params);
            priceView.setText("Price: " + price);
            layout.addView(priceView, indexToInsetView);
            indexToInsetView++;
        }
    }

    private void setSummaryField(JSONArray jsonEntry, int intentIndex) throws JSONException {

        if(jsonEntry.getJSONObject(intentIndex).has("summary")) {
            String summary = jsonEntry.getJSONObject(intentIndex).getJSONObject("summary").getString("label");
            Log.d("demo", "summary: " + summary);
            TextView categoryView = new TextView(DetailedMediaActivity.this);
            LinearLayout layout = (LinearLayout) findViewById(R.id.detailed_act_layout);
            categoryView.setLayoutParams(params);
            categoryView.setText("Summary:\n" + summary);
            layout.addView(categoryView, indexToInsetView);
            indexToInsetView++;
        }
    }

    private void setReleaseDateField(JSONArray jsonEntry, int intentIndex) throws JSONException {

        if(jsonEntry.getJSONObject(intentIndex).has("im:releaseDate")) {
            String date = jsonEntry.getJSONObject(intentIndex).getJSONObject("im:releaseDate").getJSONObject("attributes").getString("label");
            Log.d("demo", "date: " + date);
            TextView dateView = (TextView) findViewById(R.id.app_release);
            dateView.setText(date);
        }
    }

    private void setCategoryField(JSONArray jsonEntry, int intentIndex) throws JSONException {
        if(jsonEntry.getJSONObject(intentIndex).has("category")) {
            String category = jsonEntry.getJSONObject(intentIndex).getJSONObject("category").getJSONObject("attributes").getString("label");
            Log.d("demo", "category: " + category);
            LinearLayout layout = (LinearLayout) findViewById(R.id.detailed_act_layout);
            TextView categoryView = new TextView(DetailedMediaActivity.this);
            categoryView.setLayoutParams(params);
            categoryView.setText("Category: " + category);
            layout.addView(categoryView, indexToInsetView);
            indexToInsetView++;
        }
    }

    private void setLinkField(JSONArray jsonEntry, int intentIndex) throws JSONException{
        TextView linkText = (TextView) findViewById(R.id.app_link_text_view);
        String link = "App Link in Store:\n";
        Object linkObj = jsonEntry.getJSONObject(intentIndex).get("link");
        if (linkObj instanceof JSONArray) {
            Log.d("demo", "is a json array");
            link += jsonEntry.getJSONObject(intentIndex).getJSONArray("link").getJSONObject(0).getJSONObject("attributes").getString("href");
        } else if (linkObj instanceof JSONObject) {
            Log.d("demo", "is json object");
            link += jsonEntry.getJSONObject(intentIndex).getJSONObject("link").getJSONObject("attributes").getString("href");
        } else {
            Log.d("demo", "whoops!");
        }

        linkText.setText(link);
    }

    private void setDurationField(JSONArray jsonEntry, int intentIndex) throws JSONException {
        Object jsonObj = jsonEntry.getJSONObject(intentIndex).get("link");
        if(jsonObj instanceof JSONArray && jsonEntry.getJSONObject(intentIndex).getJSONArray("link").getJSONObject(1).has("im:duration")) {
            int durationMilliseconds = jsonEntry.getJSONObject(intentIndex).getJSONArray("link").getJSONObject(1).getJSONObject("im:duration").getInt("label");
            String duration = durationMilliseconds / 1000 +"";
            Log.d("demo", "duration: " + duration);
            LinearLayout layout = (LinearLayout) findViewById(R.id.detailed_act_layout);

            TextView durationView = new TextView(DetailedMediaActivity.this);
            durationView.setLayoutParams(params);
            durationView.setText("Duration: " + duration + "sec");

            layout.addView(durationView, indexToInsetView);
            indexToInsetView++;
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
