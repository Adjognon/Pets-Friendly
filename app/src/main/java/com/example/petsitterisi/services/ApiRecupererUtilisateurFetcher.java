package com.example.petsitterisi.services;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.petsitterisi.BottomNavigationBar;
import com.example.petsitterisi.R;
import com.example.petsitterisi.managers.UtilisateurManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
public class ApiRecupererUtilisateurFetcher extends AsyncTask<String, Nullable, String> {

    private Context  context;

    LinearLayout favorisContainer;

    public ApiRecupererUtilisateurFetcher(Context  context, LinearLayout favorisContainer) {
        this.context = context;
        this.favorisContainer = favorisContainer;
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {

            View favorisCardPetSitterParam = View.inflate(context , R.layout.favoris_card_pet_sitter,null);
            JSONObject jsonObjectDuServeur = new JSONObject(s);

            //Recuperateion des nom des enfants JsonObject
            Iterator<String> itr = jsonObjectDuServeur.keys();
            while(itr.hasNext()) {
                String key = itr.next();

                JSONObject utilisateurJson = jsonObjectDuServeur.getJSONObject(key);


                if(key.equals("utilisateur")){

                    TextView petSitterName = favorisCardPetSitterParam.findViewById(R.id.name);
                    petSitterName.setText(utilisateurJson.getString("nom"));

                }else if(key.equals("adresse")){

                    TextView petSitterAdresse = favorisCardPetSitterParam.findViewById(R.id.adresse);
                    petSitterAdresse.setText("Modneue ,doeuee,do0e fjee");
                    petSitterAdresse.setText(utilisateurJson.getString("ville")+", "+utilisateurJson.getString("province")+", "+utilisateurJson.getString("code_postal"));

                }

            }

            favorisContainer.addView(favorisCardPetSitterParam);


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
