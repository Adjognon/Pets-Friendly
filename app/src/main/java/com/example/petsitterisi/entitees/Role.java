package com.example.petsitterisi.entitees;

public class Role {

    private int id;
    private String role;
    private String definition;

    public Role(int id, String role, String definition) {
        this.id = id;
        this.role = role;
        this.definition = definition;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getDefinition() {
        return definition;
    }
}
