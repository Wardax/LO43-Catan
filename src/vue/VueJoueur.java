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
    10: totalRessources
    11: NomJoueur
    */

    public ImageView[] objectifs;

    public VueJoueur(Joueur joueur) {
        this.joueur = joueur;
        imageViews=new ImageView[12];

        for (int i = 0; i < 8; i++) imageViews[i] = new ImageView("images/material"+i+".png");
        imageViews[8] = new ImageView("images/PV.png");
        imageViews[9] = new ImageView("images/carte0.jpg");
        imageViews[10] = new ImageView("images/ressources.png");
        imageViews[11] = new ImageView("images/chevalier.png");

        for (ImageView imageView : imageViews) {
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(20);
            getChildren().add(imageView);
        }

        texts =new Text[13];
        for (int i = 0; i < texts.length; i++){
            texts[i]=new Text("0");
            texts[i].setFont(Font.font ("Verdana", 20));
            texts[i].setFill(joueur.getCouleur());
            getChildren().add(texts[i]);
        }
        texts[12].setText(joueur.getNom());
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
        texts[11].setText("" + joueur.getNbChevalier());
    }

    public void affichageResume(){
        relocate(900,120*(joueur.getNumero()-1));
        texts[12].relocate(0, 20);
        for (int i=0; i<4; i++){
            texts[i].relocate(i*70, 80);
            imageViews[i].relocate(i*70+30,80);
        }
        for (int i=0; i<4; i++){
            texts[i+4].relocate(i*70, 100);
            imageViews[i+4].relocate(i*70+30,100);
        }


        for (int i=0; i<4; i++){
            texts[i+8].relocate(i*70, 50);
            imageViews[i+8].relocate(i*70+30,50);
        }

    }

}
