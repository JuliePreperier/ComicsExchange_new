package com.example.comicsexchange_new;

/**
 * Created by Julie on 28.04.2017.
 */

public class Comic {

    private int color;
    private String titre;
    private String text;

    public Comic(int color, String titre, String text){
        this.color=color;
        this.titre=titre;
        this.text=text;
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

}
