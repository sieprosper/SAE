import java.io.*;
import java.util.*;

public class GrapheManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Tache> taches;

    public GrapheManager() {
        taches = new HashMap<>();
    }

    public boolean ajouterTache(String nom, int duree) {
        if (taches.containsKey(nom)) return false;
        Tache t = new Tache(nom, duree);
        taches.put(nom, t);
        return true;
    }

    public boolean supprimerTache(String nom) {
        Tache t = taches.get(nom);
        if (t == null) return false;

        if (!t.getSuccesseurs().isEmpty()) {
            return false;
        }

        for (Tache pred : new ArrayList<>(t.getPredecesseurs())) {
            pred.supprimerSuccesseur(t);
        }

        taches.remove(nom);
        return true;
    }

    public boolean modifierTache(String nom, String nouveauNom, int nouvelleDuree) {
        Tache t = taches.get(nom);
        if (t == null) return false;

        t.setNom(nouveauNom);
        t.setDuree(nouvelleDuree);

        taches.remove(nom);
        taches.put(nouveauNom, t);
        return true;
    }

    public boolean ajouterLien(String origine, String destination) {
        Tache t1 = taches.get(origine);
        Tache t2 = taches.get(destination);
        if (t1 == null || t2 == null) return false;

        t1.ajouterSuccesseur(t2);
        return true;
    }

    public boolean supprimerLien(String origine, String destination) {
        Tache t1 = taches.get(origine);
        Tache t2 = taches.get(destination);
        if (t1 == null || t2 == null) return false;

        t1.supprimerSuccesseur(t2);
        return true;
    }

    public boolean existeTache(String nom) {
        return taches.containsKey(nom);
    }

    public Tache getTache(String nom) {
        return taches.get(nom);
    }

    public Collection<Tache> getToutesLesTaches() {
        return taches.values();
    }

    public void afficherGraphe() {
        for (Tache t : taches.values()) {
            System.out.print(t.getNom() + " → ");
            for (Tache succ : t.getSuccesseurs()) {
                System.out.print(succ.getNom() + " ");
            }
            System.out.println();
        }
    }

    public void ajouterTachesDebutEtFin() {
        if (taches.containsKey("Début") || taches.containsKey("Fin")) return;

        Tache debut = new Tache("Début", 0);
        Tache fin = new Tache("Fin", 0);

        taches.put("Début", debut);
        taches.put("Fin", fin);

        for (Tache t : taches.values()) {
            if (t == debut || t == fin) continue;
            if (t.getPredecesseurs().isEmpty()) {
                debut.ajouterSuccesseur(t);
            }
            if (t.getSuccesseurs().isEmpty()) {
                t.ajouterSuccesseur(fin);
            }
        }
    }

    public void enregistrerGraphe(String cheminFichier) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cheminFichier))) {
            oos.writeObject(this);
        }
    }

    public static GrapheManager chargerGraphe(String cheminFichier) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminFichier))) {
            return (GrapheManager) ois.readObject();
        }
    }
}
