package com.example.petsitterisi.managers;
import android.content.Context;

import com.example.petsitterisi.entitees.Service;
import com.example.petsitterisi.services.ApiAjouterContratFetcher;
import com.example.petsitterisi.services.ApiRechercheFetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
public class AjouterContratManager {


    public static void getContrat(Context context, String date_debut, String date_fin, int id_service1, int id_service2, int id_service3, String date_debut_promotion, String date_fin_promotion, double prix) throws JSONException {
        try {

            //creation du Json
            JSONObject creationContrat = new JSONObject(); // Json principal
            JSONObject contrat = new JSONObject();
            JSONArray service = new JSONArray();
            JSONObject promotion  = new JSONObject();
            JSONObject facture = new JSONObject();

            contrat.put("date_debut",date_debut);
            contrat.put("date_fin",date_fin);

            service.put(Integer.parseInt("id_service1"),id_service1);
            service.put(Integer.parseInt("id_service2"),id_service2);
            service.put(Integer.parseInt("id_service3"),id_service3);

            promotion.put("date_debut",date_debut_promotion);
            promotion.put("date_fin",date_fin_promotion);

            facture.put("prix",prix);

            creationContrat.put("contrat", contrat);
            creationContrat.put("service", service);
            creationContrat.put("promotion", promotion);
            creationContrat.put("facture", facture);

            //connexion a l'Api
            ApiAjouterContratFetcher apiFetcher = new ApiAjouterContratFetcher(context);
            apiFetcher.execute("https://pets-friendly.herokuapp.com/contrats/creation", creationContrat.toString());

        }catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
