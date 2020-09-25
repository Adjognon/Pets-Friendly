package com.example.petsitterisi.entitees;

import java.util.Date;

public class Contrat {

    private int id;
    private int idFacture;
    private boolean estAccepte;
    private boolean estLuProprietaire;
    private boolean estLuPetSitter;
    private boolean estTermine;
    private boolean encoreDisponible;
    private Date dateDebut;
    private Date dateFin;

    public Contrat(int id, int idFacture, boolean estAccepte, boolean estLuProprietaire, boolean estLuPetSitter, boolean estTermine, boolean encoreDisponible, Date dateDebut, Date dateFin) {
        this.id = id;
        this.idFacture = idFacture;
        this.estAccepte = estAccepte;
        this.estLuProprietaire = estLuProprietaire;
        this.estLuPetSitter = estLuPetSitter;
        this.estTermine = estTermine;
        this.encoreDisponible = encoreDisponible;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getId() {
        return id;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public boolean isEstAccepte() {
        return estAccepte;
    }

    public boolean isEstLuProprietaire() {
        return estLuProprietaire;
    }

    public boolean isEstLuPetSitter() {
        return estLuPetSitter;
    }

    public boolean isEstTermine() {
        return estTermine;
    }

    public boolean isEncoreDisponible() {
        return encoreDisponible;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }
}
