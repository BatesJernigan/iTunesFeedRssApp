package com.example.itunesfeedrssapp;

/**
 * Created by batesjernigan on 10/2/15.
 */

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MediaUtil {

    static public class MediaJSONParser {

        static public ArrayList<Media> parseMedia(String in) throws JSONException {
            Log.d("demo", "in parse media thingy");
            ArrayList<Media> medialist = new ArrayList<Media>();
            Gson gson = new GsonBuilder().create();


            JSONObject root = new JSONObject(in); // cause the data starts with an array

            Object obj = gson.fromJson(in, Object.class);

            Log.d("demo", "obj from json: " + obj.toString());

            Log.d("demo", "feed field: " + root.getJSONObject("feed").getJSONObject("author").toString());

            Media media = gson.fromJson(in, Media.class);

//            Type collectionType2 = new TypeToken<List<String>>() {}.getType();
//            List<String> listObj = gson.fromJson(root, Type.class);
//            gson.fromJson(root, ModelObject.class);
            Log.d("demo", "converted object representation: " + media.toString());

//            String name = root.getString("entry");
//            Log.d("demo","the entry is"+" "+name);

            JSONArray entryJSONArray = root.getJSONArray("image");
            if(entryJSONArray != null) {
                Log.d("Demo","array is not empty");
            }
            //  m.setTitle(root.getString("title"));
//            JSONArray mediaJSONArray = root.getJSONArray("im:image");
//            int length = mediaJSONArray.length();
//            System.out.println(length);
            // Log.d("demo", m.getTitle());



//            for(int i=0; i< mediaJSONArray.length(); i++) {
//                JSONObject mediaJSONObject = mediaJSONArray.getJSONObject(i);
//                Media media = new Media();
//                person.setName(mediaJSONObject.getString("name"));
//                person.setId(mediaJSONObject.getInt("id"));
//                person.setAge(mediaJSONObject.getInt("age"));
//                person.setDepartment(mediaJSONObject.getString("department"));
//
//                medialist.add(person);
//            }

            return medialist;
        }
    }
}
