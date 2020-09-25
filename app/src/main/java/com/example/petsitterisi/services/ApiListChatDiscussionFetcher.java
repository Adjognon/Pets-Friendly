package com.example.petsitterisi.services;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.petsitterisi.BottomNavigationBar;
import com.example.petsitterisi.R;
import com.example.petsitterisi.managers.UtilisateurManager;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
public class ApiListChatDiscussionFetcher extends AsyncTask<String, Nullable, String> {

    private Context  context;
    //LinearLayout ll;
    View view;
    LinearLayout ll;
    LinearLayout chat_message_container;
    View messageListe;
    SharedPreferences sharedpreferences;
    TextView item_message_recu;
    TextView item_message_envoye;
    EditText edittext_chatbox;
    Button button_chatbox_send;
    ChatService chatService;
    private Object LinearLayout;

    public ApiListChatDiscussionFetcher(Context  context, LinearLayout llParam, EditText edittext_chatbo, Button tem_message_envoye) {
        this.context = context;
        this.ll = llParam;
        this.edittext_chatbox = edittext_chatbox;
        this.button_chatbox_send = button_chatbox_send;

        TextView item_message_envoye;
        Button btn_envoyer_discussion;

    }

    @Override
    protected String doInBackground(String... urls) {

//        String result = "";
//
//        try {
//            URL url = new URL(urls[0]);
//
//
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setDoOutput(false);
//            urlConnection.setDoInput(true);
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
//            urlConnection.connect();
//
//            int codeRetour = urlConnection.getResponseCode();
//
//
//            if (codeRetour == HttpURLConnection.HTTP_OK) {
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//
//                String line = "";
//                while ((line = in.readLine()) != null)
//                    result += line;
//
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
              return "";
    }





    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"ResourceAsColor", "SetTextI18n", "WrongViewCast"})
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {

            String tContents = "";
            String concat = "";
            try {
                InputStream stream = context.getAssets().open("chat_discussions.json");
                int size = stream.available();
                byte[] buffer = new byte[size];
                stream.read(buffer);
                stream.close();
                tContents = new String(buffer);

                JSONArray jsonArray = new JSONArray(tContents);



//                View headerChat = View.inflate(context , R.layout.header_footer_chat,null);
//                ll.addView(messageListe);



//                final View cardMessageEnvoyerParam = View.inflate(context , R.layout.activity_item_message_envoyer,null);
//                View viewMessageList = View.inflate(context , R.layout.activity_message_list,null);
//
//                //ll.addView(cardMessageEnvoyerParam);
//
//                TextInputEditText text_message_discussion;
//                btn_envoyer_discussion  = (Button) viewMessageList.findViewById(R.id.button_chatbox_send);
//                final TextView messageItemEnvoyer = (TextView) cardMessageEnvoyerParam.findViewById(R.id.text_message_body_envoyer);
//                text_message_discussion =  viewMessageList.findViewById(R.id.edittext_chatbox);
//
//                final Editable textMsgEnvoyerDepuisEditText =  text_message_discussion.getText();






//                Toast.makeText(context.getApplicationContext(), textMsgEnvoyerDepuisEditText, Toast.LENGTH_LONG).show();

//                btn_envoyer_discussion.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
////
////                            try {
////                                Intent intent = new Intent(context, BottomNavigationBar.class);
////                                intent.putExtra("Demande", "true");
////
////                                context.startActivity(intent);
////                            } catch (Exception e) {
////                                e.printStackTrace();
////                            }
//
//
//
//                            final View cardMessageEnvoyerParamsss = View.inflate(context , R.layout.activity_item_message_envoyer,null);
//                            ll.addView(cardMessageEnvoyerParamsss);
//
//                            messageItemEnvoyer.setText((CharSequence) textMsgEnvoyerDepuisEditText);
//
//
//
//                            //UtilisateurManager.addMessageContacterInsideDiscussion(context, "message_contacter", textMsgEnvoyer);
//
//                        }
//                    });
//
//                ll.addView(cardMessageEnvoyerParam);
//                lll.addView(viewMessageList);




//                ViewGroup container = null;
//                LayoutInflater  inflater = null;
//                assert inflater != null;
//
//                View rootView =   inflater.inflate(R.layout.activity_message_list, container, false);
//
//                LinearLayout container_message_liste = (LinearLayout) rootView.findViewById(R.id.container_message_list);
//                //View cardMessageEnvoyerParamss = LayoutInflater.from(context).inflate(R.layout.activity_item_message_envoyer , (ViewGroup) container_message_liste, false);
//                Intent intentte = new Intent();
//                intentte.getStringExtra("textMsgEnvoyer");







                //iciiiiiiiiiiiiiiiiii

//                chat_message_container = view.findViewById(R.id.container_message_list);
//
//                final View cardMessageEnvoyerParam = View.inflate(context , R.layout.activity_item_message_envoyer,null);
//
//                final TextView messageItemEnvoyer = (TextView) cardMessageEnvoyerParam.findViewById(R.id.text_message_body_envoyer);
//
//                String messageEnvoyerDepuisContacterInsideProfilSitter = UtilisateurManager.getMessageContacterInsideDiscussion(context);
//
//                messageItemEnvoyer.setText(messageEnvoyerDepuisContacterInsideProfilSitter);
//
//
//
//                if (!messageEnvoyerDepuisContacterInsideProfilSitter.equals(""))
//                {
//
//                    // UtilisateurManager.getMessageContacterInsideDiscussion(context,"");
//                    //ll.addView(cardMessageEnvoyerParam);
//
//                    chat_message_container.addView(cardMessageEnvoyerParam);
//
//
//
//                }











            for(int i = 0; i < jsonArray.length(); i++){

                JSONObject chatJsonObject = jsonArray.getJSONObject(i);

                String idUtlisateur = chatJsonObject.getString("id_utlisateur");
                String nomUtilisateur = chatJsonObject.getString("name");
                String urlImage = chatJsonObject.getString("url_photo");
                String messageConversation = chatJsonObject.getString("discussion");
                String heureMessage = chatJsonObject.getString("date");
//                String msg_lu = "true";

                //String messageEstLu = chatJsonObject.getString("message_est_lu");
                //String messageAEteLu = chatJsonObject.getString("a_lu");



//                if (idUtlisateur.equals("1")  ) // message envoyer de michel
//                {
//
//
//
//                    TextView heureMessageEnvoyer = cardMessageEnvoyerParam.findViewById(R.id.text_message_time_envoyer);
//                    //TextView messageEnvoyer = cardMessageEnvoyerParam.findViewById(R.id.text_message_body_envoyer);
////                    ImageView messageLu = cardMessageEnvoyerParam.findViewById(R.id.image_message_lu);
//                    //final Editable textMsgEnvoyer ;
//
//                    heureMessage = DateConvertisseur(heureMessage);
//                    heureMessageEnvoyer.setText(heureMessage);
//
//                    messageItemEnvoyer.setText(messageConversation);
//
////                    textMsgEnvoyer = editTextConversation.getText();
////
////                    btn_envoyer_discussion.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////
////                            messageEnvoyer.setText(textMsgEnvoyer);
////                            ll.addView(cardMessageEnvoyerParam);
////                        }
////                    });
//
//
//
//                    ll.addView(cardMessageEnvoyerParam);
//
//
//
//                    //item_message_envoye = cardMessageEnvoyerParam.findViewById(R.id.text_message_body_envoyer);
//
//
//
//
//                }





                if(idUtlisateur.equals("2")){ //  message recus de kamel


                    View cardMessageRecuParam =  View.inflate(context , R.layout.activity_item_message_recus,null);





                    TextView messageRecu = cardMessageRecuParam.findViewById(R.id.text_message_body_recu);
                    String non_chat_header = UtilisateurManager.getNomChat(context);
                    TextView heureMessageRecu = cardMessageRecuParam.findViewById(R.id.text_message_time_recus);
                    TextView nomUtilisateurRecus = cardMessageRecuParam.findViewById(R.id.text_message_name_recus);
                    ImageView UrlPhotoUtilisateurRecus = cardMessageRecuParam.findViewById(R.id.image_message_profile);
                    ImageView messageLu = cardMessageRecuParam.findViewById(R.id.image_message_lu);



                   // String.valueOf(messageAEteLu.equals("true"));

                    heureMessage = DateConvertisseur(heureMessage);
                    heureMessageRecu.setText(heureMessage);
                    UrlPhotoUtilisateurRecus.setImageResource(R.drawable.rectangle_66);

                    //messageLu.setImageResource(R.drawable.icone_message_lu);

                    messageRecu.setText(messageConversation);

                    nomUtilisateurRecus.setText(non_chat_header);

                    //ll.addView(cardMessageEnvoyerParam);
                   // chat_message_container.addView(cardMessageRecuParam);


//                     if (!messageAEteLu.equals("false")){
//                      messageLu.setImageResource(R.drawable.icone_message_lu);
//                      //messageEstLu = chatJsonObject.put("message_est_lu", msgLu).toString();
//                     //String.valueOf(messageEstLu.equals("true"));
//                  }

                   // item_message_recu =  cardMessageRecuParam.findViewById(R.id.text_message_body_recu);



                }



           }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }catch (Exception e)
        {
            e.printStackTrace();
        }


//        View footerChat = View.inflate(context , R.layout.footer_bar_chat,null);
//        ll.addView(footerChat);


//
//        button_chatbox_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String message = edittext_chatbox.getText().toString();
//
//                if(!message.trim().equals("")){
//                    chatService.sendMyMessage(message);
//                }else{
//                    Toast.makeText(context, "Champ message vide", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String DateConvertisseur(String timestampWithTimeZone){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(timestampWithTimeZone, inputFormatter);
        String formattedDate = outputFormatter.format(date);
        return formattedDate;
    }

    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader rd = new BufferedReader(isr);

        String in = "";

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.toString();
    }


}
