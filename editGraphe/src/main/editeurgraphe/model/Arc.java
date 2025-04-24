package editeurgraphe.model;

class Arc {
    private Tache source;
    private Tache successeur;

    public Arc(Tache source, Tache successeur) {
        this.source = source;
        this.successeur = successeur;
    }

    public int  getpoids() {
        return source.getDuree();
    }

    public Tache getSource() {
        return source;
    }

    public Tache getsuccesseur() {
        return successeur;
    }

}