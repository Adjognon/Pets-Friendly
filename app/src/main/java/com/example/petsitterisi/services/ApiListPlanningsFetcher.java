package com.example.petsitterisi.services;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.petsitterisi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;

public class ApiListPlanningsFetcher extends AsyncTask<String, Nullable, String> {

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


    public ApiListPlanningsFetcher(Context  context, LinearLayout llParam) {
        this.context = context;
        this.ll = llParam;
        sharedpreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        dialog_reservation = new Dialog(context);
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
            JSONArray jsonArray = new JSONArray(s);

            for(int i = 0; i < jsonArray.length(); i++){
                View cardPlanningPetSitter = View.inflate(context , R.layout.card_planning_pet_sitter,null);
                TextView dateDebutContratDemandeSitter = cardPlanningPetSitter.findViewById(R.id.date_debut_contrat_demande_sitter);
                TextView dateFinContratDemandeSitter = cardPlanningPetSitter.findViewById(R.id.date_fin_contrat_demande_sitter);
                TextView dateReservationDemandeSitter = cardPlanningPetSitter.findViewById(R.id.date_reservation_demande_sitter);
                JSONObject planningJsonObject = jsonArray.getJSONObject(i);
                String idPetSitter = planningJsonObject.getString("id_petsitter");
                String idProprietaire = planningJsonObject.getString("id_proprietaire");
                String dateDebut = planningJsonObject.getString("date_debut");
                String dateFin = planningJsonObject.getString("date_fin");

                dateDebut = DateConvertisseur(dateDebut);
                dateFin = DateConvertisseur(dateFin);
                String dateReservation = planningJsonObject.getString("date_creation");
                dateReservation = DateConvertisseur(dateReservation);
                dateReservationDemandeSitter.setText("Reservation faite le "+dateReservation);


                dateDebutContratDemandeSitter.setText(dateDebut);
                dateFinContratDemandeSitter.setText(dateFin);

                ll.addView(cardPlanningPetSitter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String DateConvertisseur(String timestampWithTimeZone){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH);
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
