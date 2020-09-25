package com.example.petsitterisi.services;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class ApiFiltresFetcher extends AsyncTask<String, Nullable, String> {

    String jsonStringDuServeur;

    public ApiFiltresFetcher(String jsonStringDuServeur) {
        this.jsonStringDuServeur = jsonStringDuServeur;
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = null;

        try {

            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(false);
            urlConnection.setDoInput(true);
            urlConnection.connect();

            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setRequestMethod("POST");

            OutputStream os = urlConnection.getOutputStream();
            os.write(urls[1].getBytes("UTF-8"));

            int codeRetour = urlConnection.getResponseCode();
            if (codeRetour == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = in.readLine()) != null)
                    result += line;
            }

        } catch (Exception ex) {

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


    }

    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader rd = new BufferedReader(isr);

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
