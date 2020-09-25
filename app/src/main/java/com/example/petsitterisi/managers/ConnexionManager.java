package com.example.petsitterisi.managers;

import android.content.Context;
import android.widget.TextView;

import com.example.petsitterisi.entitees.Utilisateur;
import com.example.petsitterisi.services.ApiUtilisateurFetcher;

import org.json.JSONException;
import org.json.JSONObject;

public class ConnexionManager {


    public static void getUtilisateur(Context context, TextView error, String email, String mot_de_passe) {
        try {

            ApiUtilisateurFetcher apiFetcher = new ApiUtilisateurFetcher(context, error, email, mot_de_passe);

            apiFetcher.execute("https://pets-friendly.herokuapp.com/utilisateurs/connexion");

        }catch (Exception e) {

            e.printStackTrace();

        }


    }

}
