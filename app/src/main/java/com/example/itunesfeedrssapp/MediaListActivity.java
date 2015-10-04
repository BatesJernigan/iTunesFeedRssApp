package com.example.itunesfeedrssapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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


public class MediaListActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_list);

        String url = getIntent().getExtras().getString(MainActivity.URL);
        Log.d("demo", url);
        new ProgressTask().execute(url);

    }

    private class ProgressTask extends AsyncTask<String , Void , ArrayList<String>> {


        @Override
        protected ArrayList<String> doInBackground(String... params) {

            JSONParser jParser = new JSONParser();
            try {
                publishProgress();
                JSONObject root =  jParser.getJSONFromUrl(params[0]);
                JSONObject feed2 = root.getJSONObject("feed");
                JSONArray entry = feed2.getJSONArray("entry");

                for(int i = 0; i < entry.length(); i = i+2) {
                    String title = entry.getJSONObject(i).getJSONObject("title").getString("label");
                    String imgUrl = entry.getJSONObject(i).getJSONArray("im:image").getJSONObject(0).getString("label");

                    list.add(i, title);
                    list.add((i + 1), imgUrl);
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
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if(result != null) {
                Log.d("demo", "result: "+result.toString());
                // what is img_pref this used for?
                //SharedPreferences img_pref = getApplicationContext().getSharedPreferences("MyImgList", MODE_PRIVATE);

                // do you have a link to explain all of this? I don't really understand it and
                // would love some clarity
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyDataList", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                Set<String> set = new HashSet<>();
                set.addAll(result);
                editor.putStringSet("list", set);
                editor.commit();

                // this set is never used? also
//                set = pref.getStringSet("list", null);
//                ArrayList<String> sample = new ArrayList<>(set);

//                ScrollView scrollView = new ScrollView(MediaListActivity.this);
//                scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                LinearLayout horizontalLinearLayout = new LinearLayout(MediaListActivity.this);
//                horizontalLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
//                scrollView.addView(horizontalLinearLayout);
//                setContentView(scrollView);

                LinearLayout verticalLayout = (LinearLayout) findViewById(R.id.child_vertical_layout);

                //changed this to result instead of sample instead of result because the ordering
                // was being messed up.
                for(int i=0; i < result.size(); i = i+2) {
                    LinearLayout horizontalLinearLayout = new LinearLayout(MediaListActivity.this);
                    horizontalLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    horizontalLinearLayout.setPadding(10, 10, 10, 10);

                    ImageView im = new ImageView(MediaListActivity.this);
                    Picasso.with(MediaListActivity.this).load(result.get(i+1)).resize(100, 100).centerCrop().into(im);
                    horizontalLinearLayout.addView(im);

                    TextView tv = new TextView(MediaListActivity.this);
                    tv.setText(result.get(i));
                    horizontalLinearLayout.addView(tv);

                    horizontalLinearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo", "clicked a horizontal view");
                        }
                    });

                    verticalLayout.addView(horizontalLinearLayout);
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

    protected void testMethod() {

    }
}
