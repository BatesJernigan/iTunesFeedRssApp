package com.example.arjun.homework4;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    ArrayList<String> list_img = new ArrayList<>();
    List<String> samplelist = new ArrayList<String>();
    List<String> imglist = new ArrayList<String>();
    List<String> sample = new ArrayList<String>();


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

                for(int i = 0; i < entry.length(); i++) {
                    JSONObject titles = entry.getJSONObject(i);
                    JSONObject t = titles.getJSONObject("title");
                    String titleval2 = t.getString("label");
                    JSONObject img = entry.getJSONObject(i);
                    JSONArray t2 = img.getJSONArray("im:image");
                    JSONObject i2 = t2.getJSONObject(0);
                    String l = i2.getString("label");

                    list.add(i, titleval2);
                    //list_img.add(i , l);

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return list;
            //return list_img;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            if(result != null){
                Log.d("demo","result"+result.toString());
                samplelist = result;

            }

            progressDialog.dismiss();
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyDataList", MODE_PRIVATE);
            SharedPreferences img_pref = getApplicationContext().getSharedPreferences("MyImgList", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            Set<String> set = new HashSet<String>();

            set.addAll(samplelist);
            editor.putStringSet("list", set);
            editor.commit();

            set = pref.getStringSet("list", null);
            List<String> sample = new ArrayList<>(set);

            ScrollView sv = new ScrollView(MediaListActivity.this);
            sv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 250));
            LinearLayout rl = new LinearLayout(MediaListActivity.this);//this refers to current activity
            rl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            rl.setOrientation(LinearLayout.VERTICAL);
            sv.addView(rl);
            setContentView(sv);

            for(int i=0; i < sample.size(); i++) {
//                ImageView im = new ImageView(MediaListActivity.this);
//                Picasso.with(MediaListActivity.this).load(sample.get(i)).resize(100,100).centerCrop().into(im);
//                rl.addView(im);
                Log.d("demo", "the titles are" + " " + sample.get(i));
                TextView tv = new TextView(MediaListActivity.this);
                tv.setText(sample.get(i));
                rl.addView(tv);

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