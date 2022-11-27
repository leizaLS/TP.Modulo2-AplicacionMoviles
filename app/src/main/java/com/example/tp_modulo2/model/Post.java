package com.example.tp_modulo2.model;

import java.io.Serializable;

public class Post implements Serializable {

    private String city;
    private String country;
    private String imagen;
    private String description;


    public Post(String city, String country,String imagen,String description) {
        this.city = city;
        this.country = country;
        this.imagen = imagen;
        this.description = description;
    }

    public Post() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
