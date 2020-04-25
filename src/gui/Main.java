package gui;

import java.util.*;

import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;

/**
 * Application principale.
 */
public class Main extends Application {
    /**
     * Correspond à la largeur et la hauteur de la fenêtre.
     */
    public static final int WIDTH = 1080, HEIGHT = 780;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("The Luna's program");
        primaryStage.setResizable(false);
        // Confirmation de la fermeture de l'application
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Alert dialogC = new Alert(AlertType.CONFIRMATION);
                dialogC.getDialogPane().getStylesheets().add(getClass().getResource("Dialog.css").toExternalForm());
                dialogC.setTitle("Fermeture de l'application");
                dialogC.setHeaderText(null);
                dialogC.setContentText("Voulez-vous vraiment quitter l'application ?");
                Optional<ButtonType> answer = dialogC.showAndWait();
                if (answer.get() == ButtonType.OK) {
                    System.out.println("Bonne journee ! ");
                    Platform.exit();
                } else {
                    we.consume();
                }
            }
        });

        // On met la scène d'accueil
        Scene scene = new HomeScene(primaryStage);
        scene.getStylesheets().add(this.getClass().getResource("WelcomeScene.css").toExternalForm());
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }
}
