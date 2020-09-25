package com.example.petsitterisi.services;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.annotation.SuppressLint;

import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.petsitterisi.BottomNavigationBar;
import com.example.petsitterisi.R;
import com.example.petsitterisi.entitees.Utilisateur;
import com.example.petsitterisi.managers.UtilisateurManager;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Iterator;

public class ApiListPetSitterFetcher extends AsyncTask<String, Nullable, String> {

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
    Button reservation_final;
    Button button_profil;
    private int Drawable;
    DatePickerDialog picker;


    public ApiListPetSitterFetcher(Context  context, LinearLayout llParam) {
        this.context = context;
        this.ll = llParam;
        sharedpreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        dialog_reservation = new Dialog(context);
        dialog_paiement = new Dialog(context);
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


            JSONArray adressJsonArray = new JSONArray();
            adressJsonArray.put(4);

            JSONObject adresseJsonObject = new JSONObject();

            String numero_rue = UtilisateurManager.getAdresseInfos(context, "numero_rue");
            adresseJsonObject.put("numero_rue", numero_rue);

            String nom_rue = UtilisateurManager.getAdresseInfos(context, "nom_rue");
            adresseJsonObject.put("nom_rue", nom_rue);

            String code_postal = UtilisateurManager.getAdresseInfos(context, "code_postal");
            adresseJsonObject.put("code_postal", code_postal);

            String ville = UtilisateurManager.getAdresseInfos(context, "ville");
            adresseJsonObject.put("ville", ville);

            String province = UtilisateurManager.getAdresseInfos(context, "province");
            adresseJsonObject.put("province", province);


            String pays = UtilisateurManager.getAdresseInfos(context, "pays");
            adresseJsonObject.put("pays", pays);

            JSONObject grandJsonObject = new JSONObject();
            grandJsonObject.put("services", adressJsonArray);
            grandJsonObject.put("adresse", adresseJsonObject);


            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(grandJsonObject.toString());
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

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
            try {

                JSONArray jsonArray = new JSONArray(s);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsObject = (JSONObject) jsonArray.get(i);

                        View cardPetSitterParam = View.inflate(context , R.layout.card_pet_sitter,null);
                        ImageView utilisateur_photo_profile = cardPetSitterParam.findViewById(R.id.utilisateur_photo_profile);
                        TextView petSitterName = cardPetSitterParam.findViewById(R.id.name);
                        final String petSitterId = jsObject.getString("id");
                        String rating = jsObject.getString("rating");
                        String url_photo = jsObject.getString("url_photo");
                        String utilisateur_sexe = jsObject.getString("sexe");

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


                        //LoadImageFromWebOperations(String url)

                        petSitterName.setText(jsObject.getString("nom"));
                        button_profil = cardPetSitterParam.findViewById(R.id.button_profil);
                        reservervation_liste_pet_sitter = cardPetSitterParam.findViewById(R.id.reservervation_liste_pet_sitter);

                         int ratingInteger = 0;

                        if(rating != null && rating != "null"){
                            ratingInteger = Integer.parseInt(rating);
                        }



                        Drawable = 0;
                        ImageView rateIcon = cardPetSitterParam.findViewById(R.id.rate_icon);
                        String rateDescription = "";


                        if(ratingInteger > 0 && ratingInteger < 50){
                            Drawable  = R.drawable.icones_pas_satisfaisant;
                            rateIcon.setColorFilter(Color.rgb(255, 0, 0)); // rouge
                            rateDescription = "Debutant";

                        }else if(ratingInteger >= 50 && ratingInteger < 100){
                            Drawable  = R.drawable.icones_moyen;
                            rateIcon.setColorFilter(Color.rgb(255, 204, 0)); // jaune
                            rateDescription = "Intermediare";

                        }else if(ratingInteger >= 100 && ratingInteger < 200){
                            Drawable  = R.drawable.icones_bien;
                            rateIcon.setColorFilter(Color.rgb(255, 51, 0)); // orange
                            rateDescription = "Avance";

                        }else if(ratingInteger >= 200 && ratingInteger < 400){
                            Drawable  = R.drawable.icones_tres_bien;
                            rateIcon.setColorFilter(Color.rgb(255, 153, 0)); // or
                            rateDescription = "Expert";

                        }else if(ratingInteger >= 400){
                            Drawable  = R.drawable.icones_excellent;
                            rateIcon.setColorFilter(Color.rgb(46, 184, 46)); // vert
                            rateDescription = "Genie";

                        }

                        TextView description_niveau_pet_sitter = cardPetSitterParam.findViewById(R.id.description_niveau_pet_sitter);
                        description_niveau_pet_sitter.setText(rateDescription);

                        rateIcon.setImageResource(Drawable);

                        button_profil.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(context, BottomNavigationBar.class);
                                intent.putExtra("Profil", "true");
                                UtilisateurManager.addDataToSharedPreference(context, "petsitter_profile_selectionner", petSitterId);
                                context.startActivity(intent);

                            }
                        });

                        final JSONArray petSitterServiceStringArray = jsObject.getJSONArray("services");

                        for(int k = 0; k <  petSitterServiceStringArray.length(); k++){
                            String idService = petSitterServiceStringArray.getString(k);

                            String descriptionService = sharedpreferences.getString("description_service_"+idService, null);
                            String prixService = sharedpreferences.getString("prix_service_"+idService, null);

                            View serviceView = View.inflate(context , R.layout.service,null);
                            ImageView serviceImage = serviceView.findViewById(R.id.service_image);
                            LinearLayout pet_sitter_services_container = cardPetSitterParam.findViewById(R.id.pet_sitter_services_container);

                            try {
                                if (descriptionService.equals("Promenade")) {
                                    serviceImage.setImageResource(R.drawable.image_16);

                                    serviceImage.getLayoutParams().height = 80;
                                    serviceImage.getLayoutParams().width = 80;
                                    serviceImage.setScaleType(ImageView.ScaleType.FIT_XY);

                                } else if (descriptionService.equals("Garder chez vous")) {
                                    serviceImage.setImageResource(R.drawable.image_icon_2);

                                    serviceImage.getLayoutParams().height = 80;
                                    serviceImage.getLayoutParams().width = 80;
                                    serviceImage.setScaleType(ImageView.ScaleType.FIT_XY);


                                } else if (descriptionService.equals("Garder chez le Pet Sitter")) {
                                    serviceImage.setImageResource(R.drawable.image_icon_1);

                                    serviceImage.getLayoutParams().height = 80;
                                    serviceImage.getLayoutParams().width = 80;
                                    serviceImage.setScaleType(ImageView.ScaleType.FIT_XY);


                                }


                                TextView servicePrix = serviceView.findViewById(R.id.service_prix);
                                servicePrix.setText(prixService + "$");

                                TextView serviceName = serviceView.findViewById(R.id.service_name);
                                serviceName.setText(descriptionService);


                                pet_sitter_services_container.addView(serviceView);
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                        }

                int id_role = UtilisateurManager.getIdUtilisateurRole(context);

                        if(id_role == 3){
                            reservervation_liste_pet_sitter.setEnabled(false);
                        }

                reservervation_liste_pet_sitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            afficherAlertDialogReservation(petSitterId, petSitterServiceStringArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                        ll.addView(cardPetSitterParam);


            }



        } catch (JSONException e) {
            // Handle exceptions here
              e.printStackTrace();
        }

    }


    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = android.graphics.drawable.Drawable.createFromStream(is, "michel_iamge");
            return d;
        } catch (Exception e) {
            return null;
        }
    }


    private void afficherAlertDialogReservation(final String petSitterId, final JSONArray petSitterServiceStringArray) throws JSONException {

        //material_dialog_reservation.setView(R.layout.alert_dialog_reservation);

        JSONArray jsonServiceSelectionnerArray = new JSONArray();

        sharedpreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String serviceSelectId = sharedpreferences.getString("id_service_select", null);
        if(serviceSelectId != null){

            String descriptionService = sharedpreferences.getString("description_service_"+serviceSelectId, null);
            String prixService = sharedpreferences.getString("prix_service_"+serviceSelectId, null);

            JSONObject serviceJson = new JSONObject();
            serviceJson.put("descriptionService", descriptionService );
            serviceJson.put("prixService", prixService);

            jsonServiceSelectionnerArray.put(serviceJson);

        }

        String id_service_promenade_select = sharedpreferences.getString("id_service_promenade_select", null);

        if(id_service_promenade_select != null && id_service_promenade_select != "0"){

            String descriptionService = sharedpreferences.getString("description_service_"+id_service_promenade_select, null);
            String prixService = sharedpreferences.getString("prix_service_"+id_service_promenade_select, null);

            JSONObject serviceJson = new JSONObject();
            serviceJson.put("descriptionService", descriptionService );
            serviceJson.put("prixService", prixService);

            jsonServiceSelectionnerArray.put(serviceJson);

        }

        dialog_reservation.setContentView(R.layout.alert_dialog_reservation);
        dialog_paiement.setContentView(R.layout.alert_dialog_paiement);

        double valeurTps = 0.05 ; // 5%
        double valeurTvq = 0.09975; // 9,975%
        float prixHT = 0 ;
        float montantTps = (float) (prixHT * valeurTps);
        float montantTvq = (float) (prixHT *valeurTvq);
        float prixTotal = prixHT + montantTps + montantTvq;

        for(int i = 0; i < jsonServiceSelectionnerArray.length(); i++){
            JSONObject nouveauJsonObject = jsonServiceSelectionnerArray.getJSONObject(i);
            String prixService = nouveauJsonObject.getString("prixService");
            prixHT += Integer.parseInt(prixService);
            montantTps = (float) (prixHT * valeurTps);
            montantTvq = (float) (prixHT *valeurTvq);
            prixTotal = prixHT + montantTps + montantTvq;
        }

            DecimalFormat df = new DecimalFormat("########.00");
            String prixTotalArondissement = df.format(prixTotal);

            prix_ht_facture = (TextView) dialog_reservation.findViewById(R.id.prix_ht_facture);
            prix_ht_facture.setText(String.valueOf(prixHT));

            prix_ttc_facture = (TextView) dialog_reservation.findViewById(R.id.prix_ttc_facture);
            prix_ttc_facture.setText(String.valueOf(prixTotalArondissement));

            appliquer_code_promo = (Button) dialog_reservation.findViewById(R.id.button_appliquer_code_promo);
            reservation_final = (Button) dialog_reservation.findViewById(R.id.reservervation_final);
            final double finalPrixTotal = prixTotal;

        reservation_final.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog_reservation.dismiss();

                    final EditText nomSurCarte = dialog_paiement.findViewById(R.id.nom_sur_carte);
                    final EditText numeroDeCarte = dialog_paiement.findViewById(R.id.numero_de_carte);
                    final EditText dateExpiration = dialog_paiement.findViewById(R.id.date_expiration);
                    final EditText cvvCarte = dialog_paiement.findViewById(R.id.cvv_carte);
                    Button paiementButton = dialog_paiement.findViewById(R.id.paiement_button);

                    dateExpiration.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Calendar cldr = Calendar.getInstance();
                            int day = cldr.get(Calendar.DAY_OF_MONTH);
                            int month = cldr.get(Calendar.MONTH);
                            int year = cldr.get(Calendar.YEAR);
                            // date picker dialog
                            picker = new DatePickerDialog(context, R.style.datePickerTheme,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                            dateExpiration.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                        }
                                    }, year, month, day);
                            picker.show();
                        }
                    });

                    paiementButton.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onClick(View v) {
                            String nomSurCarteValeur = nomSurCarte.getText().toString();
                            String numeroDeCarteValeur = numeroDeCarte.getText().toString();
                            String dateExpirationValeur = dateExpiration.getText().toString();
                            String cvvCarteValeur = cvvCarte.getText().toString();

                            if(!nomSurCarteValeur.trim().equals("")){
                                nomSurCarte.setBackgroundColor(R.color.white);

                                if(!numeroDeCarteValeur.trim().equals("")){
                                    numeroDeCarte.setBackgroundColor(R.color.white);

                                    if(!dateExpirationValeur.trim().equals("")){
                                        dateExpiration.setBackgroundColor(R.color.white);

                                        if(!cvvCarteValeur.trim().equals("")){
                                            cvvCarte.setBackgroundColor(R.color.white);

                                            //envoie de contrat

                                            JSONObject contratJSONDonneeAuComplet = new JSONObject();

                                            //Object utilisateur
                                            int idProprietaire = UtilisateurManager.getIdUtilisateur(context);
                                            JSONObject contratUtilisateurJsonObject = new JSONObject();

                                            //Object contrat
                                            JSONObject contratJsonObject = new JSONObject();

                                            try {
                                                contratUtilisateurJsonObject.put("id_proprietaire",idProprietaire);
                                                contratUtilisateurJsonObject.put("id_petsitter", Integer.parseInt(petSitterId));
                                                contratJSONDonneeAuComplet.put("utilisateur", contratUtilisateurJsonObject);

                                                String debutContrat = UtilisateurManager.getDataFromSharePreference(context, "debut_contrat");
                                                String finContrat = UtilisateurManager.getDataFromSharePreference(context, "fin_contrat");
                                                contratJsonObject.put("date_debut", debutContrat);
                                                contratJsonObject.put("date_fin", finContrat);
                                                contratJSONDonneeAuComplet.put("contrat", contratJsonObject);



                                                JSONArray petSitterIdServiceJsonAttay = new JSONArray();

                                                sharedpreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                                String serviceSelectIdDansRecherche = sharedpreferences.getString("id_service_select", null);
                                                if(serviceSelectIdDansRecherche != null) {
                                                    petSitterIdServiceJsonAttay.put(Integer.parseInt(serviceSelectIdDansRecherche));
                                                }

                                                String id_service_promenade_selectDansLaRecherche = sharedpreferences.getString("id_service_promenade_select", null);

                                                if(id_service_promenade_selectDansLaRecherche != null && id_service_promenade_selectDansLaRecherche != "0"){
                                                    petSitterIdServiceJsonAttay.put(Integer.parseInt(serviceSelectIdDansRecherche));
                                                }



                                                JSONObject promotionJsonObject = new JSONObject();
                                                promotionJsonObject.put("id_promotion", 1);

                                                contratJSONDonneeAuComplet.put("promotion", promotionJsonObject);

                                                contratJSONDonneeAuComplet.put("service", petSitterIdServiceJsonAttay);

                                                ApiContratFetcher apiContratFetcher = new ApiContratFetcher(context, contratJSONDonneeAuComplet);
                                                apiContratFetcher.execute("https://pets-friendly.herokuapp.com/contrats/creation");

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }




                                        }else{
                                            cvvCarte.setBackgroundColor(R.color.red);
                                        }
                                    }else{
                                        dateExpiration.setBackgroundColor(R.color.red);
                                    }

                                    numeroDeCarte.setBackgroundColor(R.color.red);

                                }else{


                                }
                            }else{
                                nomSurCarte.setBackgroundColor(R.color.red);
                            }
                        }
                    });


                    dialog_paiement.show();



                    //ApiAjouterFactureFetcher apiFacture = new ApiAjouterFactureFetcher(context,finalPrixTotal);
                    ApiAjouterContratFetcher apiContrat = new ApiAjouterContratFetcher(context);


                }
            });

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
