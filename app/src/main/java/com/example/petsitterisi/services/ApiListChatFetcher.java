package com.example.petsitterisi.services;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.petsitterisi.BottomNavigationBar;
import com.example.petsitterisi.R;
import com.example.petsitterisi.managers.UtilisateurManager;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
public class ApiListChatFetcher extends AsyncTask<String, Nullable, String> {

    private Context  context;
    LinearLayout ll;
    SharedPreferences sharedpreferences;
    LinearLayout card_chat_select;

    Button icone_suivant;
    int utilisateurId;
    int id_role;


    public ApiListChatFetcher(Context  context, LinearLayout llParam, int utilisateurId, int id_role) {
        this.context = context;
        this.ll = llParam;
        sharedpreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        this.utilisateurId = utilisateurId;
        this.id_role = id_role;
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
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONArray chatJSONArray = new JSONArray(s);

            ArrayList<String> ids = new ArrayList<String>();

            for(int i = 0; i < chatJSONArray.length(); i++){
                JSONObject chatOject = chatJSONArray.getJSONObject(i);
                String discussion_avec_id = chatOject.getString("_id");

                    View cardChatParam = View.inflate(context , R.layout.card_chat,null);
                    ApiRecupererUtilisateurChatBoxFetcher apiRecupererUtilisateurChatBoxFetcher = new ApiRecupererUtilisateurChatBoxFetcher(context, cardChatParam, ll, Integer.parseInt(discussion_avec_id));
                    apiRecupererUtilisateurChatBoxFetcher.execute("https://pets-friendly.herokuapp.com/utilisateurs/recuperation/"+discussion_avec_id);



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String DateConvertisseur(String timestampWithTimeZone){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(timestampWithTimeZone, inputFormatter);
        String formattedDate = outputFormatter.format(date);
        return formattedDate;
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
