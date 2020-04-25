package gui;

import java.io.*;
import java.util.*;

import javafx.application.*;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import nbody.*;

/**
 * Classe permettant de créer et d'afficher le menu.
 */
public class MenuScene extends AbstractScene {
    /**
     * Permet de garder la scène précédente afin de pouvoir revenir en arrière.
     */
    private Scene previousScene;

    /**
     * Constructeur de la classe.
     * 
     * @param stage         Objet sur lequel la fenêtre est dessinée
     * @param previousScene Scène d'où l'on vient
     */
    public MenuScene(Stage stage, Scene previousScene) {
        super(new HBox(), stage);
        this.previousScene = previousScene;
        this.modifyRoot();
    }

    /**
     * Permet de modifier l'objet parent afin d'afficher les objets pour le menu. On
     * crée donc le menu ici.
     */
    @Override
    protected void modifyRoot() {
        Label label_title = new Label("Luna's program");
        label_title.getStyleClass().add("label_title");
        Label label = new Label("Choix des simulations :");
        Button button1 = new Button("Lancer la simulation à N corps avec des paramètres aléatoires");
        // Lorsque l'on clique, on crée lance une boîte de dialogue qui créera une
        // simulation aléatoire
        button1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                RandomBodiesDialog dialog = new RandomBodiesDialog();
                if (dialog.getSimulator() != null) {
                    setSimulationScene(dialog.getSimulator());
                }
            }
        });

        // Permet de choisir un fichier
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        Button button2 = new Button("Charger une sauvegarde d'une simulation déjà existante");
        // On charge le fichier quand on clique
        button2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(stage);
                // Si un fichier est choisi
                if (file != null) {
                    try {
                        NBodySimulator simulator = NBodyFileParser.load(file.getAbsolutePath().toString());
                        setSimulationScene(simulator);
                    } catch (IOException e) {
                        // On affiche la boîte dialogue d'erreur pour que l'utilisateur en soit informer
                        Alert dialogE = new Alert(AlertType.ERROR);
                        dialogE.setTitle("Erreur lors de la récupération des données");
                        dialogE.setHeaderText(null);
                        dialogE.setContentText("Le fichier renseigné ne respecte pas la norme d'écriture.");
                        // Optional<ButtonType> answer = dialogE.showAndWait();
                    }
                } else {
                    System.out.println("Ouverture de fichier annulée");
                }
            }
        });

        Button button3 = new Button("Lancer la simulation à N corps avec des paramètres spécifiques");
        // Lorsque l'on clique, on lance une boîte de dialogue pour choisir un nombre de
        // corps à instancier
        button3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.getDialogPane().getScene().getStylesheets()
                        .add(this.getClass().getResource("Dialog.css").toExternalForm());
                dialog.setTitle("Choix des valeurs");
                dialog.setHeaderText(null);

                ButtonType confirm = new ButtonType("Suivant", ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(confirm, ButtonType.CANCEL);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField nbrBodyField = new TextField();
                nbrBodyField.setPromptText("Nombre de corps à créer");
                nbrBodyField.setText("10");
                grid.add(new Label("Nombre de corps à créer : "), 0, 0);
                grid.add(nbrBodyField, 1, 0);

                Node goConfirm = dialog.getDialogPane().lookupButton(confirm);
                ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
                    if (oldValue != null || newValue != null) {
                        goConfirm.setDisable(newValue.trim().isEmpty());
                    } else {
                        goConfirm.setDisable(true);
                    }
                });
                nbrBodyField.textProperty().addListener(listener);

                dialog.getDialogPane().setContent(grid);

                // On effectue des vérifications
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.get() == confirm && nbrBodyField != null) {
                    try {
                        int nbrBody = Integer.parseInt(nbrBodyField.getText());
                        if (nbrBody <= 0) {
                            throw new Exception("Le nombre de corps doit être strictement positif !");
                        }
                        // On instancie le simulateur
                        NBodySimulator simulator = new NBodySimulator();
                        for (int i = 1; i <= nbrBody; i++) {
                            // On crée une boîte de dialogue pour chaque corps que l'on souhaite instancier
                            BodyDialog dialog2 = new BodyDialog("Création du corps N°" + i);
                            if (dialog2.getCanceled()) {
                                throw new Exception("Une erreur est survenue lors de l'ajout d'un des corps !");
                            }
                            simulator.addBody(dialog2.getBody());
                        }
                        // S'il n'y a pas d'erreur, on va vers la scène de simulation
                        setSimulationScene(simulator);
                    } catch (Exception e) {
                        // Une boîte de dialogue d'erreur est ouverte pour prévenir l'utilisateur de
                        // l'échec des instanciations
                        Alert dialogE = new Alert(AlertType.ERROR);
                        dialogE.getDialogPane().getStylesheets()
                                .add(getClass().getResource("Dialog.css").toExternalForm());
                        dialogE.setTitle("Erreur lors de la création des corps");
                        dialogE.setHeaderText(null);
                        dialogE.setContentText(e.getMessage());
                        Optional<ButtonType> answer = dialogE.showAndWait();
                    }
                }
            }
        });

        Button homeButton = new Button("Retourner à la page d'accueil");
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                stage.setScene(previousScene);
            }
        });

        Button exitButton = new Button("Quitter");
        exitButton.getStyleClass().add("exit_button");
        // Confirmation de la fermeture de l'application
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Alert dialogC = new Alert(AlertType.CONFIRMATION);
                dialogC.setTitle("Fermeture de l'application");
                dialogC.setHeaderText(null);
                dialogC.setContentText("Voulez-vous vraiment quitter l'application ?");
                dialogC.getDialogPane().getStylesheets().add(getClass().getResource("Dialog.css").toExternalForm());
                Optional<ButtonType> answer = dialogC.showAndWait();
                if (answer.get() == ButtonType.OK) {
                    System.out.println("Bonne journee ! ");
                    Platform.exit();
                } else {
                    e.consume();
                }
            }
        });

        label_title.setTranslateX(25);
        label.setTranslateX(25);
        button1.setTranslateX(25);
        button2.setTranslateX(25);
        button3.setTranslateX(25);
        homeButton.setTranslateX(25);
        exitButton.setTranslateX(25);

        // On crée le layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label_title, label, button1, button2, button3, homeButton, exitButton);

        // On remplace le root
        this.setRoot(layout);
    }

    /**
     * Permet de changer de scène en allant à la scène de simulation.
     * 
     * @param simulator Simulateur à N corps instancié
     */
    private void setSimulationScene(NBodySimulator simulator) {
        this.stage.setScene(new SimulationScene(this.stage, this, simulator));
    }
}