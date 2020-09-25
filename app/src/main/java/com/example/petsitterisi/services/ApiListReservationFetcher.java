package com.example.petsitterisi.services;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Editable;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
public class ApiListReservationFetcher extends AsyncTask<String, Nullable, String> {

    private Context  context;
    LinearLayout ll;
    SharedPreferences sharedpreferences;
    Button button_commentaire;
    Button button_envoyer_commentaire;
    Button button_contacterSitter;
    TextInputEditText commentaire_envoyer;
    Dialog dialog_reservation;
    Dialog dialog_contacter_sitter;



    public ApiListReservationFetcher(Context  context, LinearLayout llParam) {
        this.context = context;
        this.ll = llParam;
        sharedpreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        dialog_reservation = new Dialog(context);
        dialog_contacter_sitter = new Dialog(context);
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

                JSONObject reservationJsonObject = jsonArray.getJSONObject(i);

                final String idPetSitter = reservationJsonObject.getString("id_petsitter");
                final String idProprietaire = reservationJsonObject.getString("id_proprietaire");
                String dateDebut = reservationJsonObject.getString("date_debut");
                String dateFin = reservationJsonObject.getString("date_fin");
                String dateReservation = reservationJsonObject.getString("date_creation");

                View cardReservationParam = View.inflate(context , R.layout.card_reservation_proprietaire,null);
                TextView nomPetSitter = cardReservationParam.findViewById(R.id.name);
                TextView petSitterAddress = cardReservationParam.findViewById(R.id.petSitterAddress);
                ImageView utilisateur_photo_profile = cardReservationParam.findViewById(R.id.reservation_photo_profile);

                ApiRecupererUtilisateurReserverFetcher apiRecupererUtilisateurReserverFetcher = new ApiRecupererUtilisateurReserverFetcher(context, nomPetSitter, petSitterAddress, utilisateur_photo_profile);
                apiRecupererUtilisateurReserverFetcher.execute("https://pets-friendly.herokuapp.com/utilisateurs/recuperation/"+idPetSitter);


                TextView dateDebutContratReservationProprietaire = cardReservationParam.findViewById(R.id.date_debut_contrat_demande);
                TextView dateFinContratReservationProprietaire = cardReservationParam.findViewById(R.id.date_fin_contrat_demande);
                TextView dateReservationReservationProprietaire = cardReservationParam.findViewById(R.id.date_reservation_demande);



                dateReservationReservationProprietaire.setText(dateReservation);

                dateDebut = DateConvertisseur(dateDebut);
                dateFin = DateConvertisseur(dateFin);

                dateDebutContratReservationProprietaire.setText(dateDebut);
                dateFinContratReservationProprietaire.setText(dateFin);

                dateReservation = DateConvertisseur(dateReservation);
                dateReservationReservationProprietaire.setText("Reservation faite le "+ dateReservation);


                button_commentaire = cardReservationParam.findViewById(R.id.button_commentaire);

                button_commentaire.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            afficherAlertDialogReservation();

                    }
                });

                button_contacterSitter = cardReservationParam.findViewById(R.id.buttom_contacter_pet_sitter);

                button_contacterSitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            afficherAlertDialogContacter(idPetSitter, idProprietaire);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                ll.addView(cardReservationParam);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }




    private void afficherAlertDialogContacter(final String idPetSitter, final String idProprietaire) {
        Button button_envoyer_message;
        final TextInputEditText[] message_envoyer = new TextInputEditText[1];
        //final String text_message_envoyer;
        final Editable[] textMsgEnvoyer = new Editable[1];


        dialog_contacter_sitter.setContentView(R.layout.alert_dialog_contacter_pet_sitter);

        button_envoyer_message = dialog_contacter_sitter.findViewById(R.id.button_envoyer_message);


        //text_message_envoyer = Objects.requireNonNull(message_envoyer.getText()).toString();

//
//        View view;
//        LayoutInflater inflater = (LayoutInflater)   requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(R.layout.activity_message_list, null);
//
//        final LinearLayout item = (LinearLayout) view.findViewById(R.id.container_message_list);




        button_envoyer_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MediaPlayer son_message_envoyer = MediaPlayer.create(context, R.raw.son_message_envoye);
                final View cardMessageEnvoyer = View.inflate(context , R.layout.activity_item_message_envoyer,null);
                String heureNowMsgEnvoyer = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

                final TextView messageEnvoyer = cardMessageEnvoyer.findViewById(R.id.text_message_body_envoyer);
                message_envoyer[0] = dialog_contacter_sitter.findViewById(R.id.edit_text_message_envoyer_contacter_sitter);
                textMsgEnvoyer[0] = message_envoyer[0].getText();

                Intent intent = new Intent(context, BottomNavigationBar.class);
                intent.putExtra("ChatDiscussion", "true");
//                Intent intentte = new Intent();
//                intentte.putExtra("textMsgEnvoyer", textMsgEnvoyer);


                //messageEnvoyer.setText(textMsgEnvoyer);
                //item.addView(cardMessageEnvoyer);
                //ll.removeView(cardMessageEnvoyer);


                if (!message_envoyer[0].getText().toString().equals("")){


                    UtilisateurManager.addDataToSharedPreference(context, "chat_id_petsitter", idPetSitter);
                    UtilisateurManager.addDataToSharedPreference(context, "chat_id_proprietaire", idProprietaire);

                    // sharedPreference pour l'heure
                    UtilisateurManager.addHeureMessageEnvoyer(context, "heure_Msg", heureNowMsgEnvoyer);
                    UtilisateurManager.addMessageContacterInsideDiscussion(context, "message_contacter", textMsgEnvoyer[0]);
                    UtilisateurManager.addDataToSharedPreference(context, "bouton_contacter", "1");
                    message_envoyer[0].setText("");
                    son_message_envoyer.start();
                    context.startActivity(intent);
                }

                assert textMsgEnvoyer[0] != null;
                textMsgEnvoyer[0].clear();
            }
        });

        dialog_contacter_sitter.show();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private String DateConvertisseur(String timestampWithTimeZone){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(timestampWithTimeZone, inputFormatter);
        String formattedDate = outputFormatter.format(date);
        return formattedDate;
    }


    private void afficherAlertDialogReservation() {

        dialog_reservation.setContentView(R.layout.alert_dialog_commentaire);

        commentaire_envoyer = dialog_reservation.findViewById(R.id.commentaire_envoyer);
        Objects.requireNonNull(commentaire_envoyer.getText()).toString();

        try {
            button_envoyer_commentaire = (Button) dialog_reservation.findViewById(R.id.button_envoyer_commentaire);
            button_envoyer_commentaire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BottomNavigationBar.class);
                    intent.putExtra("FeedBack", "true");
                    context.startActivity(intent);
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        dialog_reservation.show();


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
