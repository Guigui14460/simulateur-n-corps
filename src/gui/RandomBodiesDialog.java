package gui;

import java.util.*;

import javafx.beans.value.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

import nbody.*;

/**
 * Classe permettant de créer une boîte de dialogue qui permettra d'instancier
 * des corps aléatoirement.
 */
public class RandomBodiesDialog extends AbstractDialog {
    /**
     * Simulateur généré.
     */
    private NBodySimulator simulator = null;

    /**
     * Constructeur de la classe.
     */
    public RandomBodiesDialog() {
        super("Simulation aléatoire", "Choix des valeurs");

        this.createButtons();
        this.createFields();
    }

    /**
     * Permet de créer tous les champs pour la boîte de dialogue.
     */
    @Override
    protected void createFields() {
        // Grille d'affichage
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nbrBodyField = new TextField();
        nbrBodyField.setPromptText("Nombre de corps");
        nbrBodyField.setText("100");
        Label nbrBodyLabel = new Label("Nombre de corps :");
        nbrBodyLabel.setLabelFor(nbrBodyField);
        grid.add(nbrBodyLabel, 0, 0);
        grid.add(nbrBodyField, 1, 0);

        TextField massField = new TextField();
        massField.setPromptText("Masse maximun");
        massField.setText("50");
        Label massLabel = new Label("Masse maximun :");
        massLabel.setLabelFor(massField);
        grid.add(massLabel, 0, 1);
        grid.add(massField, 1, 1);

        TextField radiusField = new TextField();
        radiusField.setPromptText("Diamètre du corps");
        radiusField.setText("5");
        Label radiusLabel = new Label("Diamètre des corps :");
        radiusLabel.setLabelFor(radiusField);
        grid.add(radiusLabel, 0, 2);
        grid.add(radiusField, 1, 2);

        TextField positionField = new TextField();
        positionField.setPromptText("Position maximum (pour les 3 axes)");
        positionField.setText("200");
        Label positionLabel = new Label("Position maximum (pour les 3 axes) :");
        positionLabel.setLabelFor(positionField);
        grid.add(positionLabel, 0, 3);
        grid.add(positionField, 1, 3);

        TextField velocityField = new TextField();
        velocityField.setPromptText("Velocité maximum (pour les 3 axes)");
        velocityField.setText("0");
        Label velocityLabel = new Label("Velocité maximum (pour les 3 axes) :");
        velocityLabel.setLabelFor(velocityField);
        grid.add(velocityLabel, 0, 4);
        grid.add(velocityField, 1, 4);

        ColorPicker colorField = new ColorPicker();
        colorField.setValue(Color.WHITE);
        Label colorLabel = new Label("Choix de la couleur des corps :");
        colorLabel.setLabelFor(colorField);
        grid.add(colorLabel, 0, 5);
        grid.add(colorField, 1, 5);

        // Écouteurs sur les champs
        Node goConfirm = this.getDialogPane().lookupButton(this.confirm);
        ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
            if (oldValue != null || newValue != null) {
                // Désactivation du bouton "Confirmer" si un des champs est vide
                goConfirm.setDisable(newValue.trim().isEmpty());
            } else {
                goConfirm.setDisable(true);
            }
        });
        nbrBodyField.textProperty().addListener(listener);
        massField.textProperty().addListener(listener);
        radiusField.textProperty().addListener(listener);
        positionField.textProperty().addListener(listener);
        velocityField.textProperty().addListener(listener);

        // On ajoute la grille à la boîte
        this.getDialogPane().setContent(grid);

        // On récupère les informations
        Optional<ButtonType> result = this.showAndWait();
        if (result.get() == this.confirm && nbrBodyField != null && positionField != null && massField != null
                && radiusField != null && velocityField != null) {
            try {
                int nbrBody = Integer.parseInt(nbrBodyField.getText());
                double mass = Double.parseDouble(massField.getText());
                double radius = Double.parseDouble(radiusField.getText());
                double position = Double.parseDouble(positionField.getText());
                double velocity = Double.parseDouble(velocityField.getText());
                Color color = colorField.getValue();

                // On effectue des vérifications
                if (nbrBody <= 0 || mass <= 0 || radius <= 0 || position <= 0 || velocity < 0) {
                    throw new Exception("Les valeurs doivent être strictement positives !");
                }
                // S'il n'y a pas eu d'erreur, on crée le simulateur
                this.simulator = new NBodySimulator(nbrBody, mass, radius, position, velocity, color);
            } catch (Exception e) {
                // On crée une boîte de dialogue d'erreur pour prévenir l'utilisateur
                Alert dialogE = new Alert(AlertType.ERROR);
                dialogE.getDialogPane().getStylesheets().add(getClass().getResource("Dialog.css").toExternalForm());
                dialogE.setTitle("Erreur lors de la création des corps");
                dialogE.setHeaderText(null);
                dialogE.setContentText(e.getMessage());
                Optional<ButtonType> answer = dialogE.showAndWait();
            }
        }
    }

    /**
     * Permet de créer les boutons de la boîte de dialogue.
     */
    @Override
    protected void createButtons() {
        this.confirm = new ButtonType("Confirmer", ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(confirm, ButtonType.CANCEL);
    }

    /**
     * Permet de récupérer le simulateur stocké.
     * 
     * @return Simulateur
     */
    public NBodySimulator getSimulator() {
        return this.simulator;
    }
}