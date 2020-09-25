package com.example.petsitterisi.services;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.petsitterisi.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class ApiRecupererUtilisateurReserverFetcher extends AsyncTask<String, Nullable, String> {

    private Context  context;

    LinearLayout favorisContainer;
    TextView nomPetSitter;
    TextView petSitterAddress;
    ImageView utilisateur_photo_profile;

    public ApiRecupererUtilisateurReserverFetcher(Context  context, TextView nomPetSitter, TextView petSitterAddress, ImageView utilisateur_photo_profile) {
        this.context = context;
        this.favorisContainer = favorisContainer;
        this.nomPetSitter = nomPetSitter;
        this.petSitterAddress = petSitterAddress;
        this.utilisateur_photo_profile = utilisateur_photo_profile;
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
                    nomPetSitter.setText(utilisateurJson.getString("nom"));

                    String url_photo = utilisateurJson.getString("url_photo");
                    String utilisateur_sexe = utilisateurJson.getString("sexe");

                    if(!url_photo.equals("null")){
                        url_photo = "https://pets-friendly.herokuapp.com/images/images_profiles/"+ url_photo;
                    }else{

                        if(utilisateur_sexe.equals("masculin")){
                            url_photo = "https://pets-friendly.herokuapp.com/images/images_profiles/image_profile_default_homme.jpg";

                        }else if(utilisateur_sexe.equals("feminin")){
                            url_photo = "https://pets-friendly.herokuapp.com/images/images_profiles/image_profile_default_femme.jpg";
                        }

                    }

                    ImageUrlFetcher imageUrlFetcher = new ImageUrlFetcher(context, utilisateur_photo_profile, utilisateur_sexe);
                    imageUrlFetcher.execute(url_photo);


                }else if(key.equals("adresse")){

                    petSitterAddress.setText(utilisateurJson.getString("ville")+", "+utilisateurJson.getString("province")+", "+utilisateurJson.getString("code_postal"));

                }

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
