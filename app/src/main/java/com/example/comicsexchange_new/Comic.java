package com.example.comicsexchange_new;

/**
 * Created by Julie on 28.04.2017.
 */

public class Comic {

    private int id;
    private int color;
    private String titre;
    private String text;

    public Comic(int id, int color, String titre, String text){
        this.color=color;
        this.titre=titre;
        this.text=text;
        this.id=id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
