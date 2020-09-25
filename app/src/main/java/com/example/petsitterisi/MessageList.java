package com.example.petsitterisi;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
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

import com.example.petsitterisi.entitees.Utilisateur;
import com.example.petsitterisi.managers.UtilisateurManager;
import com.example.petsitterisi.services.ApiListChatDiscussionFetcher;
import com.example.petsitterisi.services.ApiListChatFetcher;
import com.example.petsitterisi.services.ApiListReservationFetcher;
import com.example.petsitterisi.services.ApiMessageListFetcher;
import com.example.petsitterisi.services.ChatService;
import com.github.nkzawa.emitter.Emitter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
public class MessageList extends Fragment {
    Context ctx;
    LinearLayout chat_message_container;

    EditText edittext_chatbox;
    Button button_chatbox_send;
//    LinearLayout icone_retour;

     Button icone_retour;
    Button btn_envoyer_discussion;

    //TextInputEditText text_message_discussion;
    EditText text_message_discussion;
    Editable textMsgEnvoyer ;
    Editable textMsgRecus ;
    // Define the pic id
    private static final int pic_id = 123;
    Button ajouter_image;
    ImageView image_recuperer ;
    ImageView img_recuper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View chatMessages = null;
        try {
            chatMessages = inflater.inflate(R.layout.activity_message_list, container, false);


       // final View view_photos_envoyer =  inflater.inflate(R.layout.photo_envoyer, container, false);

        ctx = chatMessages.getContext();
        final Handler handler = new Handler();


        final View cardMessageEnvoyer = View.inflate(ctx , R.layout.activity_item_message_envoyer,null);
        final ChatService chatService = new ChatService(ctx, chatMessages);

        TextView nomInterlocuteur = chatMessages.findViewById(R.id.nom_utilsateur_message_recus);
        final String non_chat_header = UtilisateurManager.getNomChat(ctx);
        nomInterlocuteur.setText(non_chat_header);


        icone_retour = chatMessages.findViewById(R.id.icone_retour);
        icone_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, BottomNavigationBar.class);
                intent.putExtra("Chat", "true");
                ctx.startActivity(intent);
            }
        });

            chat_message_container = chatMessages.findViewById(R.id.container_message_list);
            btn_envoyer_discussion  = chatMessages.findViewById(R.id.button_chatbox_send);
            text_message_discussion = chatMessages.findViewById(R.id.edittext_chatbox);

            ajouter_image = chatMessages.findViewById(R.id.icone_ajoute_photo_conversation);
//        image_recuperer = (ImageView) new ImageView(ctx);

            final MediaPlayer son_message_envoyer = MediaPlayer.create(ctx, R.raw.son_message_envoye);
            final MediaPlayer son_message_recu = MediaPlayer.create(ctx, R.raw.son_message_recu);

        final int chat_id_petsitter = Integer.parseInt(UtilisateurManager.getDataFromSharePreference(ctx, "chat_id_petsitter"));
        final int chat_id_proprietaire = Integer.parseInt(UtilisateurManager.getDataFromSharePreference(ctx, "chat_id_proprietaire"));

       ApiMessageListFetcher apiMessageListFetcher = new ApiMessageListFetcher(ctx, chat_message_container, chatService, chat_id_proprietaire, chat_id_petsitter);
       apiMessageListFetcher.execute("https://pets-friendly.herokuapp.com/chats/recuperation");

            RechercheFragment.mSocket.on("nouveau_message", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {

                    JSONObject data = (JSONObject) args[0];

                    String message = null;
                    try {

                        message = data.getString("message");

                        final String finalMessage = message;
                        final String finalMessage1 = message;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                View cardMessageRecus = View.inflate(ctx , R.layout.activity_item_message_recus,null);
                                TextView messageBulbeTextView = cardMessageRecus.findViewById(R.id.text_message_body_recu);
                                messageBulbeTextView.setText(finalMessage1);
                                chat_message_container.addView(cardMessageRecus);
                                son_message_recu.start();
                                //Toast.makeText(ctx, finalMessage, Toast.LENGTH_LONG).show();
                            }
                        }, 1000);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            });




        btn_envoyer_discussion.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String nouveauMmessage = text_message_discussion.getText().toString();

                // photo
                final MediaPlayer son_photo_envoyer = MediaPlayer.create(ctx, R.raw.son_message_envoye);
                ajouter_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        final View view_photos_envoyer = View.inflate(ctx, R.layout.photo_envoyer, null);
                        image_recuperer = view_photos_envoyer.findViewById(R.id.img_recup);
                        //son_photo_envoyer.start();
                        chat_message_container.addView(view_photos_envoyer);

                        startActivityForResult(camera_intent, pic_id);

                    }
                });



                if(!nouveauMmessage.equals("")) {

                    final View cardMessageEnvoyerParam = View.inflate(ctx, R.layout.activity_item_message_envoyer, null);

                    final TextView messageItemEnvoyer = (TextView) cardMessageEnvoyerParam.findViewById(R.id.text_message_body_envoyer);
                    TextView heureMessageEnvoyer = cardMessageEnvoyerParam.findViewById(R.id.text_message_time_envoyer);
                    String messageEnvoyerDepuisContacterInsideProfilSitter = UtilisateurManager.getMessageContacterInsideDiscussion(ctx);
                    String heureMsgEnvoyerContacter = UtilisateurManager.getHeureMessageEnvoyer(ctx);


                    int id_utilisateur = UtilisateurManager.getIdUtilisateur(ctx);
                    int id_role = UtilisateurManager.getIdUtilisateurRole(ctx);

                    int id_to = 0;

                    if (id_role == 2) {
                        id_to = chat_id_petsitter;
                    } else if (id_role == 3) {
                        id_to = chat_id_proprietaire;
                    }

                    try {

                        JSONObject chatJsonObject = new JSONObject();
                        chatJsonObject.put("idFrom", id_utilisateur);
                        chatJsonObject.put("idTo", id_to);
                        chatJsonObject.put("id_proprietaire", chat_id_proprietaire);
                        chatJsonObject.put("id_petsitter", chat_id_petsitter);

                        chatJsonObject.put("message_entre", chat_id_proprietaire + "_" + chat_id_petsitter);
                        chatJsonObject.put("message", nouveauMmessage);
                        chatService.sendMyMessage(chatJsonObject);
                        text_message_discussion.setText("");
                        son_message_envoyer.start();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }




        });


        }catch (Exception e){
            e.printStackTrace();
        }
        return chatMessages;
    }
//
//    private void addImageBetweentext(Bitmap drawable, EditText editText1) {
//
//
//        int selectionCursor = editText1.getSelectionStart();
//        editText1.getText().insert(selectionCursor, ".");
//        selectionCursor = editText1.getSelectionStart();
//
//        SpannableStringBuilder builder = new SpannableStringBuilder(editText1.getText());
//        builder.setSpan(new ImageSpan(drawable), selectionCursor - ".".length(), selectionCursor,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        editText1.setText(builder);
//        editText1.setSelection(selectionCursor);
//    }


    // This method will help to retrieve the image
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {

            // BitMap is data structure of image file
            // which stor the image in memory
            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            // Set the image in imageview for display
            image_recuperer.setImageBitmap(photo);
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    private String DateConvertisseur(String timestampWithTimeZone){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(timestampWithTimeZone, inputFormatter);
        String formattedDate = outputFormatter.format(date);
        return formattedDate;
    }

}