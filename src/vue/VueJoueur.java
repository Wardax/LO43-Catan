package vue;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Joueur;

public class VueJoueur extends Group{
    private Joueur joueur;

    private ImageView[] imageViews;

    private Text[] texts;
    /*
    0: Ressource1;
    1: Ressource2;
    2: Ressource3;
    3: Ressource4;
    4: Ressource5;
    5: Ressource6;
    6: Ressource7;
    7: Ressource8;
    8: PV;
    9: CD;
    10: NomJoueur
    */

    public ImageView[] objectifs;

    public VueJoueur(Joueur joueur) {
        this.joueur = joueur;
        imageViews=new ImageView[10];

        //imageViews[0]=new ImageView("image/irrigation.png");
        /*for (int i=0; i<imageViews.length; i++){
            imageViews[i].setPreserveRatio(true);
            imageViews[i].setFitHeight(30);
            getChildren().add(imageViews[i]);
        }*/

        texts =new Text[11];
        for (int i = 0; i < texts.length; i++){
            texts[i]=new Text("0");
            texts[i].setFont(Font.font ("Verdana", 20));
            texts[i].setFill(joueur.getCouleur());
            getChildren().add(texts[i]);
        }
        texts[10].setText(joueur.getNom());
        actualiseVueJoueur();
        affichageResume();
    }

    public void actualiseVueJoueur(){
        int[] ressources = joueur.getRessources();
        for (int i = 0; i < ressources.length; i++) {
            texts[i].setText(i + ":"+ressources[i]);
        }
        texts[8].setText(""+joueur.getScore());
        texts[9].setText(""+joueur.getCartes().size());
    }

    public void affichageResume(){
        relocate(900,60*joueur.getNumero());
        texts[8].relocate(0,20);
        texts[9].relocate(40,20);
        //imageViews[0].relocate(55,20);
        texts[10].relocate(240, 20);
        for (int i=1; i<8; i++){
            texts[i].relocate(i*40+25, 20);
            //imageViews[i].relocate(i*40+40,20);
        }
    }

}
