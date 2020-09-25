package com.example.petsitterisi;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import com.example.petsitterisi.managers.ConnexionManager;

public class Connexion extends Activity {

    TextView top_textView;
    Button connexion_button;
    Context ctx;
    EditText e_mail;
    EditText mot_de_passe;
    BottomNavigationBar bottomNavigationBar = null;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_connexion);

        ctx = this;

        connexion_button = findViewById(R.id.connexion_button);

        e_mail =  findViewById(R.id.e_mail);
        error = findViewById(R.id.error);
        mot_de_passe = findViewById(R.id.mot_de_passe);

        connexion_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!e_mail.getText().toString().equals("") && !mot_de_passe.getText().toString().equals("")) {

                    Toast.makeText(ctx, "Loading...", Toast.LENGTH_SHORT).show();
                    String email = e_mail.getText().toString();
                    String motDePasse = mot_de_passe.getText().toString();

                    ConnexionManager.getUtilisateur(ctx, error, email, motDePasse);

                }else{
                    Toast.makeText(ctx, "Veuillez remplire les champ", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}