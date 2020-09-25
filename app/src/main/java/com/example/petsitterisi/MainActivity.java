package com.example.petsitterisi;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.petsitterisi.managers.UtilisateurManager;
import com.example.petsitterisi.services.ConnexionBd;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.InputStream;


public class MainActivity extends Activity {


    Button connexion_button;
    Context ctx;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onStart() {
        super.onStart();
        //cacher temporairement la navigation bar android par defaut
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); // cacher temporairement avec transparence


        final MediaPlayer[] son_accueille = {MediaPlayer.create(ctx, R.raw.son_accueille_friendly)};

        VideoView videoView =(VideoView)findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.video_accueille_chiens;
        videoView.setMediaController(mediaController);
        videoView.setVideoPath(path);
        videoView.requestFocus();
        mediaController.setVisibility(View.GONE);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
                mp.setLooping(true);
               // son_accueille[0].setLooping(true);

            }
        });
        videoView.start();
       // son_accueille[0].start();


        //ouvrir l'activite connexion
        connexion_button = findViewById(R.id.connexion_button);
        connexion_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, Connexion.class);


                if (son_accueille[0].isPlaying()) {
                    son_accueille[0].stop();

                }
              //  son_accueille[0].start();

                startActivity(intent);




            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ctx = this;
        super.onCreate(savedInstanceState);

        //cacher temporairement  la bare d'etat du haut
        requestWindowFeature(Window.FEATURE_NO_TITLE); getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        //cacher temporairement  la bare d'etat du haut
//        requestWindowFeature(Window.FEATURE_NO_TITLE); getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ConnexionBd.copyBdFromAssets(this);

        int idUtilisateur = UtilisateurManager.getIdUtilisateur(ctx);


        if(idUtilisateur > 0){
            //Utilisateur s'est deja loger tanto

            try {
                Intent intent = new Intent(ctx, BottomNavigationBar.class);
                startActivity(intent);
            }catch(Exception e){
                e.printStackTrace();
            }


        }

    }

    }

