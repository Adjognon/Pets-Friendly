package com.example.petsitterisi.entitees;

public class Service {

    private int id;
    private String description;
    private Double prixService;

    public Service(int id, String description, Double prixService) {
        this.id = id;
        this.description = description;
        this.prixService = prixService;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrixService() {
        return prixService;
    }
}
