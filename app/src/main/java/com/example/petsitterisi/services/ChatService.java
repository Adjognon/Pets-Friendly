package com.example.petsitterisi.services;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petsitterisi.R;
import com.example.petsitterisi.RechercheFragment;
import com.example.petsitterisi.entitees.Utilisateur;
import com.example.petsitterisi.managers.UtilisateurManager;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.logging.Handler;

public class ChatService {

    Context ctx;
    private Socket mSocket;
    JSONObject data;
    TextView chatView;
    String  message;
    Handler handler;
    View chatMessages;

    public ChatService(final Context ctx, View chatMessages) {
        this.ctx = ctx;
        this.chatMessages = chatMessages;
        mSocket = RechercheFragment.mSocket;

    }

    public void start(){
//        if(mSocket != null) {
//            mSocket.connect();
//        }
    }



    public void sendMyMessage(JSONObject messageJsonObject) throws JSONException {
        mSocket.emit("nouveau_message", messageJsonObject);
        LinearLayout chat_message_container = chatMessages.findViewById(R.id.container_message_list);
        ScrollView message_container_scrollview = chatMessages.findViewById(R.id.message_container_scrollview);

        View cardMessageEnvoyer = View.inflate(ctx , R.layout.activity_item_message_envoyer,null);
        TextView messageBulbeTextView = cardMessageEnvoyer.findViewById(R.id.text_message_body_envoyer);
        messageBulbeTextView.setText(messageJsonObject.getString("message"));
        chat_message_container.addView(cardMessageEnvoyer);
        message_container_scrollview.fullScroll(View.FOCUS_DOWN);

    }



}
