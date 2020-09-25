package com.example.petsitterisi.services;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class ApiRecupererFavorisFetcher extends AsyncTask<String, Nullable, String> {

    private Context  context;


    //TextView error;

    public ApiRecupererFavorisFetcher(Context  context) {
        this.context = context;
        //this.error = error;
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = "";

        try {
            URL url = new URL(urls[0]);


            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            urlConnection.connect();

            OutputStream os = urlConnection.getOutputStream();
            os.write(urls[1].getBytes("UTF-8"));

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

        //error.setText(String.valueOf(s));
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
