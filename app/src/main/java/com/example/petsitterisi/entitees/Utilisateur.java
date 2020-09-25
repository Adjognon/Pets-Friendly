package com.example.petsitterisi.entitees;

import java.util.Date;

public class Utilisateur {

    private int id;
    private int idRole;
    private int idAdresse;
    private String nom;
    private String prenom;
    private int age;
    private String email;
    private String sexe;
    private String motDePass;
    private String telephone;
    private Double  secteurAction;
    private boolean  estDisponible;
    private boolean  estValider;
    private boolean  estActive;
    private String  idActivation;
    private Double remunerationPetSitter;
    private Date dateCreation;


    public Utilisateur(int id, int idRole, int idAdresse, String nom, String prenom, int age, String email, String sexe, String motDePass, String telephone, Double secteurAction, boolean estDisponible, boolean estValider, boolean estActive, String idActivation, Double remunerationPetSitter, Date dateCreation) {
        this.id = id;
        this.idRole = idRole;
        this.idAdresse = idAdresse;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.sexe = sexe;
        this.motDePass = motDePass;
        this.telephone = telephone;
        this.secteurAction = secteurAction;
        this.estDisponible = estDisponible;
        this.estValider = estValider;
        this.estActive = estActive;
        this.idActivation = idActivation;
        this.remunerationPetSitter = remunerationPetSitter;
        this.dateCreation = dateCreation;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public void setIdAdresse(int idAdresse) {
        this.idAdresse = idAdresse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setMotDePass(String motDePass) {
        this.motDePass = motDePass;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setSecteurAction(Double secteurAction) {
        this.secteurAction = secteurAction;
    }

    public void setEstDisponible(boolean estDisponible) {
        this.estDisponible = estDisponible;
    }

    public void setEstValider(boolean estValider) {
        this.estValider = estValider;
    }

    public void setEstActive(boolean estActive) {
        this.estActive = estActive;
    }

    public void setIdActivation(String idActivation) {
        this.idActivation = idActivation;
    }

    public void setRemunerationPetSitter(Double remunerationPetSitter) {
        this.remunerationPetSitter = remunerationPetSitter;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
