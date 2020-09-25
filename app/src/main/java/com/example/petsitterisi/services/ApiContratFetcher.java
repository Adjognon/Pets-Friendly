package com.example.petsitterisi.services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.petsitterisi.BottomNavigationBar;
import com.example.petsitterisi.managers.UtilisateurManager;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class ApiContratFetcher extends AsyncTask<String, Nullable, String> {

    private Context  context;
    TextView error;
    String email;
    String mot_de_passe;
    JSONObject contratJsonObject;


    public ApiContratFetcher(Context  context, JSONObject contratJsonObject) {
        this.context = context;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.error = error;
        this.contratJsonObject = contratJsonObject;
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
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(contratJsonObject.toString());
            wr.flush();
            wr.close();

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
        try {

             JSONObject jsonObject = new JSONObject(s);

             if(jsonObject.length() == 0){

                 Toast.makeText(context, "Reservation faite avec Success", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(context, BottomNavigationBar.class);
                 intent.putExtra("Demande", "true");
                 context.startActivity(intent);

             }else{
                 Toast.makeText(context, "Erreur de connexion. Reesayez encore", Toast.LENGTH_SHORT).show();
             }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erreur de donnees", Toast.LENGTH_SHORT).show();
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
