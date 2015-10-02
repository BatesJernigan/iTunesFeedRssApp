//package com.example.arjun.homework4;
//
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.ProtocolException;
//import java.net.URL;
//import java.util.ArrayList;
//
///**
// * Created by arjun on 9/28/2015.
// */
//public class GetPersonsAsync extends AsyncTask<String, Void, ArrayList<String>>{
//
//    ProgressDialog progressDialog;
//
//    @Override
//    protected ArrayList<String> doInBackground(String... params) {
//        try {
//            URL url = new URL(params[0]);
//            HttpURLConnection con = (HttpURLConnection)url.openConnection();
//            con.setRequestMethod("GET");
//            con.connect();
//            int statusCode = con.getResponseCode();
//            if(statusCode == HttpURLConnection.HTTP_OK) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                StringBuilder sb = new StringBuilder();
//                String line = reader.readLine();
//                while(line != null) {
//                    sb.append(line);
//                    line = reader.readLine();
//                }
//                Log.d("demo",sb.toString());
//
//                //return PersonsUtil.PersonsJSONParser.parsePersons(sb.toString());
//            }
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(ArrayList<String> result) {
//        super.onPostExecute(result);
//        if(result != null){
//            Log.d("demo",result.toString());
//        }
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//
//        progressDialog = new ProgressDialog(GetPersonsAsync.class);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading Image ...");
//        progressDialog.show();
//    }
//}
