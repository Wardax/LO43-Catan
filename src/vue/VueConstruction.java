package vue;

import javafx.scene.image.ImageView;
import model.constructions.Construction;


public class VueConstruction extends ImageView{

    private Construction construction;

    public VueConstruction(String s, Construction c){
        super(s);
        construction = c;
    }

    public Construction getConstruction() {
        return construction;
    }
}
