package controleur;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.*;
import model.cartes.*;
import model.constructions.Construction;
import model.constructions.Delorean;
import model.constructions.Monument;
import vue.*;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

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

    /**
     * lance le déroulement de la partie
     */
    private void start() {
        activationBoutonFinDeTour();
        activationBoutonEchanger();
        activationBoutonConstruction();
        activationBoutonCarteDev();
        constructionDeloreanBase();
        vue.disableButton();
    }

    /**
     * vérifie si un joueur à atteint 12 pts de victoire
     */
    private void verifFinJeu() {
        if (model.getJoueurActuel().getScore() >= 12) {
            vue.fenetreFin();
        }
    }

    /**
     * lorsqu'un joueur presse le bouton de fin de tour:
     * passe au joueur suivant
     * si on est au début de la partie le joueur construit une delorean
     * si tout les joueurs ont construit 2 delorean -> les boutons d'actions sont débloqués
     * les dées sont lancés --> génération de ressours ou déplacement du brigand
     */
    private void activationBoutonFinDeTour() {
        vue.getbFinTour().setOnAction(actionEvent -> {
            model.joueurSuivant();
            vue.actualisationFinDeTour();
            if (start && model.getJoueurActuel().getConstructions().size() >= 2) {
                start = false;
                vue.enableButton();
            }
            if (start) {
                vue.getbFinTour().setDisable(true);
                constructionDeloreanBase();
            }
            else {
                int [] des = model.lancementDe();
                vue.afficherDe(des);
                vue.actualiseVuesJoueurs();
                if (des[1] + des[2] == 7) {
                    model.limiteRessources();
                    vue.actualiseVuesJoueurs();
                    vue.getTabPane().getSelectionModel().select(des[0]-1);
                    deplacementBrigand(des[0]-1);
                    vue.disableButton();
                }
            }
        });
    }

    /**
     * lorsque le joueur appuie sur le bouton construction une fenêtre proposant les différentes construction s'ouvre
     */
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
            fenetreConstruction.setX(BackToKatane.getPrimaryStage().getX() + 200);
            fenetreConstruction.setY(BackToKatane.getPrimaryStage().getY() + 100);


            Joueur joueur = model.getJoueurActuel();

            Button bRoute = new Button("Route");
            bRoute.relocate(150, 50);
            if(joueur.ressourcesSuffisante(0) && model.routesConstructibles().size() > 0) {
                bRoute.setOnAction(actionEvent2 -> {
                    fenetreConstruction.hide();
                    constructionRoute();
                });
            } else {
                bRoute.setDisable(true);
            }
            groupConstructions.getChildren().add(bRoute);

            Button bDelorean = new Button("Delorean");
            bDelorean.relocate(150, 100);
            if(joueur.ressourcesSuffisante(1) && model.deloreansConstructibles().size() > 0) {
                bDelorean.setOnAction(actionEvent2 -> {
                    fenetreConstruction.hide();
                    constructionDelorean();
                });
            } else {
                bDelorean.setDisable(true);
            }
            groupConstructions.getChildren().add(bDelorean);

            Button bMonument = new Button("Monument");
            bMonument.relocate(150, 150);
            if(model.monumentsConstructibles().size()>0) {
                bMonument.setOnAction(actionEvent2 -> {
                    fenetreConstruction.hide();
                    constructionMonument();
                });
            } else {
                bMonument.setDisable(true);
            }
            groupConstructions.getChildren().add(bMonument);


            Button bConvecteur = new Button("Convecteur");
            bConvecteur.relocate(150, 200);
            if(model.convecteursConstrucibles().size() > 0) {
                bConvecteur.setOnAction(actionEvent2 -> {
                    fenetreConstruction.hide();
                    constructionConvecteur();
                });
            } else {
                bConvecteur.setDisable(true);
            }
            groupConstructions.getChildren().add(bConvecteur);

            fenetreConstruction.show();
        });
    }

    /**
     * lorsque le joueur appuie sur le bouton carte développement une fenêtre avec les cartes développement du joueur ainsi qu'un bouton d'achat s'ouvre
     */
    private void activationBoutonCarteDev() {
        vue.getbCarteDev().setOnAction(actionEvent ->{
            Label labelCD = new Label("Cartes Développements !");

            Group groupCD = new Group();
            groupCD.getChildren().add(labelCD);


            Scene sceneCD = new Scene(groupCD, 950, 400);

            Stage fenetreCD = new Stage();
            fenetreCD.setTitle("Cartes Développements");
            fenetreCD.setScene(sceneCD);

            // Set position of second window, related to primary window.
            fenetreCD.setX(BackToKatane.getPrimaryStage().getX() + 100);
            fenetreCD.setY(BackToKatane.getPrimaryStage().getY() + 100);


            Joueur joueur = model.getJoueurActuel();
            int[] nbCartes = joueur.decompteCartes();

            for (int i = 0; i < 5; i++) {
                ImageView imageView = new ImageView("images/carte"+i+".jpg");
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(150);
                imageView.relocate(50 + 170*i, 50);
                if (nbCartes[i] > 0) {
                    int finalI = i;
                    imageView.setOnMouseClicked(MouseEvent -> {
                        activationCarteDev(finalI);
                        fenetreCD.hide();
                    });
                }
                Label l = new Label("" + nbCartes[i]);
                l.relocate(133 + 170*i, 300);
                groupCD.getChildren().addAll(imageView, l);
            }

            Button bAchat = new Button("Achat Carte Developpement");
            bAchat.relocate(150, 350);
            if(joueur.ressourcesSuffisante(2)) {
                bAchat.setOnAction(actionEvent2 -> {
                    fenetreCD.hide();
                    achatCarteDev();
                });
            } else {
                bAchat.setDisable(true);
            }
            groupCD.getChildren().add(bAchat);

            fenetreCD.show();
        });
    }

    /**
     * lorsque le joueur appuie sur le bouton d'échange, une fenêtre proposant d'échanger avec les joueurs ou avec la banque s'ouvre
     */
    private void activationBoutonEchanger() {
        vue.getbEchange().setOnAction(actionEvent ->{
            Label labelEchange = new Label("Echanger !");

            Group groupEchange = new Group();
            groupEchange.getChildren().add(labelEchange);


            Scene sceneEchange = new Scene(groupEchange, 400, 300);

            Stage fenetreEchange = new Stage();
            fenetreEchange.setTitle("Construction");
            fenetreEchange.setScene(sceneEchange);



            // Set position of second window, related to primary window.
            fenetreEchange.setX(BackToKatane.getPrimaryStage().getX() + 200);
            fenetreEchange.setY(BackToKatane.getPrimaryStage().getY() + 100);


            Button bEchangeBanque = new Button("Echange Banque");
            Button bEchangeJoueur = new Button("Echange Joueur");
            bEchangeJoueur.relocate(150, 100);
            bEchangeJoueur.setOnAction(actionEvent2 -> {
                groupEchange.getChildren().remove(bEchangeBanque);
                groupEchange.getChildren().remove(bEchangeJoueur);
                echange(groupEchange, fenetreEchange, false);
            });
            groupEchange.getChildren().add(bEchangeJoueur);

            bEchangeBanque.relocate(150, 50);
            bEchangeBanque.setOnAction(actionEvent2 -> {
                groupEchange.getChildren().remove(bEchangeBanque);
                groupEchange.getChildren().remove(bEchangeJoueur);
                echange(groupEchange, fenetreEchange, true);
            });
            groupEchange.getChildren().add(bEchangeBanque);


            fenetreEchange.show();
        });

    }

    /**
     * propose un échange aux autres joueur ou à la banque
     * @param groupEchange
     * @param fenetreEchange
     * @param banque si vrai échange avec la banque sinon avec les joueurs
     */
    private void echange(Group groupEchange, Stage fenetreEchange, boolean banque) {
        int[] ressourcesDemandees = new int[8];
        int[] ressourcesProposees = new int[8];

        Label lPropose = new Label("Proposé : ");
        lPropose.relocate(10, 70);
        Label lRecut = new Label("Reçut : ");
        lRecut.relocate(10, 120);
        int taux = model.getJoueurActuel().tauxEchange();
        Label lTaux = new Label("taux : 1/" + taux);
        lTaux.relocate(200, 172);

        for (int i = 0; i < 8; i++) {
            int n = i;
            ImageView imageView = new ImageView("images/material" + i + ".png");
            imageView.relocate(65 + i*30, 40);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(20);
            ComboBox<Integer> comboBox = new ComboBox<>();
            comboBox.relocate(60 + i*30, 70);
            comboBox.setMaxWidth(30);

            TextField tf = new TextField("0");

            final UnaryOperator<TextFormatter.Change> integerOnlyFilter = change -> {
                final String text = change.getText();
                return (text.isEmpty() || text .matches("[0-9]")) ? change : null;
            };
            final TextFormatter integerOnlyFormatter = new TextFormatter(integerOnlyFilter);
            tf.setTextFormatter(integerOnlyFormatter);

            tf.selectionProperty().addListener(observable -> {
                if (tf.getText().isEmpty()) tf.setText("0");
                ressourcesDemandees[n] = Integer.parseInt(tf.getText());
            });
            tf.setPrefWidth(30);
            tf.relocate(60 + i*30, 120);

            for (int j = 0; j <= model.getJoueurActuel().getRessources()[i]; j++) {
                comboBox.getItems().add(j);
            }
            comboBox.setValue(0);
            comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                ressourcesProposees[n] = newValue;
            });
            groupEchange.getChildren().addAll(comboBox, tf, imageView);
        }

        Label lErreur = new Label("");
        lErreur.relocate(20, 220);

        Button boutonEchanger = new Button("Echanger");
        boutonEchanger.setOnAction(actionEvent -> {
            if (banque) {
                int nbressourcesNecessaires = 0;
                int nbressourcesProposees = 0;
                for (int i = 0; i < 8; i++) {
                    nbressourcesNecessaires += ressourcesDemandees[i] * taux;
                    nbressourcesProposees += ressourcesProposees[i];
                }
                if (nbressourcesNecessaires == nbressourcesProposees) {
                    model.getJoueurActuel().echanger(ressourcesProposees, ressourcesDemandees, null);
                    fenetreEchange.hide();
                    vue.actualiseVuesJoueurs();
                } else {
                    lErreur.setText("Echange invalide ! \n" +
                            "nombre de ressources nécessaires : " + nbressourcesNecessaires + "\n" +
                            "nombre de ressources proposées : " + nbressourcesProposees);
                }
            } else {
                lRecut.setText("Proposé");
                lPropose.setText("Reçut");
                groupEchange.getChildren().remove(boutonEchanger);
                reponseEchange(fenetreEchange, groupEchange, ressourcesProposees, ressourcesDemandees, model.getJoueurActuel().getNumero()%4);
            }
        });
        boutonEchanger.relocate(100, 170);


        groupEchange.getChildren().addAll(boutonEchanger, lRecut, lPropose);
        if (banque) groupEchange.getChildren().addAll(lErreur, lTaux);

    }

    /**
     * Permet aux joueurs de répondre aux échanges
     * @param fenetreEchange
     * @param groupEchange
     * @param ressourcesProposees
     * @param ressourcesDemandees
     * @param n
     */
    private void reponseEchange(Stage fenetreEchange, Group groupEchange, int[] ressourcesProposees, int[] ressourcesDemandees, int n){
        Joueur j = model.getJoueurs()[n];
        Label l = new Label("Réponse " + j.getNom());
        l.relocate(50, 20);
        Button boutonAccepter = new Button("Accepter");
        boutonAccepter.relocate(50, 170);
        if (j.possedeRessources(ressourcesDemandees)){
            boutonAccepter.setOnAction(actionEvent2 -> {
                model.getJoueurActuel().echanger(ressourcesProposees, ressourcesDemandees, j);
                vue.actualiseVuesJoueurs();
                fenetreEchange.hide();
            });

        } else {
            boutonAccepter.setDisable(true);
        }
        Button boutonRefuser = new Button("Refuser");
        boutonRefuser.relocate(150, 170);
        boutonRefuser.setOnAction(actionEvent2 -> {
            if ((n+2)%4 == model.getJoueurActuel().getNumero()) fenetreEchange.hide();
            else {
                reponseEchange(fenetreEchange, groupEchange, ressourcesProposees, ressourcesDemandees, (n+1)%4);
                groupEchange.getChildren().removeAll(boutonAccepter, boutonRefuser, l);
            }
        });
        groupEchange.getChildren().addAll(boutonAccepter, boutonRefuser, l);

    }

    /**
     * récupère la liste des croisements où une deloréan est constructible et permet au joueur de cliquer sur le croisement correspondant pour construit une delorean
     */
    private void constructionDeloreanBase() {
        vue.getActionEnCours().setText("Construction delorean en cours");
        VuePlateau vp = vue.getVuesPlateaux()[0];
        ArrayList<VueCroisement> vcs = vp.getCroisements();
        for(VueCroisement vc : vcs) {
            if (vc.getCroisement().isConstructible()) {
                vc.setFill(Color.BLACK);
                vc.setOnMouseClicked(MouseEvent -> {
                    model.getJoueurActuel().construireDelorean(vc.getCroisement(), true);
                    vc.setFill(model.getJoueurActuel().getCouleur());
                    vp.addConstruction(vc.getCroisement());
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
        vue.getActionEnCours().setText("Construction route en cours");
        VuePlateau[] vps = vue.getVuesPlateaux();
        ArrayList<VueRoute> vrs = new ArrayList<>();
        for (VuePlateau vp : vps) vrs.addAll(vp.getRoutes());
        ArrayList routes = model.routesConstructibles();
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
                    vue.actualiseVuesJoueurs();
                });
            }
        }
    }

    private void constructionDelorean() {
        vue.getActionEnCours().setText("Construction delorean en cours");
        VuePlateau[] vps = vue.getVuesPlateaux();
        ArrayList<VueCroisement> vcs = new ArrayList<>();
        for (VuePlateau vp : vps) vcs.addAll(vp.getCroisements());
        ArrayList<Croisement> croisements = model.deloreansConstructibles();
        for(VueCroisement vc : vcs) {
            if (croisements.contains(vc.getCroisement())) {
                vc.setFill(Color.BLACK);
                vc.setOnMouseClicked(MouseEvent -> {
                    model.getJoueurActuel().construireDelorean(vc.getCroisement(), false);
                    vc.setFill(model.getJoueurActuel().getCouleur());
                    vps[vc.getCroisement().getDate()].addConstruction(vc.getCroisement());
                    for (VueCroisement vc2 : vcs) {
                        if (!vc2.getCroisement().hasConstruction()) {
                            vc2.setFill(null);
                        }
                        vc2.setOnMouseClicked(null);
                    }
                    vue.actualiseVuesJoueurs();
                    verifFinJeu();
                });
            }
        }

    }

    private void constructionMonument() {
        vue.getActionEnCours().setText("Construction monument en cours");
        VuePlateau[] vps = vue.getVuesPlateaux();
        ArrayList<VueCroisement> vcs = new ArrayList<>();
        for (VuePlateau vp : vps) vcs.addAll(vp.getCroisements());
        ArrayList<Croisement> croisements = model.monumentsConstructibles();
        for(VueCroisement vc : vcs) {
            if (croisements.contains(vc.getCroisement())) {
                vc.setFill(Color.BLACK);
                vc.setOnMouseClicked(MouseEvent -> {
                    Monument m = model.getJoueurActuel().constructionMonument(vc.getCroisement());
                    model.getPlateaux()[vc.getCroisement().getDate()].setMonument(m);
                    vc.setFill(model.getJoueurActuel().getCouleur());
                    vc.setRadius(25);
                    vps[vc.getCroisement().getDate()].addConstruction(vc.getCroisement());
                    for (VueCroisement vc2 : vcs) {
                        if (!vc2.getCroisement().hasConstruction()) {
                            vc2.setFill(null);
                        }
                        vc2.setOnMouseClicked(null);
                    }
                    vue.actualiseVuesJoueurs();
                    verifFinJeu();
                });
            }
        }
    }

    private void constructionConvecteur(){
        vue.getActionEnCours().setText("Sélectionner la delorean à équiper");
        VuePlateau[] vps = vue.getVuesPlateaux();
        ArrayList<VueConstruction> vcs = new ArrayList<>();
        for (VuePlateau vp : vps) vcs.addAll(vp.getConstructions());
        ArrayList<Construction> deloreans = model.convecteursConstrucibles();
        for(VueConstruction vc : vcs) {
            if (deloreans.contains(vc.getConstruction())) {
                vc.setImage(new Image("images/delorean.png"));
                vc.setOnMouseClicked(MouseEvent -> {
                    vc.setImage(new Image("images/convecteur"+model.getJoueurActuel().getNumero()+".png"));
                    for (VueConstruction vc2 : vcs) {

                        if (((Delorean)vc2.getConstruction()).getConvecteur() == null && vc2 != vc) vc2.setImage(new Image("images/player"+vc2.getConstruction().getJoueur().getNumero()+".png"));
                        vc2.setOnMouseClicked(null);
                    }
                    destinationConvecteur(vc.getConstruction().getCroisement());
                });
            }
        }

    }

    private void destinationConvecteur(Croisement croisement) {
        vue.getActionEnCours().setText("Sélectionner le point d'arriver");
        int n =(croisement.getDate()+1)%4;
        model.getPlateaux()[n].setAtteint(true);
        vue.getTabPane().getSelectionModel().select(n);

        ArrayList<VueCroisement> vcs = vue.getVuesPlateaux()[n].getCroisements();
        for(VueCroisement vc : vcs) {
            if (vc.getCroisement().isConstructible()) {
                vc.setFill(Color.BLACK);
                vc.setOnMouseClicked(MouseEvent -> {
                    model.getJoueurActuel().construireConvecteur(croisement, vc.getCroisement());
                    model.getJoueurActuel().construireDelorean(vc.getCroisement(), true);
                    vc.setFill(model.getJoueurActuel().getCouleur());
                    vue.getVuesPlateaux()[n].addConstruction(vc.getCroisement());
                    for (VueCroisement vc2 : vcs) {
                        if (!vc2.getCroisement().hasConstruction()) {
                            vc2.setFill(null);
                        }
                        vc2.setOnMouseClicked(null);
                    }
                    vue.actualiseVuesJoueurs();
                    verifFinJeu();
                });
            }
        }

    }

    private void deplacementBrigand(int epoque) {
        vue.getActionEnCours().setText("Deplacement du brigand en cours");
        VuePlateau vp = vue.getVuesPlateaux()[epoque];
        for (VueParcelle p : vp.getParcelles()) {
            p.setOnMouseClicked(MouseEvent -> {
                ArrayList<Joueur> joueursVolable = model.getPlateaux()[epoque].getBrigand().deplacement(p.getParcelle());
                for (VueParcelle p2 : vp.getParcelles()) {
                    p2.setOnMouseClicked(null);
                }
                vp.actualisePositionBrigand(p);
                volJoueur(joueursVolable, model.getPlateaux()[epoque].getBrigand());
            });
        }
    }

    private void volJoueur(ArrayList<Joueur> joueursVolables, Brigand brigand) {
        joueursVolables.remove(model.getJoueurActuel());
        if (joueursVolables.size() == 1) {
            brigand.volRessource(model.getJoueurActuel(), joueursVolables.get(0));
            vue.actualiseVuesJoueurs();
            vue.enableButton();
        } else if (joueursVolables.size() > 1){
            Label l = new Label("Choisir le joueur à voler !");
            Group g = new Group();
            g.getChildren().add(l);
            Scene s = new Scene(g, 400, 300);
            Stage fenetre = new Stage();
            fenetre.setTitle("Choix");
            fenetre.setScene(s);
            fenetre.setX(BackToKatane.getPrimaryStage().getX() + 200);
            fenetre.setY(BackToKatane.getPrimaryStage().getY() + 100);
            for (int i = 0; i<joueursVolables.size(); i++){
                Joueur j = joueursVolables.get(i);
                Button b = new Button(j.getNom());
                b.relocate( 150, 50 + 50*i);
                b.setOnAction(actionEvent -> {
                    brigand.volRessource(model.getJoueurActuel(), j);
                    fenetre.hide();
                    vue.actualiseVuesJoueurs();
                    vue.enableButton();
                });
                g.getChildren().add(b);
            }
            fenetre.show();
        } else {
            vue.enableButton();
            vue.getActionEnCours().setText("Sélectionnez une action !");
        }
    }

    private void activationCarteDev(int typeCarte){
        Joueur joueur = model.getJoueurActuel();
        int i = 0;
        boolean ok = false;
        while (!ok) {
            CarteDeveloppement carte = joueur.getCartes().get(i);
            i++;
            switch (typeCarte) {
                case 0:
                    if (carte instanceof CartePV) {
                        ok = true;
                        joueur.getCartes().remove(carte);
                        carte.activation(model);
                        verifFinJeu();
                    }
                    break;
                case 1:
                    if (carte instanceof CarteChevalier) {
                        ok = true;
                        joueur.getCartes().remove(carte);
                        carte.activation(model);
                        verifFinJeu();
                    }
                    deplacementBrigand(vue.getTabPane().getSelectionModel().getSelectedIndex());
                    break;
                case 2:
                    if (carte instanceof CarteMonopole) {
                        ok = true;
                        joueur.getCartes().remove(carte);
                        choixRessource(carte);
                    }
                    break;
                case 3:
                    if (carte instanceof CarteRoute) {
                        ok = true;
                        joueur.getCartes().remove(carte);
                        carte.activation(model);
                    }
                    break;
                case 4:
                    if (carte instanceof CarteDecouverte) {
                        ok = true;
                        joueur.getCartes().remove(carte);
                        choixRessource(carte);
                    }
                    break;
            }
        }
        vue.actualiseVuesJoueurs();

    }

    private void choixRessource(CarteDeveloppement carte) {
        Label l = new Label("Choisir la ressource !");
        Group g = new Group();
        g.getChildren().add(l);
        Scene s = new Scene(g, 500, 150);
        Stage fenetre = new Stage();
        fenetre.setTitle("Choix");
        fenetre.setScene(s);
        fenetre.setX(BackToKatane.getPrimaryStage().getX() + 200);
        fenetre.setY(BackToKatane.getPrimaryStage().getY() + 300);
        for (int i = 0; i<8; i++){
            ImageView imageView = new ImageView("images/material" + i + ".png");
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(40);
            imageView.relocate( 50 + 50*i, 50);
            int finalI = i;
            imageView.setOnMouseClicked(mouseEvent -> {
                if (carte instanceof  CarteMonopole) ((CarteMonopole)carte).setTypeRessource(finalI);
                else ((CarteDecouverte)carte).setTypeRessource(finalI);
                carte.activation(model);
                fenetre.hide();
                vue.actualiseVuesJoueurs();
            });
            g.getChildren().add(imageView);
        }
        fenetre.show();

    }

    private void achatCarteDev(){
        model.getJoueurActuel().acheterCarteDeveloppement(model.getCartes().get(model.getCartes().size()-1));
        model.getCartes().remove(model.getCartes().size()-1);
        vue.actualiseVuesJoueurs();
    }

}
