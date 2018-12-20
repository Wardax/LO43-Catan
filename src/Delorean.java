public class Delorean extends Construction {
    Convertisseur convertisseur;

    public Delorean(Joueur j){
        super(j);
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
}
