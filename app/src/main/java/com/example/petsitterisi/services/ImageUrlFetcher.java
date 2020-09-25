package com.example.petsitterisi.services;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

public class ImageUrlFetcher extends AsyncTask<String, Nullable, Bitmap> {

    private Context  context;
    LinearLayout ll;
    SharedPreferences sharedpreferences;
    Button  reservervation_liste_pet_sitter;
    Dialog dialog_reservation;
    Dialog dialog_paiement;
    TextView prix_ht_facture;
    TextView taxe_tps;
    TextView taxe_tvq;
    TextView prix_ttc_facture;
    Button appliquer_code_promo;
    ImageView utilisateur_photo_profile;
    Button button_profil;
    String utilisateur_sexe;
    private int Drawable;
    DatePickerDialog picker;


    public ImageUrlFetcher(Context  context, ImageView utilisateur_photo_profile, String utilisateur_sexe) {
        this.context = context;
        this.utilisateur_photo_profile = utilisateur_photo_profile;
        this.utilisateur_sexe = utilisateur_sexe;
        sharedpreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {

        Bitmap bmp = null;
        String url_photo = "";

        try {
            url_photo = urls[0];
            InputStream in = new java.net.URL(url_photo).openStream();
            bmp = BitmapFactory.decodeStream(in);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(bmp == null){

            if(utilisateur_sexe.equals("masculin")){
                url_photo = "https://pets-friendly.herokuapp.com/images/images_profiles/image_profile_default_homme.jpg";

            }else if(utilisateur_sexe.equals("feminin")){
                url_photo = "https://pets-friendly.herokuapp.com/images/images_profiles/image_profile_default_femme.jpg";
            }

            InputStream in_default = null;
            try {
                in_default = new java.net.URL(url_photo).openStream();
                bmp = BitmapFactory.decodeStream(in_default);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return bmp;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})

    protected void onPostExecute(Bitmap s) {

        if(s != null) {
            utilisateur_photo_profile.setImageBitmap(s);
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
