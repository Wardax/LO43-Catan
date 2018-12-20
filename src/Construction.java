public abstract class Construction {
    private Joueur joueur;

    public Construction(Joueur j) {
        joueur = j;
    }

    public abstract int getNbPoint();

    public abstract void recupereRessource(int type);

    public Joueur getJoueur() {
        return joueur;
    }
}
