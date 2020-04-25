package gui;

import java.io.*;
import java.util.*;

import javafx.animation.*;
import javafx.application.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.transform.*;
import javafx.stage.*;

import math_physics.physics.*;
import nbody.*;

/**
 * Classe permettant de créer et d'afficher la simulation.
 */
public class SimulationScene extends AbstractScene {
    /**
     * Permet de garder le menu afin de pouvoir revenir au menu principal.
     */
    private Scene menuScene;

    /**
     * Simulateur à N corps.
     */
    private NBodySimulator simulator;

    /**
     * Service du simulateur pour l'exécuter sur un autre Thread.
     */
    private SimulationService service;

    /**
     * Permet d'avoir une animation. Utilisé pour la simulation automatique.
     */
    private AnimationTimer timer;

    /**
     * Fichier temporaire pour garder avant le début de la simulation.
     */
    private File tempFile = null;

    /**
     * Permet de stocker les données lors du clic pour la rotation.
     */
    private double anchorX, anchorY, anchorAngleX = 0, anchorAngleY = 0;

    /**
     * Propriétés représentant l'angle en X.
     */
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);

    /**
     * Propriétés représentant l'angle en Y.
     */
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    /**
     * Constructeur de la classe.
     * 
     * @param stage     Objet sur lequel la fenêtre est dessinée
     * @param menuScene Scène du menu
     * @param simulator Simulateur instancié
     */
    public SimulationScene(Stage stage, MenuScene menuScene, NBodySimulator simulator) {
        super(new HBox(), stage);
        this.menuScene = menuScene;
        this.simulator = simulator;
        this.service = new SimulationService(simulator);

        // On effectue une sauvegarde dans un fichier temporaire avant de lancer la
        // simulation
        try {
            this.tempFile = NBodyFileParser.saveInTemporaryFile(simulator, "tmpFileNBodySimulator1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setFill(Color.BLACK);
        this.modifyRoot();

        // Lancement de la simulation
        this.animateBodies();
    }

    /**
     * Permet de modifier l'objet parent afin d'afficher les objets pour la
     * simulation. On crée donc les 2 parties de la simulation ici.
     */
    @Override
    protected void modifyRoot() {
        BorderPane separator = new BorderPane();
        separator.setCenter(this.setLeftScene());
        separator.setRight(this.setRightScene());

        this.setRoot(separator);
    }

    /**
     * Permet de créer la partie gauche de la simulation.
     * 
     * @return Partie gauche
     */
    private Pane setLeftScene() {
        // Création de l'espace de simulation
        Pane simulation_part = new Pane();
        simulation_part.setTranslateX((this.stage.getWidth() - 250) / 2);
        simulation_part.setTranslateY(this.stage.getHeight() / 2);
        simulation_part.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        // Ajout des corps sur le visuel de l'interface graphique
        for (GenericObject b : this.simulator.getBodies()) {
            Body body = (Body) b;
            simulation_part.getChildren().add(body.getSphere());
        }

        // On créé un autre pane pour éviter de voir les bords lors d'un dézoom
        Pane pane = new Pane(simulation_part);
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        // Ajout d'un évènement lors d'un scroll
        pane.setOnScroll(new EventHandler<ScrollEvent>() {
            public void handle(ScrollEvent e) {
                // Règle le problème de zoom sur Ubuntu
                if(e.getDeltaY() != 0){
                    // Calcul du facteur de zoom/dézoom
                    double zoomFactor = 1.25;
                    if (e.getDeltaY() < 0) {
                        zoomFactor = 1 / zoomFactor;
                    }
                    // Calcul du nouveau zoom
                    double scaleX = simulation_part.getScaleX() * zoomFactor;
                    double scaleY = simulation_part.getScaleY() * zoomFactor;
    
                    // On met à jour
                    simulation_part.setScaleX(scaleX);
                    simulation_part.setScaleY(scaleY);
                }
            }
        });

        // On crée les objets pour la rotation
        Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        // On ajoute les transformations
        simulation_part.getTransforms().addAll(xRotate, yRotate);
        // On fait en sorte d'observer les propriétés
        xRotate.angleProperty().bind(this.angleX);
        yRotate.angleProperty().bind(this.angleY);

        // On affecte les valeurs lors du clic
        pane.setOnMousePressed(e -> {
            anchorX = e.getSceneX();
            anchorY = e.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        // On modifie les propriétés lorsque l'on "drag"
        pane.setOnMouseDragged(e -> {
            angleX.set(anchorAngleX - (anchorY - e.getSceneY()));
            angleY.set(anchorAngleY + (anchorX - e.getSceneX()));
        });

        return pane;
    }

    /**
     * Permet de créer la partie droite de la simulation.
     * 
     * @return Partie droite
     */
    private Node setRightScene() {
        Label label = new Label("Luna's simulation");
        label.setStyle("-fx-text-fill: #c2b439; ");
        label.setFont(new Font("Arial", 30));
        label.setTranslateY(15);
        GridPane buttons = this.createButtons();
        buttons.setTranslateY(30);
        Accordion accordion = this.createAccordion();
        accordion.setTranslateY(45);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #1e4655;");
        layout.getChildren().addAll(label, buttons, accordion);
        layout.getStylesheets().add(this.getClass().getResource("SimulationScene.css").toExternalForm());
        layout.setMaxWidth(250);
        return layout;
    }

    /**
     * Permet de créer l'accordéon contenant les outils pour changer les paramètres
     * et pour voir tous les objets.
     * 
     * @return Accordéon
     */
    private Accordion createAccordion() {
        Accordion accordion = new Accordion();

        TitledPane t1 = new TitledPane("Général", this.createParameters());
        TitledPane t2 = new TitledPane("Liste des objets", this.createBodyList());

        accordion.getPanes().addAll(t1, t2);
        return accordion;
    }

    /**
     * Permet de créer les paramètres de la simulation.
     * 
     * @return Groupe de paramètres
     */
    private GridPane createParameters() {
        GridPane gridpane = new GridPane();
        gridpane.getColumnConstraints().add(new ColumnConstraints(250));

        Slider sliderSpeed = new Slider(0, 1, 1);
        sliderSpeed.setMin(0);
        sliderSpeed.setMajorTickUnit(100);
        sliderSpeed.setMax(10000);
        sliderSpeed.setValue(1);
        sliderSpeed.setShowTickLabels(true);
        sliderSpeed.setShowTickMarks(true);
        sliderSpeed.setValueChanging(true);
        Label speedLabel = new Label("Vitesse de la simulation : " + sliderSpeed.getValue() + " s");
        speedLabel.setLabelFor(sliderSpeed);
        sliderSpeed.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                simulator.setDeltaTime(newValue.intValue());
                speedLabel.setText("Vitesse de la simulation : " + newValue.intValue() + " s");
            }
        });
        gridpane.add(speedLabel, 0, 0);
        gridpane.add(sliderSpeed, 0, 1);

        Slider sliderTheta = new Slider();
        sliderTheta.setMin(0);
        sliderTheta.setMajorTickUnit(0.5);
        sliderTheta.setMax(2);
        sliderTheta.setValue(0.5);
        sliderTheta.setShowTickLabels(true);
        sliderTheta.setShowTickMarks(true);
        sliderTheta.setValueChanging(true);
        Label thetaLabel = new Label("Thêta : " + sliderTheta.getValue());
        thetaLabel.setLabelFor(sliderTheta);
        sliderTheta.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                simulator.setTheta(newValue.floatValue());
                thetaLabel.setText("Thêta : " + newValue.floatValue());
            }
        });
        gridpane.add(thetaLabel, 0, 2);
        gridpane.add(sliderTheta, 0, 3);

        CheckBox barnesHutActive = new CheckBox("À une vache près");
        barnesHutActive.setSelected(true);
        gridpane.add(barnesHutActive, 0, 4);

        return gridpane;
    }

    private ListView<Body> createBodyList() {
        ObservableList<Body> bodies = FXCollections.observableArrayList();
        for (GenericObject o : this.simulator.getBodies()) {
            Body body = (Body) o;
            bodies.add(body);
        }

        ListView<Body> list = new ListView<Body>(bodies);

        list.setCellFactory(new BodyFactory());
        list.getSelectionModel().selectFirst();
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Body>() {
            public void changed(ObservableValue<? extends Body> ov, final Body oldValue, final Body newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (oldValue.equals(newValue)) {
                            new BodyDialog("Changement des paramètres", oldValue);
                        } else if (newValue == null) {
                            System.out.println("Click en dehors de la liste");
                        } else {
                            new BodyDialog("Changement des paramètres", newValue);
                        }
                    }
                });
            }
        });

        return list;
    }

    /**
     * Permet de créer les boutons de la scène.
     * 
     * @return Grille de boutons
     */
    private GridPane createButtons() {
        GridPane buttonHolder = new GridPane();
        buttonHolder.getColumnConstraints().add(new ColumnConstraints(100));
        buttonHolder.getColumnConstraints().add(new ColumnConstraints(100));

        // Bouton pour sauvegarder les corps de la simulation
        Button buttonSave = new Button("Sauvegarder");
        buttonSave.setOnAction((ActionEvent event) -> {
            Dialog<ButtonType> dialog = new Dialog<ButtonType>();
            dialog.setTitle("Choix de la sauvegarde");
            dialog.setHeaderText("Quelle sauvegarde voulez-vous effectuer ?");
            dialog.getDialogPane().getScene().getStylesheets()
                    .add(this.getClass().getResource("Dialog.css").toExternalForm());
            ButtonType confirm1 = new ButtonType("Avant simulation", ButtonData.BACK_PREVIOUS);
            ButtonType confirm2 = new ButtonType("Simulation actuelle", ButtonData.NEXT_FORWARD);
            dialog.getDialogPane().getButtonTypes().addAll(confirm1, confirm2, ButtonType.CANCEL);
            ButtonBar bar = (ButtonBar) dialog.getDialogPane().lookup(".button-bar");
            bar.getButtons().forEach(b -> {
                if (((Button) b).getText() == confirm1.getText()) {
                    b.setStyle("-fx-background-color: #3862a5");
                }
            });
            int simulationFilechoice = 0;
            Optional<ButtonType> answer = dialog.showAndWait();
            if (answer.get() == confirm1) {
                simulationFilechoice = 1;
            } else if (answer.get() == confirm2) {
                simulationFilechoice = 2;
            } else {
                event.consume();
            }

            if (simulationFilechoice != 0) {
                FileChooser fileChooserSave = new FileChooser();
                // Filtration des fichiers
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
                fileChooserSave.getExtensionFilters().add(extFilter);
                File file = fileChooserSave.showSaveDialog(stage);
                if (file != null) {
                    try {
                        File choice = this.tempFile;
                        if (simulationFilechoice == 2) {
                            // On effectue une sauvegarde dans un fichier temporaire de la simulation
                            // actuelle
                            choice = NBodyFileParser.saveInTemporaryFile(simulator, "tmpFileNBodySimulator2");
                        }
                        NBodyFileParser.saveFile(choice, file);
                    } catch (IOException e) {
                        Alert dialogE = new Alert(AlertType.ERROR);
                        dialogE.setTitle("Erreur lors de la sauvegarde");
                        dialogE.setHeaderText(null);
                        dialogE.setContentText(
                                "Une erreur est survenu lors de la sauvegarde. La simulation n'a pas pu être sauvegardée.");
                        // Optional<ButtonType> answer = dialogE.showAndWait();
                    }
                }
            }
        });

        // Bouton pour retourner au menu principal
        Button backToMenu = new Button("Retour Menu");
        backToMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Alert dialogC = new Alert(AlertType.CONFIRMATION);
                dialogC.getDialogPane().getStylesheets().add(getClass().getResource("Dialog.css").toExternalForm());
                dialogC.setTitle("Retourner au menu principal");
                dialogC.setHeaderText(null);
                dialogC.setContentText(
                        "Si vous retourner au menu principal, toutes les données de la simulation seront perdues. Voulez-vous vraiment retourner au menu principal de l'application ?");
                Optional<ButtonType> answer = dialogC.showAndWait();
                if (answer.get() == ButtonType.OK) {
                    returnToMenu();
                } else {
                    e.consume();
                }
            }
        });

        // Bouton pour quitter l'application
        Button exitButton = new Button("Quitter");
        exitButton.getStyleClass().add("exit_button");
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
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
                    e.consume();
                }
            }
        });

        buttonHolder.add(buttonSave, 0, 2);
        buttonHolder.add(backToMenu, 1, 2);
        buttonHolder.add(exitButton, 2, 2);
        return buttonHolder;
    }

    /**
     * Permet de lancer la simulation des corps.
     */
    private void animateBodies() {
        // Instanciation de l'animation
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                // if (!service.isRunning()) {
                // service.restart(); // Simulation des corps
                // }
                simulator.simulateAllBodies();
            }
        };
        timer.start(); // Lancement de l'animation
    }

    /**
     * Permet de retourner au menu principal.
     */
    private void returnToMenu() {
        this.stage.setScene(this.menuScene);
    }
}