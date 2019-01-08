package vue;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Joueur;
import model.Model;
import model.Plateau;

import java.util.ArrayList;

public class Vue {
    private Model model;
    private VuePlateau[] vuesPlateaux;
    private ArrayList<VueConstruction> VuesConstructions;
    private ArrayList<VueJoueur> VuesJoueurs;

    private Scene sceneJeu;

    private Group vueJeu;
    private Button bFinTour;
    private Button bConstruction;
    private Button bCarteDev;
    private VueJoueur[] vueJoueurs;
    private Text joueurActuel;



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
        TabPane tabPane = new TabPane();
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
        bFinTour.relocate(1100, 650);
        vueJeu.getChildren().add(bFinTour);
        bConstruction=new Button("Construction");
        bConstruction.relocate(1100, 600);
        vueJeu.getChildren().add(bConstruction);
        bCarteDev=new Button("Carte Developpement");
        bCarteDev.relocate(1100, 550);
        vueJeu.getChildren().add(bCarteDev);

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
    }

    public void afficherDe(int[] des) {

        System.out.println(des[1] + des[2]);
    }

    public void actualisationFinDeTour() {
        joueurActuel.setText("C'est le tour du joueur n°"+(model.getJoueurActuel().getNumero()));
        joueurActuel.setFill(model.getJoueurActuel().getCouleur());
    }

    public void enableButton() {
        bFinTour.setDisable(false);
        bCarteDev.setDisable(false);
        bConstruction.setDisable(false);
        bFinTour.setDisable(false);
    }

    public void disableButton() {
        bFinTour.setDisable(true);
        bCarteDev.setDisable(true);
        bConstruction.setDisable(true);
        bFinTour.setDisable(true);
    }
}
