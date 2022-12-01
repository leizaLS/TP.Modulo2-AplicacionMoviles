package com.example.tp_modulo2;

public class MainPosts {
    //Los datos aqui deben ser iguales a los de la firebase para que funcione
    private String pTitle, city, pImage, publicationDate, pDescr, state, longitude, latitude;
    private String phoneNumber;

    public MainPosts() { }

    public MainPosts(String pTitle, String city, String pImage, String publicationDate) {
        this.pTitle = pTitle;
        this.city = city;
        this.pImage = pImage;
        this.publicationDate = publicationDate;
    }

    public String getPTitle() {
        return pTitle;
    }

    public void setPTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPImage() {
        return pImage;
    }

    public void setImg(String pImage) {
        this.pImage = pImage;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getpDescr() {
        return pDescr;
    }

    public void setpDescr(String pDescr) {
        this.pDescr = pDescr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
