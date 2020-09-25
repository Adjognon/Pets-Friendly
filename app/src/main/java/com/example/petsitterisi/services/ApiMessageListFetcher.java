package com.example.petsitterisi.services;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.petsitterisi.BottomNavigationBar;
import com.example.petsitterisi.R;
import com.example.petsitterisi.RechercheFragment;
import com.example.petsitterisi.managers.UtilisateurManager;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

public class ApiMessageListFetcher extends AsyncTask<String, Nullable, String> {

    private Context  context;
    TextView error;
    int id_proprietaire;
    int id_petsitter;
    LinearLayout chat_message_container;
    ChatService chatService;
    Handler handler;


    public ApiMessageListFetcher(Context  context, LinearLayout chat_message_container, ChatService chatService, int id_proprietaire, int id_petsitter) {
        this.context = context;
        this.id_proprietaire = id_proprietaire;
        this.id_petsitter = id_petsitter;
        this.chat_message_container = chat_message_container;
        this.chatService = chatService;
        handler = new Handler();
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
            connexionJson.put("id_proprietaire", id_proprietaire);
            connexionJson.put("id_petsitter", id_petsitter);


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
            JSONArray chatJsonArrayObject = new JSONArray(s);

            chat_message_container.removeAllViews();
            int monIdUtilisateur = UtilisateurManager.getIdUtilisateur(context);

            for(int i = 0; i < chatJsonArrayObject.length(); i++){
                JSONObject chatJsonObject = chatJsonArrayObject.getJSONObject(i);
                String message = chatJsonObject.getString("message");
                String idFrom = chatJsonObject.getString("idFrom");
                String idTo = chatJsonObject.getString("idTo");

                View cardMessageEnvoyer = View.inflate(context , R.layout.activity_item_message_envoyer,null);
                View cardMessageRecus = View.inflate(context , R.layout.activity_item_message_recus,null);

                if(monIdUtilisateur == Integer.parseInt(idFrom)){
                    TextView messageBulbeTextView = cardMessageEnvoyer.findViewById(R.id.text_message_body_envoyer);
                    messageBulbeTextView.setText(message);
                    chat_message_container.addView(cardMessageEnvoyer);

                }else{
                    TextView messageBulbeTextView = cardMessageRecus.findViewById(R.id.text_message_body_recu);
                    messageBulbeTextView.setText(message);
                    chat_message_container.addView(cardMessageRecus);
                }

            }

<<<<<<< HEAD
            RechercheFragment.mSocket.on("nouveau_message", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {

                   JSONObject data = (JSONObject) args[0];

                    String message = null;
                    try {


                        message = data.getString("message");

                        final String finalMessage = message;
                        final String finalMessage1 = message;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                View cardMessageRecus = View.inflate(context , R.layout.activity_item_message_recus,null);
                                TextView messageBulbeTextView = cardMessageRecus.findViewById(R.id.text_message_body_recu);
                                messageBulbeTextView.setText(finalMessage1);
                                chat_message_container.addView(cardMessageRecus);

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            });
=======
>>>>>>> 6dfbccefe785569826b622d64335c5ffe0c974c9



            String chatDebutDestination = UtilisateurManager.getDataFromSharePreference(context, "bouton_contacter");

            if(!chatDebutDestination.equals("")) {

                final View cardMessageEnvoyerParam = View.inflate(context, R.layout.activity_item_message_envoyer, null);

                final TextView messageItemEnvoyer = (TextView) cardMessageEnvoyerParam.findViewById(R.id.text_message_body_envoyer);
                TextView heureMessageEnvoyer = cardMessageEnvoyerParam.findViewById(R.id.text_message_time_envoyer);
                String messageEnvoyerDepuisContacterInsideProfilSitter = UtilisateurManager.getMessageContacterInsideDiscussion(context);
                String heureMsgEnvoyerContacter = UtilisateurManager.getHeureMessageEnvoyer(context);



                int id_utilisateur = UtilisateurManager.getIdUtilisateur(context);
                int id_role = UtilisateurManager.getIdUtilisateurRole(context);

                int id_to = 0;

                if(id_role == 2){
                    id_to = id_petsitter;
                }else if(id_role == 3){
                    id_to = id_proprietaire;
                }

                try {

                    JSONObject chatJsonObject = new JSONObject();
                    chatJsonObject.put("idFrom", id_utilisateur);
                    chatJsonObject.put("idTo", id_to);
                    chatJsonObject.put("id_proprietaire", id_proprietaire);
                    chatJsonObject.put("id_petsitter", id_petsitter);

                    chatJsonObject.put("message_entre", id_proprietaire+"_"+id_petsitter);
                    chatJsonObject.put("message", messageEnvoyerDepuisContacterInsideProfilSitter);
                    chatService.sendMyMessage(chatJsonObject);
                    UtilisateurManager.addDataToSharedPreference(context, "bouton_contacter", "");
                } catch (JSONException e) {
                    e.printStackTrace();
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
