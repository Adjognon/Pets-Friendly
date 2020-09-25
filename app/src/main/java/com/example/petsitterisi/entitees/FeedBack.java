package com.example.petsitterisi.entitees;

public class FeedBack {

    private int id;
    private int idContrat;
    private String commentaire;
    private int etoile;

    public FeedBack(int id, int idContrat, String commentaire, int etoile) {
        this.id = id;
        this.idContrat = idContrat;
        this.commentaire = commentaire;
        this.etoile = etoile;
    }

    public int getId() {
        return id;
    }

    public int getIdContrat() {
        return idContrat;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public int getEtoile() {
        return etoile;
    }
}
