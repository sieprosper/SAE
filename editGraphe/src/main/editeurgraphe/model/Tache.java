package editeurgraphe.model;

import java.awt.Color;
import java.util.*;

abstract class Tache {
    // private char id ='a';
    private String nom;
    private int duree;
    Color couleur;
    private int x, y;


    Tache(String nom, int duree) {
        this.nom = nom;
        this.duree = duree;
        this.x = 0;
        this.y = 0;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



    @Override
    public String toString() {
        // edit pour getInfoTache()
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tache)) return false;
        Tache tache = (Tache) o;
        return Objects.equals(nom, tache.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

}