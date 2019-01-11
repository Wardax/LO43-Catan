package model;

import model.cartes.*;
import model.constructions.Construction;
import model.constructions.Delorean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Model {
    private Plateau[] plateaux;
    private Joueur[] joueurs;
    private int nbJoueurs;
    private ArrayList<CarteDeveloppement> cartes;
    private Joueur joueurActuel;
    final static Random rand = new Random();

    public Model(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
        plateaux = new Plateau[4];
        for (int i =0; i<4; i++) {
            plateaux[i] = new Plateau(i);
        }

        joueurs = new Joueur[nbJoueurs];
        for (int i = 0; i<nbJoueurs; i++) {
            joueurs[i] = new Joueur(i+1);
        }

        joueurActuel = joueurs[0];

        cartes = new ArrayList<>();
        for (int i = 0; i < 5; i++) cartes.add(new CartePV());
        for (int i = 0; i < 2; i++) cartes.add(new CarteMonopole());
        for (int i = 0; i < 2; i++) cartes.add(new CarteRoute());
        for (int i = 0; i < 2; i++) cartes.add(new CarteDecouverte());
        for (int i = 0; i < 14; i++) cartes.add(new CarteChevalier());
        Collections.shuffle(cartes);
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

    /**
     * cherche les routes constructibles par le joueur actuel
     * @return
     */
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

    /**
     * cherche les deloreans constructibles par le joueur actuel
     * @return
     */
    public ArrayList<Croisement> deloreansConstructibles() {
        ArrayList<Croisement> deloreansConstructibles = new ArrayList<>();

        for (Route r : joueurActuel.getRoutes()) {
            for (Croisement c : r.getCroisements()) {
                if (c.isConstructible()) deloreansConstructibles.add(c);
            }
        }
        return deloreansConstructibles;
    }

    /**
     * cherche les convecteurs constructibles par le joueur actuel
     * @return
     */
    public ArrayList<Construction> convecteursConstrucibles() {
        int[] res = joueurActuel.getRessources();
        ArrayList<Construction> convecteursConstructibles = new ArrayList<>();
        if (res[2] > 1 && res[3] > 0) {
            for (Construction c : joueurActuel.getConstructions()) {
                if (c instanceof Delorean && ((Delorean) c).getConvecteur() == null && res[c.getCroisement().getDate()+4]>2) {
                    convecteursConstructibles.add(c);
                }
            }
        }
        return convecteursConstructibles;
    }

    /**
     * cherche les monuments constructibles par le joueur actuel
     * @return
     */
    public ArrayList<Croisement> monumentsConstructibles() {
        ArrayList<Croisement> monumentsConstructibles = new ArrayList<>();
        int[] res = joueurActuel.getRessources();
        if (res[0] > 1 && res[1] > 1) {
            for (Route r : joueurActuel.getRoutes()) {
                for (Croisement c : r.getCroisements()) {
                    if (c.isConstructible() && plateaux[c.getDate()].getMonument() == null && res[c.getDate()+4] > 3) monumentsConstructibles.add(c);
                }
            }
        }
        return monumentsConstructibles;
    }

    public void joueurSuivant() {
        // comme le numéro commence a 1 alors que le tableau commence à 0 on augmente pas de 1
        joueurActuel = joueurs[joueurActuel.getNumero()%nbJoueurs];
    }

    /**
     * chosit aléatoirement une époque parmit celles atteintes
     * lance 2 dé et produit des ressources sur la parcelle ayant comme numéro la somme des 2 dées
     * @return l'index de l'époque, le premier dé et le second dé
     */
    public int[] lancementDe(){
        int nbEpoqueAtteinte = 0;
        for (Plateau p : plateaux) {
            if (p.isAtteint()) nbEpoqueAtteinte++;
        }
        int[] des = new int[3];
        des[0] = rand.nextInt(nbEpoqueAtteinte) + 1;
        des[1] = rand.nextInt(6) + 1;
        des[2] = rand.nextInt(6) + 1;
        ArrayList<Parcelle> parcelles = plateaux[des[0]-1].getParcelles();
        int n = des[1] + des[2];
        for (Parcelle p : parcelles) {
            if(p.getNumero() == n)  p.produitRessource();
        }
        return des;

    }

    /**
     * enlève aléatoirement la moitié des ressources des joueurs qui ont plus de 7 ressources
     */
    public void limiteRessources() {
        for (Joueur joueur : joueurs) {
            if (joueur.getTotalRessources() > 7) {
                int i = joueur.getTotalRessources() / 2;
                while (i > 0) {
                    int n = rand.nextInt(8);
                    if (joueur.getRessources()[n] > 0) {
                        joueur.supprimeRessource(n, 1);
                        i--;
                    }
                }
            }
        }
    }
}
