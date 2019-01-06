import java.util.ArrayList;
import java.util.Random;

public class Model {
    private Plateau[] plateaux;
    private Joueur[] joueurs;
    private int nbJoueurs;
    private ArrayList<CarteDeveloppement> cartes;
    private Joueur joueurActuel;
    private static Random rand;

    public Model(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
        plateaux = new Plateau[4];
        plateaux[0] = new Plateau(1855);
        plateaux[1] = new Plateau(1955);
        plateaux[2] = new Plateau(1985);
        plateaux[3] = new Plateau(2015);

        joueurs = new Joueur[nbJoueurs];
        joueurs[0] = new Joueur(1);
        joueurs[1] = new Joueur(2);
        joueurs[2] = new Joueur(3);
        joueurs[3] = new Joueur(4);

        joueurActuel = joueurs[0];
    }

    public ArrayList<CarteDeveloppement> getCartes() {
        return cartes;
    }

    public Plateau[] getPlateaux() {
        return plateaux;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }

    public Joueur getJoueurActuel() {
        return joueurActuel;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public ArrayList<Route> routesConstructibles() {
        ArrayList<Route> routesConstructibles = new ArrayList<>();
        for (Route r : joueurActuel.getRoutes()) {
            for (Croisement c : r.getCroisements()) {
                for (Route routeAdj : c.getRoutes()) {
                    if (routeAdj.getJoueur() == 0) routesConstructibles.add(routeAdj);
                }
            }
        }

        for (Construction c : joueurActuel.getConstructions()) {
            Croisement croisement = c.getCroisement();
            for (Route routeAdj : croisement.getRoutes()) {
                if (routeAdj.getJoueur() == 0) routesConstructibles.add(routeAdj);
            }
        }
        return routesConstructibles;
    }

    public ArrayList<Croisement> deloreansConstructibles() {
        ArrayList<Croisement> deloreansConstructibles = new ArrayList<>();

        for (Route r : joueurActuel.getRoutes()) {
            for (Croisement c : r.getCroisements()) {
                if (c.isConstructible()) deloreansConstructibles.add(c);
            }
        }
        return deloreansConstructibles;
    }

    public ArrayList<Delorean> convecteursConstrucibles() {
        ArrayList<Delorean> convecteursConstructibles = new ArrayList<>();
        for (Construction c : joueurActuel.getConstructions()) {
            if (c instanceof Delorean && ((Delorean)c).getConvertisseur() == null) {
                convecteursConstructibles.add((Delorean)c);
            }
        }
        return convecteursConstructibles;
    }

    public void joueurSuivant() {
        // comme le numéro commence a 1 alors que le tableau commence à 0 on augmente pas de 1
        joueurActuel = joueurs[joueurActuel.getNumero()%nbJoueurs];
    }

    public int[] lancementDe(){
        int[] de = new int[3];
        de[0] = rand.nextInt(4) + 1;
        de[1] = rand.nextInt(6) + 1;
        de[2] = rand.nextInt(6) + 1;
        Parcelle[] parcelles = plateaux[de[0]-1].getParcelles();
        int n = de[1] + de[2];
        for (Parcelle p : parcelles) {
            if(p.getNumero() == n)  p.produitRessource();
        }
        return de;

    }


}
