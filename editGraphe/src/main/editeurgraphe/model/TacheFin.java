package editeurgraphe.model;

class TacheFin extends Tache {
    public TacheFin(String nom, int id) {
        super(nom, id);
    }

    @Override
    public String toString() {
        return "TacheFin{" +
                "nom='" + nom + '\'' +
                ", id=" + id +
                '}';
    }
}