package model;

import controleur.Controleur;
import javafx.application.Application;
import javafx.stage.Stage;
import vue.Vue;

public class BackToCatan extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BackToCatan.primaryStage = primaryStage;
        Model model = new Model(4);
        Vue vue = new Vue(model);
        Controleur controleur = new Controleur(model, vue);

        primaryStage.setScene(vue.getSceneJeu());
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
