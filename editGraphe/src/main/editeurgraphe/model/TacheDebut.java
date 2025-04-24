package editeurgraphe.model;

class TacheDebut extends Tache {
    public TacheDebut(String nom, int id) {
        super(nom, id);
    }

    @Override
    public String toString() {
        return "TacheDebut{" +
                "nom='" + nom + '\'' +
                ", id=" + id +
                '}';
    }
}