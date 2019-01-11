package model;

import javafx.scene.paint.Color;
import model.cartes.*;
import model.constructions.Construction;
import model.constructions.Convecteur;
import model.constructions.Delorean;
import model.constructions.Monument;

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
    private int routesGratuites;
    private boolean armeLaPlusPuissante;

    public Joueur(int n) {
        numero = n;
        ressources = new int[8];
        //for (int i = 0; i<8; i++) ressources[i]= 200; // pour test
        constructions = new ArrayList<>();
        routes = new ArrayList<>();
        cartes = new ArrayList<>();
        nom = "joueur " + n;
        switch (n) {
            case 1 : couleur = Color.BLUE; break;
            case 2 : couleur = Color.GREEN; break;
            case 3 : couleur = Color.RED; break;
            case 4 : couleur = Color.ORANGE; break;
        }
    }

    /**
     * payer 1 bois, 1 pierre et 1 or pour acheter 1 carte développement
     * @param cd
     */
    public void acheterCarteDeveloppement(CarteDeveloppement cd){
        ressources[0]--;
        ressources[1]--;
        ressources[3]--;
        cartes.add(cd);
    }

    /**
     * ajoute un certain nombre de ressources du type donné
     * @param type
     * @param nb
     */
    public void ajouteRessource(int type, int nb) {
        ressources[type]+=nb;
    }

    /**
     * supprime un certain nombre de ressources du type donné
     * @param type
     * @param nb
     */
    public void supprimeRessource(int type, int nb) {
        ressources[type]-=nb;
    }

    /**
     * enlève les ressources proposées au joueur actuel et les rajoute au joueur en paramètre
     * enlève les ressources demandées au joueur en paramètre et les rajoute au joueur actuel
     * @param ressourcesProposees
     * @param ressourcesDemandees
     * @param joueur
     */
    public void echanger(int[] ressourcesProposees, int[] ressourcesDemandees, Joueur joueur) {
        for (int i =0; i<ressourcesProposees.length; i++){
            supprimeRessource(i, ressourcesProposees[i]);
            ajouteRessource(i, ressourcesDemandees[i]);
            if (joueur != null) {
                joueur.ajouteRessource(i, ressourcesProposees[i]);
                joueur.supprimeRessource(i, ressourcesDemandees[i]);
            }
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

    public boolean isArmeLaPlusPuissante() {
        return armeLaPlusPuissante;
    }

    /**
     * si true -> ajoute 2 point de victoire
     * si false les enlève
     * @param armeLaPlusPuissante
     */
    public void setArmeLaPlusPuissante(boolean armeLaPlusPuissante) {
        if (armeLaPlusPuissante) score +=2;
        else score-=2;
        this.armeLaPlusPuissante = armeLaPlusPuissante;
    }

    /**
     * payer 2 de métals et 1 d'or pour construire une delorean
     * ajoute un point de victoire
     * @param croisement
     * @param b
     */
    public void construireDelorean(Croisement croisement, boolean b) {
        if (!b) {
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

    /**
     * payer 1 de bois et 1 de pierre si il n'y a pas de route gratuite pour construire une route
     * @param route
     */
    public void construireRoute(Route route) {
        if (routes.size() > 1) {
            if(routesGratuites == 0) {
                ressources[0]--;
                ressources[1]--;
            } else {
                routesGratuites--;
            }
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

    /**
     * Vérifie si les ressources sont suffisantes pour effectuer l'action
     * @param i
     * 0 : construction route
     * 1 : construction delorean
     * 2 : achat carte
     * @return
     */
    public boolean ressourcesSuffisante(int i) {
        switch (i) {
            case 0: return (ressources[0] > 0 && ressources[1] > 0) || routesGratuites > 0;
            case 1: return ressources[2] > 1 && ressources[3] > 0;
            case 2: return ressources[0] > 0 && ressources[1] > 0 && ressources[3] > 0;
        }
        return false;
    }

    /**
     * renvoie le nombre total de ressource du joueur
     * @return
     */
    public int getTotalRessources() {
        int totalRessources = 0;
        for (int ressource : ressources) {
            totalRessources += ressource;
        }
        return totalRessources;
    }

    /**
     * taux d'échange en fonction du nombre de monuments : 4 - 1 par monuments
     * @return
     */
    public int tauxEchange() {
        int taux = 4;
        for (Construction c : constructions) {
            if (c instanceof Monument) taux--;
        }
        return taux > 1 ? taux : 2;
    }

    /**
     * paye 2 métals, 1 or et 3 ressources liées a l'époque pour construire un convecteur
     * @param c1 croisement de l'époque de construction du convecteur
     * @param c2 croisement de l'époque d'arriver du convecteur
     */
    public void construireConvecteur(Croisement c1, Croisement c2) {
        ressources[2] -= 2;
        ressources[3]--;
        ressources[c1.getDate() + 4] -= 3;
        ((Delorean) c1.getConstruction()).setConvecteur(new Convecteur(c2));
        score++;

    }

    /**
     * paye 2 bois, 2 pierres et 4 ressources liées a l'époque pour construire un monument
     * @param croisement
     * @return
     */
    public Monument constructionMonument(Croisement croisement) {
        score += 3;
        ressources[0] -= 2;
        ressources[1] -= 2;
        ressources[croisement.getDate() + 4] -= 4;
        Monument m = new Monument(this, croisement);
        constructions.add(m);
        croisement.setConstruction(m);
        return m;
    }

    public int getRoutesGratuites() {
        return routesGratuites;
    }

    public void setRoutesGratuites(int routesGratuites) {
        this.routesGratuites = routesGratuites;
    }

    /**
     * Vérifie si le joueur possède les ressources demandées pour l'échange
     * @param ressourcesDemandees
     * @return
     */
    public boolean possedeRessources(int[] ressourcesDemandees) {
        for (int i = 0; i < ressourcesDemandees.length; i++) {
            if (ressourcesDemandees[i] > ressources[i]) return false;
        }
        return true;
    }

    /**
     * Compte le nombre de cartes développement de chaque type
     * @return
     */
    public int[] decompteCartes() {
        int[] decompte = new int[5];
        for (CarteDeveloppement carte : cartes) {
            if (carte instanceof CartePV) decompte[0]++;
            if (carte instanceof CarteChevalier) decompte[1]++;
            if (carte instanceof CarteMonopole) decompte[2]++;
            if (carte instanceof CarteRoute) decompte[3]++;
            if (carte instanceof CarteDecouverte) decompte[4]++;
        }
        return decompte;
    }

}
