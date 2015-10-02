package com.example.arjun.homework4;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by arjun on 9/28/2015.
 */
public class MediaUtil {

    static public class MediaJSONParser {

        static public ArrayList<Media> parseMedia(String in) throws JSONException {
            ArrayList<Media> medialist = new ArrayList<Media>();
            Media m = new Media();

            JSONObject root = new JSONObject(in); // cause the data starts with an array

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
