package com.example.petsitterisi.services;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.petsitterisi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

public class ApiListFavorisFetcher extends AsyncTask<String, Nullable, String> {

    private Context  context;
    LinearLayout ll;
    SharedPreferences sharedpreferences;
    Button  reservervation_liste_pet_sitter;
    Dialog dialog_reservation;
    TextView prix_ht_facture;
    TextView taxe_tps;
    TextView taxe_tvq;
    TextView prix_ttc_facture;
    Button appliquer_code_promo;
    Button reservation_final;


    public ApiListFavorisFetcher(Context  context, LinearLayout llParam) {
        this.context = context;
        this.ll = llParam;
        sharedpreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        dialog_reservation = new Dialog(context);
    }

    @Override
    protected String doInBackground(String... urls) {

        return "";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String tContents = "";
        String concat = "";
        try {
            InputStream stream = context.getAssets().open("recuperer_favoris.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);

            JSONArray favorisJsonArrayObject = new JSONArray(tContents);

            for(int i = 0; i < favorisJsonArrayObject.length(); i++){

                JSONObject favorisJsonObject = favorisJsonArrayObject.getJSONObject(i);
                String petSitterId = favorisJsonObject.getString("id_petsitter");

                ApiRecupererUtilisateurFetcher apiRecupererUtilisateurFetcher = new ApiRecupererUtilisateurFetcher(context, ll);
                apiRecupererUtilisateurFetcher.execute("https://pets-friendly.herokuapp.com/utilisateurs/recuperation/"+petSitterId);

            }




        } catch (Exception e) {
            // Handle exceptions here

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
