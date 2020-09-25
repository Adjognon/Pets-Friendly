package com.example.petsitterisi;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.petsitterisi.managers.UtilisateurManager;
import com.example.petsitterisi.services.ApiListFavorisFetcher;
import com.example.petsitterisi.services.ApiListReservationFetcher;
public class ReservationFragment extends Fragment {
    Context ctx;
    LinearLayout reservation_container;
    Dialog dialog_commentaire;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View lesReservationProprietaire = inflater.inflate(R.layout.fragment_demande, container, false);

        ctx = lesReservationProprietaire.getContext();

        reservation_container = lesReservationProprietaire.findViewById(R.id.ll_liste_reservation);


        int utilisateurId = UtilisateurManager.getIdUtilisateur(ctx);
        try {
            ApiListReservationFetcher apiListReservationFetcher = new ApiListReservationFetcher(ctx, reservation_container);
            apiListReservationFetcher.execute("https://pets-friendly.herokuapp.com/contrats/recuperation/utilisateur/"+utilisateurId);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return lesReservationProprietaire;

    }
}
