package com.example.comicsexchange_new;

/**
 * Created by Julie on 28.04.2017.
 */

public class Comic {

    public int id;
    public String picture;
    public String titre;
    public String text;

    public Comic(){
        // empty constructor
    }


    // constructor with all information
    public Comic(int id, String picture, String titre, String text){
        this.picture=picture;
        this.titre=titre;
        this.text=text;
        this.id=id;
    }


    // constructor for listview (without ids)
    public Comic(String picture, String titre, String text){
        this.picture=picture;
        this.text=text;
        this.titre=titre;
    }


    // GETTERS AND SETTERS


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


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Comic)){
            return false;
        }
        Comic c = (Comic) obj;
        if(this.id == c.getId()){
            return true;
        }
        return false;
    }

}
