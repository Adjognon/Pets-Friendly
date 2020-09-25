package com.example.petsitterisi.entitees;

public class Animal {

    private int id;
    private String race;
    private String typeAnimal;
    private Double poidsAnimal;
    private String  sexeAnimal;
    private String  nom;
    private int  age;

    public Animal(int id, String race, String typeAnimal, Double poidsAnimal, String sexeAnimal, String nom, int age) {
        this.id = id;
        this.race = race;
        this.typeAnimal = typeAnimal;
        this.poidsAnimal = poidsAnimal;
        this.sexeAnimal = sexeAnimal;
        this.nom = nom;
        this.age = age;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRace() {
        return race;
    }
    public void setRace(String race) {
        this.race = race;
    }
    public String getTypeAnimal() {
        return typeAnimal;
    }
    public void setTypeAnimal(String typeAnimal) {
        this.typeAnimal = typeAnimal;
    }
    public Double getPoidsAnimal() {
        return poidsAnimal;
    }
    public void setPoidsAnimal(Double poidsAnimal) {
        this.poidsAnimal = poidsAnimal;
    }
    public String getSexeAnimal() {
        return sexeAnimal;
    }
    public void setSexeAnimal(String sexeAnimal) {
        this.sexeAnimal = sexeAnimal;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
