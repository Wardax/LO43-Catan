import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
        affichageResume();
    }

    public void actualiseVueJoueur(){
        int[] ressources = joueur.getRessources();
        for (int i = 0; i < ressources.length; i++) {
            texts[0].setText(""+ressources[i]);
        }
        texts[1].setText(""+joueur.getScore());
        texts[2].setText(""+joueur.getCartes().size());
    }

    public void affichageResume(){
        relocate(900,60*joueur.getNumero());
        texts[6].relocate(0,20);
        texts[0].relocate(40,20);
        //imageViews[0].relocate(55,20);
        texts[5].relocate(240, 20);
        for (int i=1; i<5; i++){
            texts[i].relocate(i*40+25, 20);
            //imageViews[i].relocate(i*40+40,20);
        }
    }

}
