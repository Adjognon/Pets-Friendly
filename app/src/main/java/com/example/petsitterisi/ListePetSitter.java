package com.example.petsitterisi;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petsitterisi.services.ApiListPetSitterFetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Set;
public class ListePetSitter extends Fragment {
    Context ctx;
    LinearLayout ll;
    Button reservervation_liste_pet_sitter;
    Dialog dialog_reservation;
    TextView prix_ht_facture;
    TextView taxe_tps;
    TextView taxe_tvq;
    TextView prix_ttc_facture;
    Button appliquer_code_promo;
    Button reservation_final;
    Button filtre;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View monFragmentRecherche = inflater.inflate(R.layout.activity_liste_pet_sitter, container, false);
        ctx = monFragmentRecherche.getContext();
        dialog_reservation = new Dialog(ctx);
        //filtre = new Button(ctx);
        filtre = monFragmentRecherche.findViewById(R.id.button_filtre);
        //button_profil = monFragmentRecherche.findViewById(R.id.button_profil);
        ll = monFragmentRecherche.findViewById(R.id.card_container);
        ApiListPetSitterFetcher apiListPetsitter = new ApiListPetSitterFetcher(ctx, ll);
        apiListPetsitter.execute("https://pets-friendly.herokuapp.com/recherche");


        filtre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ctx, BottomNavigationBar.class);
                intent.putExtra("Filtres", "true");
                startActivity(intent);

            }
        });




        return monFragmentRecherche;

    }
}

