package com.example.petsitterisi.services;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.petsitterisi.BottomNavigationBar;
import com.example.petsitterisi.R;
import com.example.petsitterisi.entitees.Utilisateur;
import com.example.petsitterisi.managers.UtilisateurManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class ApiRecupererUtilisateurChatBoxFetcher extends AsyncTask<String, Nullable, String> {

    private Context  context;

    LinearLayout favorisContainer;
    LinearLayout ll;
    View cardChatParam;
    TextView petSitterAddress;
    LinearLayout card_chat_select;
    int discussion_avec_id = 0;
    int idTo = 0;

    public ApiRecupererUtilisateurChatBoxFetcher(Context  context, View cardChatParam, LinearLayout ll, int discussion_avec_id) {
        this.context = context;
        this.cardChatParam = cardChatParam;
        this.ll = ll;
        this.discussion_avec_id = discussion_avec_id;
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

                final JSONObject utilisateurJson = jsonObjectDuServeur.getJSONObject(key);

                if(key.equals("utilisateur")) {
                    card_chat_select = cardChatParam.findViewById(R.id.car_chat_selectionner);
                    TextView prenom_chat = cardChatParam.findViewById(R.id.prenom_chat);
                    ImageView utilisateur_photo_profile = cardChatParam.findViewById(R.id.image_message_profile_liste_discussionr);
                    final String nom = utilisateurJson.getString("nom");
                    prenom_chat.setText(utilisateurJson.getString("nom"));

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

                    card_chat_select.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int id_utilisateur = UtilisateurManager.getIdUtilisateur(context);

                            int id_role = UtilisateurManager.getIdUtilisateurRole(context);

                            if(id_role == 2 ){

                                UtilisateurManager.addDataToSharedPreference(context, "chat_id_petsitter", String.valueOf(discussion_avec_id));
                                UtilisateurManager.addDataToSharedPreference(context, "chat_id_proprietaire", String.valueOf(id_utilisateur));


                            }
                            else if(id_role == 3) {
                                UtilisateurManager.addDataToSharedPreference(context, "chat_id_petsitter", String.valueOf(id_utilisateur));
                                UtilisateurManager.addDataToSharedPreference(context, "chat_id_proprietaire", String.valueOf(discussion_avec_id));
                            }


                            Intent intent = new Intent(context, BottomNavigationBar.class);
                            intent.putExtra("ChatDiscussion", "true");
                            UtilisateurManager.addNomMessage(context, "nom_chat", nom);
                            context.startActivity(intent);


                        }
                    });

                    ll.addView(cardChatParam);


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
