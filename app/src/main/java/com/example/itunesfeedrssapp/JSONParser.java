package com.example.arjun.homework4;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by arjun on 10/3/2015.
 */
public class JSONParser {

    static InputStream iStream = null;
    static JSONObject jarray = null;
    static String json = "";

    public JSONParser() {

    }

    public JSONObject getJSONFromUrl(String url) throws IOException {
        StringBuilder builder = new StringBuilder();
        URL url_link = new URL(url);
        HttpURLConnection con = (HttpURLConnection) url_link.openConnection();
        con.setRequestMethod("GET");
        try {
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line = reader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
            } else {
                Log.e("demo", "failed to download file");
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        try{
            jarray = new JSONObject(builder.toString());
        }
        catch(JSONException e) {
            Log.e("Json parser" , "error in parsing"+e.toString());
        }

    return jarray;
    }

}
