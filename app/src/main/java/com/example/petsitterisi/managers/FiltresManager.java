package com.example.petsitterisi.managers;
import android.content.Context;

import com.example.petsitterisi.entitees.Service;
import com.example.petsitterisi.services.ApiFiltresFetcher;

import org.json.JSONException;
import org.json.JSONObject;
public class FiltresManager {


    public static Service getService(Context context, boolean visite_veterinaire, boolean toilettage_chat, boolean toilettage_chien, boolean baignade, boolean nourrir, boolean dressage, boolean garder_la_nuit, boolean nombre_animaux) throws JSONException {

        Service services_supplementaire = null;
        String jsonStringDuServeur = "";

        //creation du Json
        JSONObject filtreRecherche = new JSONObject(); // Json principal qui contient 1 autre objet Json "service_supplementaire"
        JSONObject service_supplementaire = new JSONObject(); // autre objet Json contenant les service_supplementaire
        filtreRecherche.put("services", service_supplementaire);

        try {
            service_supplementaire.put("service_supplementaire",visite_veterinaire);
            service_supplementaire.put("service_supplementaire",toilettage_chat);
            service_supplementaire.put("service_supplementaire",toilettage_chien);
            service_supplementaire.put("service_supplementaire",baignade);
            service_supplementaire.put("service_supplementaire",dressage);
            service_supplementaire.put("service_supplementaire",nourrir);
            service_supplementaire.put("service_supplementaire",garder_la_nuit);
            service_supplementaire.put("service_supplementaire",nombre_animaux);

            //connexion a l'Api
            ApiFiltresFetcher apiFetcher = new ApiFiltresFetcher(jsonStringDuServeur);
            apiFetcher.execute("https://pets-friendly.herokuapp.com/recherche", filtreRecherche.toString());

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return services_supplementaire;
    }


}
