package com.example.petsitterisi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.petsitterisi.managers.UtilisateurManager;
import com.example.petsitterisi.services.ApiRechercheFetcher;
import com.example.petsitterisi.services.ChatService;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Objects;

public class RechercheFragment extends Fragment {

    DatePickerDialog picker;
    EditText eText;
    EditText eText_2;
    EditText lieu;
    SwitchMaterial garde_chez_petsitte;
    SwitchMaterial garde_chez_vous;
    SwitchMaterial promenade;
    SwitchMaterial chien;
    SwitchMaterial chat;
    public static EditText test_reponse;
    Context ctx;
    Button boutton_rechercher;
    int moisDebut;
    int jourDebut;
    int anneeDebut;

    int moisFin;
    int jourFin;
    int anneeFin;

    boolean garde_chez_petsitteSate = true;
    boolean garde_chez_vousSate = false;
    boolean promenadeState = false;
    boolean chienState = true;
    boolean chatState = false;
    SharedPreferences sharedpreferences;
    public static Socket mSocket;
    boolean isConnected = false;
    JSONObject data;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View monFragmentRecherche = inflater.inflate(R.layout.fragment_recherche, container, false);
        ctx = monFragmentRecherche.getContext();


        try {
            mSocket = IO.socket("https://pets-friendly.herokuapp.com/");

            mSocket.connect();
            int monIdUtilisateur = UtilisateurManager.getIdUtilisateur(ctx);
            JSONObject idJsonObject = new JSONObject();
            idJsonObject.put("id", monIdUtilisateur);
            this.isConnected = mSocket.connected();
            mSocket.emit("join", idJsonObject);


        } catch (URISyntaxException | JSONException e) {
            e.printStackTrace();
        }

        sharedpreferences = ctx.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        eText = (EditText) monFragmentRecherche.findViewById(R.id.editText1);
        garde_chez_petsitte = monFragmentRecherche.findViewById(R.id.garde_chez_petsitter);
        garde_chez_vous = monFragmentRecherche.findViewById(R.id.garde_chez_vous);
        promenade = monFragmentRecherche.findViewById(R.id.promenade);
        chien = monFragmentRecherche.findViewById(R.id.chien);
        chat = monFragmentRecherche.findViewById(R.id.chat);
        lieu = monFragmentRecherche.findViewById(R.id.lieu);
        eText.setInputType(InputType.TYPE_NULL);

        //
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("id_service_select", "1");
        editor.commit();

        editor.putString("service_animal_select", "Chien");
        editor.commit();

        //

        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ctx, R.style.datePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                moisDebut = monthOfYear;
                                jourDebut = dayOfMonth;
                                anneeDebut = year;
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String debutContrat =  year +"-"+(monthOfYear + 1)+"-"+dayOfMonth;

                                UtilisateurManager.addDataToSharedPreference(ctx, "debut_contrat", debutContrat);
                                
                            }
                        }, year, month, day);
                picker.show();
            }
        });


        eText_2 = (EditText) monFragmentRecherche.findViewById(R.id.editText2);
        eText_2.setInputType(InputType.TYPE_NULL);
        eText_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ctx, R.style.datePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                moisFin = monthOfYear;
                                jourFin = dayOfMonth;
                                anneeFin = year;
                                eText_2.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String finContrat =  year +"-"+(monthOfYear + 1)+"-"+dayOfMonth;

                                UtilisateurManager.addDataToSharedPreference(ctx, "fin_contrat", finContrat);
                                
                            }
                        }, year, month, day);
                picker.show();


            }
        });

        String animal = "";

        garde_chez_petsitte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                garde_chez_petsitteSate = isChecked;
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if(garde_chez_petsitteSate) {
                    editor.putString("id_service_select", "1");
                }
                editor.commit();
            }
        });

        garde_chez_vous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                garde_chez_vousSate = isChecked;
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if(garde_chez_vousSate) {
                    editor.putString("id_service_select", "2");
                }
                editor.commit();
            }
        });

        promenade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                promenadeState = isChecked;
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if(promenadeState){
                    editor.putString("id_service_promenade_select", "3");
                }else{
                    editor.putString("id_service_promenade_select", "0");
                }
                editor.commit();
            }
        });

        chien.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chienState = isChecked;
            }
        });

        chat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chatState = isChecked;
            }
        });

        boutton_rechercher = monFragmentRecherche.findViewById(R.id.btn_rechercher);

        boutton_rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean continutionEtat1 = false;
                boolean continutionEtat2 = false;
                String animal = "";

                if(garde_chez_petsitteSate != garde_chez_vousSate){

                    continutionEtat1 = true;

                }else{
                    continutionEtat1 = false;
                          Toast.makeText(ctx, "Veuillez selectionner omoins 1 service", Toast.LENGTH_LONG).show();
                }

                if(chienState != chatState){
                    continutionEtat2 = true;
                    if(chienState){
                        animal = "Chien";
                    }else{
                        animal = "Chat";
                    }

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("service_animal_select", animal);
                    editor.commit();

                }else{
                    continutionEtat2 = false;
                    Toast.makeText(ctx, "Veuillez selectionner omoins 1 animal", Toast.LENGTH_LONG).show();
                }

                if(continutionEtat1 == true && continutionEtat2 == true){

                    if(!eText.getText().toString().trim().equals("") && !eText_2.getText().toString().trim().equals(""))
                    {

                        Intent intent = new Intent(ctx, BottomNavigationBar.class);
                        intent.putExtra("list_pet_sitter", "true");
                        startActivity(intent);
                    }else{
                        Toast.makeText(ctx, "Veuillez selectionnezr date de debut puis fin du contrat", Toast.LENGTH_LONG).show();
                    }


                }


            }
        });




    return monFragmentRecherche;
    }

}


