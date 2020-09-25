package com.example.petsitterisi.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApiServicesFetcher extends AsyncTask<String, Nullable, String> {

    private Context  context;
    SharedPreferences sharedpreferences;


    public ApiServicesFetcher(Context  context) {
        this.context = context;
        sharedpreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = "";

        try {
            URL url = new URL(urls[0]);


            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(false);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();


            int codeRetour = urlConnection.getResponseCode();

            if (codeRetour == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

              String line = "";
                while ((line = in.readLine()) != null)
                    result += line;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(s);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                String idService = jsonObject.getString("id");
                editor.putString("id_"+idService, idService);

                String descriptionService = jsonObject.getString("description_service");
                editor.putString("description_service_"+idService, descriptionService);

                String prixService = jsonObject.getString("prix_service");
                editor.putString("prix_service_"+idService, prixService);

                editor.commit();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader rd = new BufferedReader(isr);

        String in = "";


        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.toString();
    }


}
