package com.example.petsitterisi.services;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import com.example.petsitterisi.sqliteHelper.PetHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
public class ConnexionBd {

    private static int version = 1;
    private static String nom = "titi";
    private static SQLiteDatabase bd;
    public static SQLiteDatabase getBd(Context context) {

        PetHelper petHelper = new PetHelper(context, nom, null, version);
        bd = petHelper.getWritableDatabase();
        return bd;
    }
    public static void close() {
        bd.close();
    }

    public static void copyBdFromAssets(Context context) {
        File bdApp = context.getDatabasePath(nom);
        //if (!bdApp.exists()) getBd(context);
        //        Log.d("debugApp", bdApp.getPath());
        AssetManager assetManager = context.getAssets();
        try {
            InputStream in = assetManager.open("bd/pet_sitter.db");
            FileOutputStream out = new FileOutputStream(bdApp);
            byte[] buffer = new byte[256];
            while (in.read(buffer) != -1) {
                out.write(buffer);
                buffer = new byte[256];
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
