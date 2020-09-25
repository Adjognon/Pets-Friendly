package com.example.petsitterisi.entitees;

import java.util.Date;

public class Planning {

    private int id;
    private int idContrat;
    private Date dateDebut;
    private Date dateFin;
    private String idProprietaire;
    private String idPetSitter;

    public Planning(int id, int idContrat, Date dateDebut, Date dateFin, String idProprietaire, String idPetSitter) {
        this.id = id;
        this.idContrat = idContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idProprietaire = idProprietaire;
        this.idPetSitter = idPetSitter;
    }

    public int getId() {
        return id;
    }

    public int getIdContrat() {
        return idContrat;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public String getIdProprietaire() {
        return idProprietaire;
    }

    public String getIdPetSitter() {
        return idPetSitter;
    }
}
