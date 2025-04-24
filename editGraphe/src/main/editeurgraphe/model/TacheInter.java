package editeurgraphe.model;


class TacheInter extends Tache {
    public TacheInter(String nom, int id) {
        super(nom, id);
    }

    @Override
    public String toString() {
        return "TacheInter{" +
                "nom='" + nom + '\'' +
                ", id=" + id +
                '}';
    }
}