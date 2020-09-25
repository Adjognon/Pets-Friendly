package com.example.petsitterisi.managers;
import android.content.Context;

import com.example.petsitterisi.entitees.Service;
import com.example.petsitterisi.services.ApiRechercheFetcher;

import org.json.JSONException;
import org.json.JSONObject;
public class RechercheManager {



    public static void getService(Context context, boolean service_gardez_chez_sitter, boolean service_gardez_chez_proprietaire, boolean service_promenade, boolean type_chien, boolean type_chat, String date_debut_contrat, String date_fin_contrat, String adresse_proprietaire) throws JSONException {

        Service serviceDeBase = null;
        String jsonStringDuServeur = "";

        //creation du Json
        JSONObject recherche = new JSONObject(); // Json principal qui contient 1 autre objet Json "service_de_base"
        JSONObject service_de_base = new JSONObject(); // objet1 Json contenant les services de base
        recherche.put("services", service_de_base);

        try {
            service_de_base.put("service_garde",service_gardez_chez_sitter);
            service_de_base.put("service_garde",service_gardez_chez_proprietaire);
            service_de_base.put("service_promenade",service_promenade);
            service_de_base.put("service_date_debut_contrat",date_debut_contrat);
            service_de_base.put("service_date_fin_contrat",date_fin_contrat);
            service_de_base.put("service_adresse_proprietaire",adresse_proprietaire);
            service_de_base.put("animal_type",type_chien);
            service_de_base.put("animal_type",type_chat);

            //connexion a l'Api
            ApiRechercheFetcher apiFetcher = new ApiRechercheFetcher(jsonStringDuServeur);
            apiFetcher.execute("https://pets-friendly.herokuapp.com/recherche", recherche.toString());

        }catch (JSONException e) {
            e.printStackTrace();
        }



    }





}
