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
        imageViews=new ImageView[11];

        //imageViews[0]=new ImageView("image/irrigation.png");
        /*for (int i=0; i<imageViews.length; i++){
            imageViews[i].setPreserveRatio(true);
            imageViews[i].setFitHeight(30);
            getChildren().add(imageViews[i]);
        }*/

        texts =new Text[12];
        for (int i = 0; i < texts.length; i++){
            texts[i]=new Text("0");
            texts[i].setFont(Font.font ("Verdana", 20));
            texts[i].setFill(joueur.getCouleur());
            getChildren().add(texts[i]);
        }
        texts[11].setText(joueur.getNom());
        actualiseVueJoueur();
        affichageResume();
    }

    public void actualiseVueJoueur(){
        int[] ressources = joueur.getRessources();
        for (int i = 0; i < ressources.length; i++) {
            texts[i].setText("" + ressources[i]);
        }
        texts[8].setText("" + joueur.getScore());
        texts[9].setText("" + joueur.getCartes().size());
        texts[10].setText("" + joueur.getTotalRessources());
    }

    public void affichageResume(){
        relocate(900,120*(joueur.getNumero()-1));
        texts[8].relocate(0,50);
        texts[9].relocate(70,50);
        //imageViews[0].relocate(55,20);
        texts[10].relocate(140, 50);
        texts[11].relocate(0, 20);
        for (int i=0; i<4; i++){
            texts[i].relocate(i*70, 80);
            //imageViews[i].relocate(i*40+40,20);
        }
        for (int i=0; i<4; i++){
            texts[i+4].relocate(i*70, 100);
            //imageViews[i].relocate(i*40+40,20);
        }
    }

}
