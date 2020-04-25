package gui;

import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * Classe représentant la page d'accueil de l'application.
 */
public class HomeScene extends AbstractScene {
    /**
     * Scène suivante.
     */
    private Scene nextScene;

    /**
     * Constructeur de la classe.
     * 
     * @param stage Objet sur lequel la fenêtre est dessinée
     */
    public HomeScene(Stage stage) {
        super(new HBox(), stage);
        this.nextScene = new MenuScene(stage, this);
        this.nextScene.getStylesheets().add(this.getClass().getResource("HomeScene.css").toExternalForm());

        this.modifyRoot();
    }

    /**
     * Permet de modifier l'objet parent afin d'afficher les objets pour la page
     * d'accueil. On crée donc la page d'accueil ici.
     */
    @Override
    protected void modifyRoot() {
        Label label = new Label("Luna's program");
        label.setTranslateY(-20);

        Button goToMenu = new Button("Aller au menu");

        // Lorsque l'on va cliquer, on va afficher le menu
        goToMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.setScene(nextScene);
            }
        });

        // On crée un layout
        VBox layout = new VBox(40);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, goToMenu);
        // On remplace le root
        this.setRoot(layout);
    }
}