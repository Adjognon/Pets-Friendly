package com.example.petsitterisi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.petsitterisi.managers.UtilisateurManager;
import com.example.petsitterisi.services.ApiListPlanningsFetcher;

public class PlaningsFragment extends Fragment {

    Context ctx;
    LinearLayout ll;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View monFragmentRecherche = inflater.inflate(R.layout.fragment_plannings, container, false);

        ctx = monFragmentRecherche.getContext();
        ll = monFragmentRecherche.findViewById(R.id.planingsContainer);

        ApiListPlanningsFetcher apiListPlaningsFetcher = new ApiListPlanningsFetcher(ctx, ll);
        int utilisateurId = UtilisateurManager.getIdUtilisateur(ctx);
        apiListPlaningsFetcher.execute("https://pets-friendly.herokuapp.com/plannings/recuperation/utilisateur/"+utilisateurId);


        return monFragmentRecherche;
    }

}