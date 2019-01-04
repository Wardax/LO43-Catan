public class BackToCatan {
    public static void main(String[] args) {
        Partie p = new Partie();
        Vue v = new Vue(p);
        Controleur c = new Controleur(p, v);
    }

}
