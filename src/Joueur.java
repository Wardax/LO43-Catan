import java.util.ArrayList;

public class Joueur {
    private int numero;
    private int[] ressources;
    private ArrayList<Construction> constructions;
    private ArrayList<CarteDeveloppement> cartes;

    public Joueur(int n) {
        numero = n;
        ressources = new int[5]; // nombre de ressource a changer
        constructions = new ArrayList<>();
        cartes = new ArrayList<>();
    }

    public void ajouteRessource(int type, int nb) {
        ressources[type]+=nb;
    }

    public void supprimeRessource(int type, int nb) {
        ressources[type]-=nb;
    }

    public void echanger(int[] ressourcesProposees, int[] ressourcesDemandees, Joueur j) {
        for (int i =0; i<ressourcesProposees.length; i++){
            supprimeRessource(i, ressourcesProposees[i]);
            j.ajouteRessource(i, ressourcesProposees[i]);
            ajouteRessource(i, ressourcesDemandees[i]);
            j.supprimeRessource(i, ressourcesDemandees[i]);
        }
    }
}
