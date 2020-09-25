package com.example.petsitterisi.managers;
import android.content.Context;

import com.example.petsitterisi.services.ApiAjouterFavorisFetcher;
import com.example.petsitterisi.services.ApiRecupererFavorisFetcher;

import org.json.JSONException;
import org.json.JSONObject;
public class RecupererFavorisManager {


    public static void getFavoris(Context context, int id_proprietaire) {


        try {

            //connexion a l'Api
            ApiRecupererFavorisFetcher apiFetcher = new ApiRecupererFavorisFetcher(context);

            String url = "https://pets-friendly.herokuapp.com/favoris/recuperation/utilisateur"+id_proprietaire;

            apiFetcher.execute(url);

        }catch (Exception e) {
            e.printStackTrace();
        }


    }



}
