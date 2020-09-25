package com.example.petsitterisi.managers;
import android.content.Context;

import com.example.petsitterisi.services.ApiRecupererContratFetcher;
import com.example.petsitterisi.services.ApiRecupererFavorisFetcher;
public class RecupererContratManager {


    public static void getContrat(Context context, int id) {


        try {

            //connexion a l'Api
            ApiRecupererContratFetcher apiFetcher = new ApiRecupererContratFetcher(context);

            String url = "https://pets-friendly.herokuapp.com/contrats/recuperation/utilisateur"+id;

            apiFetcher.execute(url);

        }catch (Exception e) {
            e.printStackTrace();
        }


    }



}
