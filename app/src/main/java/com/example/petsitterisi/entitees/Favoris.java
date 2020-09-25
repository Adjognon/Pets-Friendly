package com.example.petsitterisi.entitees;

public class Favoris {

    private int id;
    private int idPetSitter;
    private int idProprietaire;

    public Favoris(int id, int idPetSitter, int idProprietaire) {
        this.id = id;
        this.idPetSitter = idPetSitter;
        this.idProprietaire = idProprietaire;
    }

    public int getId() {
        return id;
    }

    public int getIdPetSitter() {
        return idPetSitter;
    }

    public int getIdProprietaire() {
        return idProprietaire;
    }
}
