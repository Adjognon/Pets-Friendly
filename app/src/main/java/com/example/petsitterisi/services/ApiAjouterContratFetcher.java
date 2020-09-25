package com.example.petsitterisi.services;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class ApiAjouterContratFetcher extends AsyncTask<String, Nullable, String> {

    private Context  context;


    //TextView error;

    public ApiAjouterContratFetcher(Context  context) {
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


        String tContents = "";
        String concat = "";
        try
        {

            InputStream stream = context.getAssets().open("reponse_ajouter_recuperer_contrat.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);

            JSONObject obj = new JSONObject(tContents);
            Toast.makeText(context,tContents,Toast.LENGTH_LONG).show();


        }
        catch (IOException | JSONException e)
        {
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
