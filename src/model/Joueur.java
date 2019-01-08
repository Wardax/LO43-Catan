package model;

import javafx.scene.paint.Color;
import model.cartes.CarteDeveloppement;
import model.constructions.Construction;
import model.constructions.Delorean;

import java.util.ArrayList;

public class Joueur {
    private int numero;
    public String nom;
    private Color couleur;
    /*
    0: bois
    1: pierre
    2: metal
    3: or
    4: charbon
    5: pétrole
    6: uranium
    7: terres rares
     */
    private int[] ressources;
    private ArrayList<Construction> constructions;
    private ArrayList<Route> routes;
    private ArrayList<CarteDeveloppement> cartes;
    private int score;
    private int nbChevalier;
    private boolean routeLaPlusLongue;
    private boolean armeLaPlusPuissante;

    public Joueur(int n) {
        numero = n;
        ressources = new int[8]; // nombre de ressource a changer
        constructions = new ArrayList<>();
        routes = new ArrayList<>();
        cartes = new ArrayList<>();
        switch (n) {
            case 1 : couleur = Color.BLUE; break;
            case 2 : couleur = Color.GREEN; break;
            case 3 : couleur = Color.RED; break;
            case 4 : couleur = Color.ORANGE; break;
        }
    }

    public void acheterCarteDeveloppement(CarteDeveloppement cd){}

    public void construire(Construction c){}

    public void ajouteRessource(int type, int nb) {
        ressources[type]+=nb;
    }

    public void supprimeRessource(int type, int nb) {
        ressources[type]-=nb;
    }

   // public int[][] proposerEchange(){}

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

    public void construireDelorean(Croisement croisement) {
        if (constructions.size() > 1) {
            ressources[2]-=2;
            ressources[3]--;
        }
        Delorean d = new Delorean(this, croisement);
        croisement.setConstruction(d);
        constructions.add(d);
        score++;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void construireRoute(Route route) {
        if (routes.size() > 1) {
            ressources[0]--;
            ressources[1]--;
        }
        routes.add(route);
        route.ammenagementRoute(numero);
    }

    public Color getCouleur() {
        return couleur;
    }

    public String getNom() {
        return nom;
    }

    public boolean ressourcesSuffisante(int i) {
        switch (i) {
            case 0: return ressources[0] > 0 && ressources[1] > 0;
            case 1: return ressources[2] > 1 && ressources[3] > 0;
            case 4: return ressources[0] > 0 && ressources[1] > 0 && ressources[3] > 0;
        }
        return false;
    }

}
