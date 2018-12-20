public class BackToCatan {
    public static void main(String[] args) {
        Partie p = new Partie();
        Vue v = new Vue(p);
        Controlleur c = new Controlleur(p, v);
    }

}
