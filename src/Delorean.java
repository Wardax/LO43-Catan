public class Delorean extends Construction {
    private Convertisseur convertisseur;

    public Delorean(Joueur j, Croisement c) {
        super(j, c);
    }

    public int getNbPoint() {
        if (convertisseur == null) return 1;
        return 2;
    }

    public void setConvertisseur(Convertisseur convertisseur) {
        this.convertisseur = convertisseur;
    }

    public void recupereRessource(int type){
        if (convertisseur == null) getJoueur().ajouteRessource(type, 1);
        getJoueur().ajouteRessource(type, 2);
    }

    public Convertisseur getConvertisseur() {
        return convertisseur;
    }
}
