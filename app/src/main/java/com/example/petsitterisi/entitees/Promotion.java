package com.example.petsitterisi.entitees;

import java.util.Date;

public class Promotion {

    private int id;
    private String codePromotion;
    private Date dateFin;
    private int pourcentage;

    public Promotion(int id, String codePromotion, Date dateFin, int pourcentage) {
        this.id = id;
        this.codePromotion = codePromotion;
        this.dateFin = dateFin;
        this.pourcentage = pourcentage;
    }

    public int getId() {
        return id;
    }

    public String getCodePromotion() {
        return codePromotion;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public int getPourcentage() {
        return pourcentage;
    }
}
