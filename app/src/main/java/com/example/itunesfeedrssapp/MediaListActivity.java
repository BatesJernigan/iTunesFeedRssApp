package com.example.itunesfeedrssapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class MediaListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_list);
        Log.d("demo", "this in media list: " + this.getIntent());
        String url = this.getIntent().getExtras().getString(MainActivity.URL);
        Log.d("demo", "url " + url);
        new GetMediaAsync().execute(url);
    }

    public class GetMediaAsync extends AsyncTask<String, Void, ArrayList<Media>> {

        ProgressDialog progressDialog;

        @Override
        protected ArrayList<Media> doInBackground(String... params) {
            SharedPreferences sharedPreferences;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                publishProgress();
                int statusCode = con.getResponseCode();
                if(statusCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = reader.readLine();
                    while(line != null) {
                        sb.append(line);
                        line = reader.readLine();
                    }
                    sharedPreferences = getSharedPreferences("feed", 0);
                    String test = sharedPreferences.getString("feed", "nothing to see here");
                    Log.d("demo", "shared Preferences " + test);
                    Log.d("demo",sb.toString());

                    return MediaUtil.MediaJSONParser.parseMedia(sb.toString());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Media> result) {
            super.onPostExecute(result);
            if(result != null){
                Log.d("demo",result.toString());
            }
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MediaListActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading apps ...");
            progressDialog.show();
        }
    }
}
