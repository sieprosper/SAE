import java.util.*;

public class Tache {
    private String nom;
    private int duree;
    private List<Tache> predecesseurs;
    private List<Tache> successeurs;
    private int x, y;

    public Tache(String nom, int duree) {
        this.nom = nom;
        this.duree = duree;
        this.predecesseurs = new ArrayList<>();
        this.successeurs = new ArrayList<>();
        this.x = 0;
        this.y = 0;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }
    public List<Tache> getPredecesseurs() { return predecesseurs; }
    public List<Tache> getSuccesseurs() { return successeurs; }

    public void ajouterSuccesseur(Tache t) {
        if (!successeurs.contains(t)) {
            successeurs.add(t);
            t.ajouterPredecesseur(this);
        }
    }

    public void supprimerSuccesseur(Tache t) {
        successeurs.remove(t);
        t.predecesseurs.remove(this);
    }

    public void ajouterPredecesseur(Tache t) {
        if (!predecesseurs.contains(t)) {
            predecesseurs.add(t);
        }
    }

    public void supprimerPredecesseur(Tache t) {
        predecesseurs.remove(t);
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    @Override
    public String toString() {
        return nom + " (" + duree + ")";
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