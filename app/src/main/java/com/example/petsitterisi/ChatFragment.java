package com.example.petsitterisi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.petsitterisi.managers.UtilisateurManager;
import com.example.petsitterisi.services.ApiListChatFetcher;
import com.example.petsitterisi.services.ApiListReservationFetcher;
public class ChatFragment extends Fragment {

    Context ctx;
    LinearLayout chat_liste_container;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View listeChatView = inflater.inflate(R.layout.fragment_chat, container, false);

        ctx = listeChatView.getContext();

        chat_liste_container = listeChatView.findViewById(R.id.liste_chat_container);

        int utilisateurId = UtilisateurManager.getIdUtilisateur(ctx);
        int id_role = UtilisateurManager.getIdUtilisateurRole(ctx);
        try {
         ApiListChatFetcher apiListChatFetcher = new ApiListChatFetcher(ctx, chat_liste_container, utilisateurId, id_role);
         String root = "https://pets-friendly.herokuapp.com/chats/recuperation/utilisateur/"+utilisateurId+"/"+id_role;
          apiListChatFetcher.execute(root);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return listeChatView;

    }
}
