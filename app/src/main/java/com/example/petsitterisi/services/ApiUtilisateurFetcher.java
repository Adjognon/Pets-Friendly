package com.example.petsitterisi.services;

import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petsitterisi.BottomNavigationBar;
import com.example.petsitterisi.managers.UtilisateurManager;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

public class ApiUtilisateurFetcher extends AsyncTask<String, Nullable, String> {

    private Context  context;
    TextView error;
    String email;
    String mot_de_passe;


    public ApiUtilisateurFetcher(Context  context, TextView error, String email, String mot_de_passe) throws URISyntaxException {
        this.context = context;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.error = error;

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

            JSONObject connexionJson = new JSONObject();

            connexionJson.put("email", email);
            connexionJson.put("mot_de_passe",  mot_de_passe);

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(connexionJson.toString());
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

            JSONObject jsonObjectDuServeur = new JSONObject(s);

            //Recuperateion des nom des enfants JsonObject
            Iterator<String> itr = jsonObjectDuServeur.keys();
            while(itr.hasNext()) {
                String key = itr.next();

                JSONObject utilisateurJson = jsonObjectDuServeur.getJSONObject(key);

                if(key.equals("utilisateur")){

                    String id = utilisateurJson.getString("id");
                    UtilisateurManager.addIdUtilisateur(context, Integer.parseInt(id));

                    String id_role = utilisateurJson.getString("id_role");
                    UtilisateurManager.addIdUtilisateurRole(context, Integer.parseInt(id_role));

                    //Recuperation de tous les service depuis la base de donnee
                    ApiServicesFetcher apiServicesFetcher  = new ApiServicesFetcher(context);
                    apiServicesFetcher.execute("https://pets-friendly.herokuapp.com/services/recuperation/tout");

                    Intent intent = new Intent(context, BottomNavigationBar.class);
                    context.startActivity(intent);

                }else if(key.equals("adresse")){

                    String numero_rue = utilisateurJson.getString("numero_rue");
                    UtilisateurManager.addAdresseInfos(context, "numero_rue", numero_rue);
                    String nom_rue = utilisateurJson.getString("nom_rue");
                    UtilisateurManager.addAdresseInfos(context, "nom_rue", nom_rue);
                    String code_postal = utilisateurJson.getString("code_postal");
                    UtilisateurManager.addAdresseInfos(context, "code_postal", code_postal);
                    String ville = utilisateurJson.getString("ville");
                    UtilisateurManager.addAdresseInfos(context, "ville", ville);
                    String province = utilisateurJson.getString("province");
                    UtilisateurManager.addAdresseInfos(context, "province", province);
                    String pays = utilisateurJson.getString("pays");
                    UtilisateurManager.addAdresseInfos(context, "pays", pays);




                }else if(key.equals("erreur")){
                    error.setText("Erreur d'email et de mot passe");
                }

                //l'object json

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
