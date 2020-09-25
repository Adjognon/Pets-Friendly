package com.example.petsitterisi.sqliteHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.petsitterisi.services.ConnexionBd;

public class PetHelper extends SQLiteOpenHelper {
    Context context;
    public PetHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion > oldVersion){
            ConnexionBd.copyBdFromAssets(context);
        }

    }

}