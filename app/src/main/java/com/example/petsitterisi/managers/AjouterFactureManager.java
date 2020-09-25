package com.example.petsitterisi.managers;
import android.content.Context;

import com.example.petsitterisi.services.ApiAjouterContratFetcher;
import com.example.petsitterisi.services.ApiAjouterFactureFetcher;

import org.json.JSONException;
import org.json.JSONObject;
public class AjouterFactureManager {


    public static void getFacture(Context context, int id_promotion, float prix) throws JSONException {


        try {

            //creation du Json
        JSONObject ajoutFacture = new JSONObject(); // Json principal qui contient 1 autre objet Json "uneFacture"
        JSONObject uneFacture = new JSONObject(); // objet1 Json contenant la facture

        uneFacture.put("id_promotion",id_promotion);
        uneFacture.put("prix",prix);

        ajoutFacture.put("facture", uneFacture);

            //connexion a l'Api
            ApiAjouterFactureFetcher apiFetcher = new ApiAjouterFactureFetcher(context,prix);
            apiFetcher.execute("https://pets-friendly.herokuapp.com/factures/ajout", ajoutFacture.toString());

        }catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
