package com.example.petsitterisi.managers;
import android.content.Context;

import com.example.petsitterisi.entitees.Animal;
import com.example.petsitterisi.services.ApiAjouterAnimal;

import org.json.JSONException;
import org.json.JSONObject;
public class AjouterUnAnimalManager {

    public static Animal getAnimal(Context context, String race , String typeAnimal , Double poidsAnimal , boolean sexeAnimal, String nom , int age) throws JSONException {

        Animal informations_animal = null;
        String jsonStringDuServeur = "";

        //creation du Json
        JSONObject ajouterAnimal = new JSONObject();
        JSONObject infos_animal = new JSONObject();
        ajouterAnimal.put("animal", infos_animal);

        try {

            infos_animal.put("animal_type",race);
            infos_animal.put("animal_type",typeAnimal);
            infos_animal.put("animal_type",poidsAnimal);
            infos_animal.put("animal_type",sexeAnimal);
            infos_animal.put("animal_type",nom);
            infos_animal.put("animal_type",age);

            //connexion a l'Api
            ApiAjouterAnimal apiFetcher = new ApiAjouterAnimal(jsonStringDuServeur);
            apiFetcher.execute("https://pets-friendly.herokuapp.com/animaux/ajout", ajouterAnimal.toString());

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return informations_animal;
    }


}
