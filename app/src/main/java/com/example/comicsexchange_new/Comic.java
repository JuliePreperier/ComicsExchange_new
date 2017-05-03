package com.example.comicsexchange_new;

/**
 * Created by Julie on 28.04.2017.
 */

public class Comic {

    private int id;
    private String picture;
    private String titre;
    private String text;

    public Comic(){

    }

    public Comic(int id, String picture, String titre, String text){
        this.picture=picture;
        this.titre=titre;
        this.text=text;
        this.id=id;
    }

    public Comic(String picture, String titre, String text){
        this.picture=picture;
        this.text=text;
        this.titre=titre;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
