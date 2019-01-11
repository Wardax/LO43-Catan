package vue;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.BackToKatane;
import model.Joueur;
import model.Model;
import model.Plateau;

import java.util.ArrayList;

public class Vue {
    private Model model;
    private VuePlateau[] vuesPlateaux;
    private ArrayList<VueJoueur> VuesJoueurs;

    private Scene sceneJeu;

    private Group vueJeu;
    private Button bFinTour;
    private Button bConstruction;
    private Button bCarteDev;
    private Button bEchange;
    private Text actionEnCours;
    private VueJoueur[] vueJoueurs;
    private Text joueurActuel;
    private TabPane tabPane;
    private Text deEpoque;
    private ImageView de1;
    private ImageView de2;



    public Vue(Model model) {
        this.model = model;
        creerSceneJeu();
    }

    private void creerSceneJeu(){
        vueJeu = new Group();


        Plateau[] plateaux = model.getPlateaux();
        vuesPlateaux = new VuePlateau[plateaux.length];
        for (int i = 0; i < plateaux.length; i++) {
            vuesPlateaux[i] = new VuePlateau(plateaux[i]);
        }

        Tab[] tabs = new Tab[plateaux.length];
        for(int i = 0; i < tabs.length; i++) {
            tabs[i] = new Tab("" + plateaux[i].getDate());
            Rectangle r = new Rectangle(0, 0);
            Group g = new Group();
            g.getChildren().addAll(r, vuesPlateaux[i]);
            tabs[i].setContent(g);
            tabs[i].setClosable(false);

        }
        tabPane = new TabPane();
        tabPane.getTabs().setAll(tabs);
        tabPane.setTabMinWidth(202);
        tabPane.setTabMinHeight(30);
        tabPane.relocate(0, 80);
        vueJeu.getChildren().add(tabPane);

        Joueur[] joueurs = model.getJoueurs();
        vueJoueurs = new VueJoueur[joueurs.length];
        for (int i = 0; i < joueurs.length; i++) {
            vueJoueurs[i] = new VueJoueur(joueurs[i]);
        }

        joueurActuel=new Text("C'est le tour du joueur n°"+(model.getJoueurActuel().getNumero()));
        vueJeu.getChildren().add(joueurActuel);
        joueurActuel.relocate(300,40);
        joueurActuel.setFont(Font.font ("Verdana", 25));
        joueurActuel.setFill(model.getJoueurActuel().getCouleur());

        Rectangle separateur = new Rectangle(3,700, Color.DARKBLUE);
        vueJeu.getChildren().add(separateur);
        separateur.relocate(880,0);

        bFinTour=new Button("Fin du tour");
        bFinTour.relocate(1000, 650);
        vueJeu.getChildren().add(bFinTour);
        bConstruction=new Button("Construction");
        bConstruction.relocate(995, 600);
        vueJeu.getChildren().add(bConstruction);
        bCarteDev=new Button("Carte Developpement");
        bCarteDev.relocate(970, 570);
        vueJeu.getChildren().add(bCarteDev);
        bEchange=new Button("Echanger");
        bEchange.relocate(1005, 540);
        vueJeu.getChildren().add(bEchange);

        deEpoque=new Text("");
        deEpoque.relocate(700, 600);
        deEpoque.setFont(Font.font ("Verdana", 15));
        de1 = new ImageView();
        de1.relocate(700, 630);
        de1.setPreserveRatio(true);
        de1.setFitHeight(40);
        de2 = new ImageView();
        de2.relocate(750, 630);
        de2.setPreserveRatio(true);
        de2.setFitHeight(40);
        vueJeu.getChildren().addAll(deEpoque, de1, de2);

        actionEnCours=new Text("");
        actionEnCours.relocate(900, 520);
        actionEnCours.setFont(Font.font ("Verdana", 15));
        vueJeu.getChildren().add(actionEnCours);

        vueJoueurs=new VueJoueur[model.getNbJoueurs()];
        for (int i=0; i<vueJoueurs.length; i++){
            vueJoueurs[i]=new VueJoueur(model.getJoueurs()[i]);
            vueJeu.getChildren().add(vueJoueurs[i]);
        }

        sceneJeu=new Scene(vueJeu,1200,700);
    }


    public Scene getSceneJeu() {
        return sceneJeu;
    }

    public Button getbFinTour() {
        return bFinTour;
    }

    public VuePlateau[] getVuesPlateaux() {
        return vuesPlateaux;
    }

    public Button getbConstruction() {
        return bConstruction;
    }

    public Button getbCarteDev() {
        return bCarteDev;
    }

    public void actualiseVuesJoueurs() {
        for (VueJoueur vj : vueJoueurs) {
            vj.actualiseVueJoueur();
        }
        actionEnCours.setText("Sélectionnez une action !");
    }

    public void afficherDe(int[] des) {
        deEpoque.setText("Epoque : " + model.getPlateaux()[des[0] - 1].getDate());
        de1.setImage(new Image("images/de"+des[1]+".jpg"));
        de2.setImage(new Image("images/de"+des[2]+".jpg"));
    }

    public void actualisationFinDeTour() {
        joueurActuel.setText("C'est le tour de "+model.getJoueurActuel().getNom());
        joueurActuel.setFill(model.getJoueurActuel().getCouleur());
    }

    public void enableButton() {
        bFinTour.setDisable(false);
        bCarteDev.setDisable(false);
        bConstruction.setDisable(false);
        bEchange.setDisable(false);
    }

    public void disableButton() {
        bFinTour.setDisable(true);
        bCarteDev.setDisable(true);
        bConstruction.setDisable(true);
        bEchange.setDisable(true);
    }

    public ButtonBase getbEchange() {
        return bEchange;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public Text getActionEnCours() {
        return actionEnCours;
    }

    public void fenetreFin() {
        Text t = new Text(model.getJoueurActuel().getNom() + " a gagné !");
        t.relocate(20, 70);
        t.setFont(Font.font ("Verdana", 25));
        t.setFill(model.getJoueurActuel().getCouleur());

        Group g = new Group();
        g.getChildren().add(t);
        Scene s = new Scene(g, 400, 200);
        Stage fenetre = new Stage();
        fenetre.setTitle("Fin");
        fenetre.setScene(s);
        fenetre.setX(BackToKatane.getPrimaryStage().getX() + 400);
        fenetre.setY(BackToKatane.getPrimaryStage().getY() + 300);
        fenetre.show();
    }
}
