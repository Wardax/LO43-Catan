import java.util.ArrayList;

public class Joueur {
    private int numero;
    private int[] ressources;
    private ArrayList<Construction> constructions;
    private ArrayList<CarteDeveloppement> cartes;
    private int score;
    private int nbChevalier;
    private boolean routeLaPlusLongue;
    private boolean armeLaPlusPuissante;

    public Joueur(int n) {
        numero = n;
        ressources = new int[5]; // nombre de ressource a changer
        constructions = new ArrayList<>();
        cartes = new ArrayList<>();
    }

    public void acheterCarteDeveloppement(CarteDeveloppement cd){}

    public void construire(Construction c){}

    public void ajouteRessource(int type, int nb) {
        ressources[type]+=nb;
    }

    public void supprimeRessource(int type, int nb) {
        ressources[type]-=nb;
    }

    public int[][] proposerEchange(){}

    public void echanger(int[] ressourcesProposees, int[] ressourcesDemandees, Joueur j) {
        for (int i =0; i<ressourcesProposees.length; i++){
            supprimeRessource(i, ressourcesProposees[i]);
            j.ajouteRessource(i, ressourcesProposees[i]);
            ajouteRessource(i, ressourcesDemandees[i]);
            j.supprimeRessource(i, ressourcesDemandees[i]);
        }
    }

    public int getNumero() {
        return numero;
    }

    public int[] getRessources() {
        return ressources;
    }

    public ArrayList<Construction> getConstructions() {
        return constructions;
    }

    public ArrayList<CarteDeveloppement> getCartes() {
        return cartes;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNbChevalier() {
        return nbChevalier;
    }

    public void setNbChevalier(int nbChevalier) {
        this.nbChevalier = nbChevalier;
    }

    public boolean isRouteLaPlusLongue() {
        return routeLaPlusLongue;
    }

    public void setRouteLaPlusLongue(boolean routeLaPlusLongue) {
        this.routeLaPlusLongue = routeLaPlusLongue;
    }

    public boolean isArmeLaPlusPuissante() {
        return armeLaPlusPuissante;
    }

    public void setArmeLaPlusPuissante(boolean armeLaPlusPuissante) {
        this.armeLaPlusPuissante = armeLaPlusPuissante;
    }
}
