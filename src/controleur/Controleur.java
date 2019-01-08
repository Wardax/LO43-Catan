package controleur;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.BackToCatan;
import model.Croisement;
import model.Joueur;
import model.Model;
import vue.Vue;
import vue.VueCroisement;
import vue.VuePlateau;
import vue.VueRoute;

import java.util.ArrayList;

public class Controleur {
    private Model model;
    private Vue vue;
    private boolean start;

    public Controleur(Model model, Vue vue) {
        this.model = model;
        this.vue = vue;
        start = true;
        start();
    }

    private void start() {
        activationBoutonFinDeTour();
        constructionDeloreanBase();
        vue.disableButton();
    }

    private void activationBoutonFinDeTour() {
        vue.getbFinTour().setOnAction(actionEvent -> {
            model.joueurSuivant();
            vue.actualisationFinDeTour();
            if (start && model.getJoueurActuel().getConstructions().size() >= 2) {
                start = false;
                activationBoutonConstruction();
                activationBoutonCarteDev();
                vue.enableButton();
            }
            if (start) {
                vue.getbFinTour().setDisable(true);
                constructionDeloreanBase();
            }
            else {
                vue.afficherDe(model.lancementDe());
                vue.actualiseVuesJoueurs();
                //lancer dé
            }
        });
    }

    private void activationBoutonConstruction() {
        vue.getbConstruction().setOnAction(actionEvent ->{
            Label labelConstruction = new Label("Sélectionner la construction que vous voulez réaliser !");

            Group groupConstructions = new Group();
            groupConstructions.getChildren().add(labelConstruction);


            Scene sceneConstruction = new Scene(groupConstructions, 400, 300);

            Stage fenetreConstruction = new Stage();
            fenetreConstruction.setTitle("Construction");
            fenetreConstruction.setScene(sceneConstruction);

            // Set position of second window, related to primary window.
            fenetreConstruction.setX(BackToCatan.getPrimaryStage().getX() + 200);
            fenetreConstruction.setY(BackToCatan.getPrimaryStage().getY() + 100);


            Joueur joueur = model.getJoueurActuel();

            Button bRoute = new Button("Route");
            bRoute.relocate(150, 50);
            if(joueur.ressourcesSuffisante(0) && model.routesConstructibles().size() > 0) {
                bRoute.setOnAction(actionEvent2 -> {
                    fenetreConstruction.hide();
                    constructionRoute();
                });
            }
            groupConstructions.getChildren().add(bRoute);

            Button bDelorean = new Button("Delorean");
            bDelorean.relocate(150, 100);
            if(joueur.ressourcesSuffisante(1) && model.deloreansConstructibles().size() > 0) {
                bDelorean.setOnAction(actionEvent2 -> {
                    fenetreConstruction.hide();
                    constructionDelorean();
                });
            }
            groupConstructions.getChildren().add(bDelorean);

            Button bMonument = new Button("Monument");
            bMonument.relocate(150, 150);
            if(joueur.ressourcesSuffisante(2)) {
                bMonument.setOnAction(actionEvent2 -> {
                    fenetreConstruction.hide();
                    constructionMonument();
                });
            }
            groupConstructions.getChildren().add(bMonument);


            Button bConvecteur = new Button("Convecteur");
            bConvecteur.relocate(150, 200);
            if(joueur.ressourcesSuffisante(3) && model.convecteursConstrucibles().size() > 0) {
                bConvecteur.setOnAction(actionEvent2 -> {
                    System.out.println("a");
                    fenetreConstruction.hide();
                    constructionConvecteur();
                });
            }
            groupConstructions.getChildren().add(bConvecteur);

            fenetreConstruction.show();
        });
    }

    private void activationBoutonCarteDev() {
        vue.getbCarteDev().setOnAction(actionEvent ->{
            Label labelCD = new Label("Cartes Développements !");

            Group groupCD = new Group();
            groupCD.getChildren().add(labelCD);


            Scene sceneCD = new Scene(groupCD, 400, 300);

            Stage fenetreCD = new Stage();
            fenetreCD.setTitle("Construction");
            fenetreCD.setScene(sceneCD);

            // Set position of second window, related to primary window.
            fenetreCD.setX(BackToCatan.getPrimaryStage().getX() + 200);
            fenetreCD.setY(BackToCatan.getPrimaryStage().getY() + 100);


            Joueur joueur = model.getJoueurActuel();

            Button bAchat = new Button("Achat CD");
            bAchat.relocate(150, 50);
            if(joueur.ressourcesSuffisante(4)) {
                bAchat.setOnAction(actionEvent2 -> {
                    fenetreCD.hide();
                    achatCarteDev();
                });
            }
            groupCD.getChildren().add(bAchat);

            fenetreCD.show();
        });
    }

    private void constructionDeloreanBase() {
        VuePlateau vp = vue.getVuesPlateaux()[0];
        ArrayList<VueCroisement> vcs = vp.getCroisements();
        for(VueCroisement vc : vcs) {
            if (vc.getCroisement().isConstructible()) {
                vc.setFill(Color.BLACK);
                vc.setOnMouseClicked(MouseEvent -> {
                    model.getJoueurActuel().construireDelorean(vc.getCroisement());
                    vc.setFill(model.getJoueurActuel().getCouleur());
                    for (VueCroisement vc2 : vcs) {
                        if (!vc2.getCroisement().hasConstruction()) {
                            vc2.setFill(null);
                        }
                        vc2.setOnMouseClicked(null);
                    }
                    constructionRoute();
                });
            }
        }
    }

    private void constructionRoute() {
        VuePlateau vp = vue.getVuesPlateaux()[0];
        ArrayList routes = model.routesConstructibles();
        ArrayList<VueRoute> vrs = vp.getRoutes();
        for(VueRoute vr : vrs) {
            if (routes.contains(vr.getRoute())) {
                vr.setFill(Color.BLACK);
                vr.setOnMouseClicked(MouseEvent -> {
                    model.getJoueurActuel().construireRoute(vr.getRoute());
                    vr.setFill(model.getJoueurActuel().getCouleur());
                    for (VueRoute vr2 : vrs) {
                        if (vr2.getRoute().getJoueur() == 0) {
                            vr2.setFill(null);
                        }
                        vr2.setOnMouseClicked(null);
                    }
                    if (start) vue.getbFinTour().setDisable(false);
                });
            }
        }
    }

    private void constructionDelorean() {
        VuePlateau vp = vue.getVuesPlateaux()[0];
        ArrayList<VueCroisement> vcs = vp.getCroisements();
        ArrayList<Croisement> croisements = model.deloreansConstructibles();
        for(VueCroisement vc : vcs) {
            if (croisements.contains(vc.getCroisement())) {
                vc.setFill(Color.BLACK);
                vc.setOnMouseClicked(MouseEvent -> {
                    model.getJoueurActuel().construireDelorean(vc.getCroisement());
                    vc.setFill(model.getJoueurActuel().getCouleur());
                    for (VueCroisement vc2 : vcs) {
                        if (!vc2.getCroisement().hasConstruction()) {
                            vc2.setFill(null);
                        }
                        vc2.setOnMouseClicked(null);
                    }
                });
            }
        }

    }

    private void constructionMonument() {

    }

    private void constructionConvecteur(){

    }

    private void echange(){

    }

    private void activationCarteDev(){

    }

    private void achatCarteDev(){

    }

}
