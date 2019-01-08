package vue;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        for (int i = 0; i < 1/*plateaux.length*/; i++) {
            vuesPlateaux[i] = new VuePlateau(plateaux[i]);
        }

        Joueur[] joueurs = model.getJoueurs();
        vueJoueurs = new VueJoueur[joueurs.length];
        for (int i = 0; i < joueurs.length; i++) {
            vueJoueurs[i] = new VueJoueur(joueurs[i]);
        }

        vueJeu.getChildren().add(vuesPlateaux[0]);

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
}
