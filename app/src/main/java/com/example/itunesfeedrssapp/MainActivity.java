package com.example.itunesfeedrssapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    final static String URL = "URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView audio_books = (TextView) findViewById(R.id.audio_book_text_view);
        TextView books = (TextView) findViewById(R.id.books_text_view);
        TextView iOs_apps = (TextView) findViewById(R.id.ios_text_view);
        TextView mac_apps = (TextView) findViewById(R.id.mac_text_view);
        TextView movies = (TextView) findViewById(R.id.movies_text_view);
        TextView itunes = (TextView) findViewById(R.id.itunes_u_text_view);
        TextView podcast = (TextView) findViewById(R.id.pod_cast_text_view);
        TextView tvshow = (TextView) findViewById(R.id.tv_shows_text_view);

        if (isConnectedOnline()) {
            Toast.makeText(MainActivity.this, "Its Connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
        }

        audio_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MediaListActivity.class);
                intent.putExtra("URL","https://itunes.apple.com/us/rss/topaudiobooks/limit=25/json");
                startActivity(intent);
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MediaListActivity.class);
                intent.putExtra("URL","https://itunes.apple.com/us/rss/topfreeebooks/limit=25/json");
                startActivity(intent);
            }
        });

        iOs_apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MediaListActivity.class);
                intent.putExtra("URL","https://itunes.apple.com/us/rss/newapplications/limit=25/json");
                startActivity(intent);
            }
        });

        mac_apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MediaListActivity.class);
                intent.putExtra("URL","https://itunes.apple.com/us/rss/topfreemacapps/limit=25/json");
                startActivity(intent);
            }
        });

        movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MediaListActivity.class);
                intent.putExtra("URL","https://itunes.apple.com/us/rss/topmovies/limit=25/json");
                startActivity(intent);
            }
        });

        podcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MediaListActivity.class);
                intent.putExtra("URL","https://itunes.apple.com/us/rss/toppodcasts/limit=25/json");
                startActivity(intent);
            }
        });

        itunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MediaListActivity.class);
                intent.putExtra("URL","https://itunes.apple.com/us/rss/topitunesucollections/limit=25/json");
                startActivity(intent);
            }
        });

        tvshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MediaListActivity.class);
                intent.putExtra("URL","https://itunes.apple.com/us/rss/toptvepisodes/limit=25/json");
                startActivity(intent);
            }
        });
    }


    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ninfo = cm.getActiveNetworkInfo();
        if (ninfo != null && ninfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
