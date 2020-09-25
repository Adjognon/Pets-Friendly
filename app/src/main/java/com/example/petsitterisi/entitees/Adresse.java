package com.example.petsitterisi.entitees;

public class Adresse {

    private int id;
    private int idUtilisateur;
    private int numeroRue;
    private String nomRue;
    private int codePostal;
    private String ville;
    private String pays;
    private int numeroAppt;

    public Adresse(int id, int idUtilisateur, int numeroRue, String nomRue, int codePostal, String ville, String pays, int numeroAppt) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.numeroRue = numeroRue;
        this.nomRue = nomRue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
        this.numeroAppt = numeroAppt;
    }

    public int getId() {
        return id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public int getNumeroRue() {
        return numeroRue;
    }

    public String getNomRue() {
        return nomRue;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public String getVille() {
        return ville;
    }

    public String getPays() {
        return pays;
    }

    public int getNumeroAppt() {
        return numeroAppt;
    }
}
