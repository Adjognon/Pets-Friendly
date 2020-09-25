package com.example.petsitterisi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petsitterisi.managers.UtilisateurManager;
import com.example.petsitterisi.services.ApiAjouterContratFetcher;
import com.example.petsitterisi.services.ApiRecupererUtilisateurProfileFetcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
public class ProfilPetSitter extends Fragment {
    Context ctx;
    Button button_contacterSitter;
    Button button_reservation_sitter;
    Dialog dialog_contacter_sitter;
    Dialog dialog_reserver_sitter;
    LinearLayout ll;
    TextView prenom_pet_sitter;
    TextView ville_pet_sitter;
    ImageView profile_photo_couverture;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View leProfilPetSitter = inflater.inflate(R.layout.activity_profil_pet_sitter, container, false);
        ctx = leProfilPetSitter.getContext();
        button_contacterSitter = leProfilPetSitter.findViewById(R.id.button_contacter_profil_sitter);
        button_reservation_sitter = leProfilPetSitter.findViewById(R.id.reservation_profil_sitter);
        prenom_pet_sitter = leProfilPetSitter.findViewById(R.id.prenom_pet_sitter);
        ville_pet_sitter = leProfilPetSitter.findViewById(R.id.ville_pet_sitter);
        profile_photo_couverture = leProfilPetSitter.findViewById(R.id.profile_photo_couverture);


        dialog_contacter_sitter = new Dialog(ctx);
        dialog_reserver_sitter = new Dialog(ctx);

        int id_role = UtilisateurManager.getIdUtilisateurRole(ctx);

        String petsitter_profile_selectionner = UtilisateurManager.getDataFromSharePreference(ctx, "petsitter_profile_selectionner");
        ApiRecupererUtilisateurProfileFetcher apiRecupererUtilisateurProfileFetcher = new ApiRecupererUtilisateurProfileFetcher(ctx, prenom_pet_sitter, ville_pet_sitter, profile_photo_couverture);
        apiRecupererUtilisateurProfileFetcher.execute("https://pets-friendly.herokuapp.com/utilisateurs/recuperation/"+petsitter_profile_selectionner);

        if(id_role == 3){
            button_contacterSitter.setEnabled(false);
            button_reservation_sitter.setEnabled(false);
        }

        button_contacterSitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    afficherAlertDialogContacter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        button_reservation_sitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    afficherAlertDialogReservationSitter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        getJson(leProfilPetSitter);
        return leProfilPetSitter;


    }
    private void afficherAlertDialogContacter() {
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

                final MediaPlayer son_message_envoyer = MediaPlayer.create(ctx, R.raw.son_message_envoye);
                final View cardMessageEnvoyer = View.inflate(ctx , R.layout.activity_item_message_envoyer,null);
                String heureNowMsgEnvoyer = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

                final TextView messageEnvoyer = cardMessageEnvoyer.findViewById(R.id.text_message_body_envoyer);
                message_envoyer[0] = dialog_contacter_sitter.findViewById(R.id.edit_text_message_envoyer_contacter_sitter);
                textMsgEnvoyer[0] = message_envoyer[0].getText();

                Intent intent = new Intent(ctx, BottomNavigationBar.class);
                intent.putExtra("ChatDiscussion", "true");
//                Intent intentte = new Intent();
//                intentte.putExtra("textMsgEnvoyer", textMsgEnvoyer);


                //messageEnvoyer.setText(textMsgEnvoyer);
                //item.addView(cardMessageEnvoyer);
                //ll.removeView(cardMessageEnvoyer);


                if (!message_envoyer[0].getText().toString().equals("")){

                    // sharedPreference pour l'heure
                    UtilisateurManager.addHeureMessageEnvoyer(ctx, "heure_Msg", heureNowMsgEnvoyer);
                    UtilisateurManager.addMessageContacterInsideDiscussion(ctx, "message_contacter", textMsgEnvoyer[0]);
                    son_message_envoyer.start();
                    ctx.startActivity(intent);
                }

                assert textMsgEnvoyer[0] != null;
                textMsgEnvoyer[0].clear();
            }
        });

        dialog_contacter_sitter.show();
    }


    private void afficherAlertDialogReservationSitter() {
        Button button_reserver_alert_dialog;

        dialog_reserver_sitter.setContentView(R.layout.alert_dialog_reservation);

        button_reserver_alert_dialog = dialog_reserver_sitter.findViewById(R.id.reservervation_final);

        button_reserver_alert_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ctx, BottomNavigationBar.class);
                intent.putExtra("Demande", "true");
                ctx.startActivity(intent);

            }
        });

        dialog_reserver_sitter.show();
    }


    public void getJson (View leProfilPetSitter) {
        String tContents = "";
        String concat = "";
        try {
            InputStream stream = ctx.getAssets().open("resultat-recuperer-utilisateur.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);

            JSONObject obj = new JSONObject(tContents);

            Iterator<String> itr = obj.keys();

            while (itr.hasNext()){

                String key = itr.next();

                JSONObject unUtilisateurJson = obj.getJSONObject(key);
                if(key.equals("utilisateur")){

                    TextView prenomPetSitterParam = leProfilPetSitter.findViewById(R.id.prenom_pet_sitter);
                    prenomPetSitterParam.setText(unUtilisateurJson.getString("prenom"));

                }
                else{
                    TextView villePetSitter = leProfilPetSitter.findViewById(R.id.ville_pet_sitter);
                    villePetSitter.setText(unUtilisateurJson.getString("ville"));

                }

            }

            //Toast.makeText(getApplicationContext(), tContents, Toast.LENGTH_LONG).show();

        } catch (IOException | JSONException e) {
            // Handle exceptions here

        }
    }



}