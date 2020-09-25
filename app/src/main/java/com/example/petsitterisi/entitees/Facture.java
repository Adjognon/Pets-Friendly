package com.example.petsitterisi.entitees;

public class Facture {

    private int  idFacture;
    private int  idPromotion;
    private Double  prix;

    public Facture(int idFacture, int idPromotion, Double prix) {
        this.idFacture = idFacture;
        this.idPromotion = idPromotion;
        this.prix = prix;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public int getIdPromotion() {
        return idPromotion;
    }

    public Double getPrix() {
        return prix;
    }
}
