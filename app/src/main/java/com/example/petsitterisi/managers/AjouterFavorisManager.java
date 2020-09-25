package com.example.petsitterisi.managers;
import android.content.Context;

import com.example.petsitterisi.services.ApiAjouterFactureFetcher;
import com.example.petsitterisi.services.ApiAjouterFavorisFetcher;

import org.json.JSONException;
import org.json.JSONObject;
public class AjouterFavorisManager {


    public static void getFavoris(Context context, int id_petsitter, int id_proprietaire) throws JSONException {

        //creation du Json
        JSONObject ajouterFavoris = new JSONObject(); // Json principal qui contient 1 autre objet Json "unFavoris"
        JSONObject unFavoris = new JSONObject(); // objet1 Json contenant le favoris
        ajouterFavoris.put("favoris", unFavoris);


        try {

            unFavoris.put("id_petsitter",id_petsitter);
            unFavoris.put("id_proprietaire",id_proprietaire);


            //connexion a l'Api
            ApiAjouterFavorisFetcher apiFetcher = new ApiAjouterFavorisFetcher(context);
            apiFetcher.execute("https://pets-friendly.herokuapp.com/favoris/ajout", ajouterFavoris.toString());

        }catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
