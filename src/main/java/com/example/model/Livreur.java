package com.example.model;

public class Livreur {
	private int id;
    private String nom;
    private String email;
    private String telephone;
    private boolean disponible;

	public Livreur(int id, String nom, String email, String telephone, boolean disponible) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.disponible = disponible;
    }

	 // Constructeur vide
	 public Livreur() {
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }


}
