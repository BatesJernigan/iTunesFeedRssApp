package com.example.itunesfeedrssapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class MediaListActivity extends AppCompatActivity {
    public final static String PREFS_NAME = "iTunesJSON";
    ProgressDialog progressDialog;
    SharedPreferences settings;
    ArrayList<String> list;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_list);

        int timeInMilliseconds = 2*60*1000;

        final String url = getIntent().getExtras().getString(MainActivity.URL);
        new ProgressTask().execute(url);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(Looper.myLooper() == null) {
                    Looper.prepare();
                }

                SharedPreferences.Editor removePrefs = settings.edit();
                removePrefs.clear();
                removePrefs.commit();

                new ProgressTask().execute(url);
            }
        }, timeInMilliseconds, timeInMilliseconds);
    }

    private class ProgressTask extends AsyncTask<String , Void , ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {

            JSONParser jParser = new JSONParser();
            settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor jsonEntry = settings.edit();
            list = new ArrayList<>();

            try {
                JSONObject root =  jParser.getJSONFromUrl(params[0]);
                JSONObject feed2 = root.getJSONObject("feed");
                JSONArray entry = feed2.getJSONArray("entry");
                jsonEntry.putString("FULL_JSON", root.toString());
                jsonEntry.putString("ENTRY_JSON", entry.toString());
                jsonEntry.commit();

                // Title, Large Image, Artist, Duration, Artist, Category, release Date and link
                for(int i = 0; i < entry.length(); i = i+2) {
                    String title = entry.getJSONObject(i).getJSONObject("title").getString("label");
                    int imgArrayLength = entry.getJSONObject(i).getJSONArray("im:image").length();
                    String thumbImgUrl = entry.getJSONObject(i).getJSONArray("im:image").getJSONObject(0).getString("label");

                    list.add(i, title);
                    list.add((i + 1), thumbImgUrl);
                    publishProgress();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // this returns an array list of the form (title1, imag_url1, title2, img_url2, etc..)
            return list;
        }

        @Override
        protected void onPostExecute(final ArrayList<String> result) {
            final ArrayList<String> resultCopy = result;
            super.onPostExecute(result);
            progressDialog.dismiss();
            if(result != null) {
                Log.d("demo", "result: "+result.toString());
                // what is img_pref this used for?
                //SharedPreferences img_pref = getApplicationContext().getSharedPreferences("MyImgList", MODE_PRIVATE);

                // do you have a link to explain all of this? I don't really understand it and
                // would love some clarity
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyDataList", MODE_PRIVATE);
                final SharedPreferences.Editor editor = pref.edit();
                HashSet<String> set = new HashSet<>();
                set.addAll(result);
                editor.putStringSet("list", set);
                editor.commit();

                final LinearLayout verticalLayout = (LinearLayout) findViewById(R.id.child_vertical_layout);
                verticalLayout.removeAllViews();

                // changed this to result instead of sample instead of result because the ordering
                // was being messed up.
                for(int i=0; i < result.size(); i = i+2) {
                    final int j = i;
                    LinearLayout horizontalLinearLayout = new LinearLayout(MediaListActivity.this);
                    horizontalLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    horizontalLinearLayout.setPadding(10, 10, 10, 10);

                    ImageView im = new ImageView(MediaListActivity.this);
                    Picasso.with(MediaListActivity.this).load(result.get(i+1)).resize(100, 100).centerCrop().into(im);
                    horizontalLinearLayout.addView(im);

                    TextView tv = new TextView(MediaListActivity.this);
                    tv.setText(result.get(i));
                    horizontalLinearLayout.addView(tv);
                    verticalLayout.addView(horizontalLinearLayout);

                    horizontalLinearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MediaListActivity.this, DetailedMediaActivity.class);
                            intent.putExtra("INDEX", j);
                            startActivity(intent);
                        }
                    });


                    horizontalLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            verticalLayout.removeView(v);

//                            none of the below works
//                            int id = v.getId();
//                            Log.d("demo", "id: " + id);
//                            result.remove(id);
//                            Set<String> set = new HashSet<>(result);
//                            editor.putStringSet("list",set);
//                            editor.commit();
                            return true;
                        }
                    });
                }
            }
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
