package gui;

import java.util.*;

import javafx.beans.value.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

import math_physics.math.*;
import nbody.*;

/**
 * Classe permettant de créer une boîte de dialogue qui permettra d'instancier
 * des corps aléatoirement.
 */
public class BodyDialog extends AbstractDialog {
    /**
     * Stocke si l'utilisateur a décidé d'annuler.
     */
    private boolean canceled = false;

    /**
     * Corps à modifier ou à créer.
     */
    private Body body;

    /**
     * Constructeur de la classe.
     * 
     * @param title Titre de la boîte de dialogue
     */
    public BodyDialog(String title) {
        this(title, null);
    }

    /**
     * Constructeur de la classe.
     * 
     * @param title        Titre de la boîte de dialogue
     * @param bodyToChange Corps à modifier
     */
    public BodyDialog(String title, Body bodyToChange) {
        super(title, "Choix des valeurs");
        this.body = bodyToChange;

        this.createButtons();
        this.createFields();
    }

    /**
     * Permet de créer tous les champs pour la boîte de dialogue.
     */
    @Override
    protected void createFields() {
        // On vérifie si le corps à déjà été instancié
        boolean isBody = false;
        if (this.body != null) {
            isBody = true;
        }

        // Grille d'affichage
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));

        TextField bodyName = new TextField();
        bodyName.setPromptText("Nom du corps :");
        bodyName.setText(isBody ? this.body.getObjectName() : "Corps");
        Label bodyNameLabel = new Label("Nom du corps :");
        bodyNameLabel.setLabelFor(bodyName);
        grid.add(bodyNameLabel, 0, 0);
        grid.add(bodyName, 1, 0);

        TextField massField = new TextField();
        massField.setPromptText("Masse");
        massField.setText(isBody ? Double.toString(this.body.getMass()) : "50");
        Label massLabel = new Label("Masse :");
        massLabel.setLabelFor(massField);
        grid.add(massLabel, 0, 1);
        grid.add(massField, 1, 1);

        TextField radiusField = new TextField();
        radiusField.setPromptText("Diamètre du corps");
        radiusField.setText(isBody ? Double.toString(this.body.getRadius()) : "5");
        Label radiusLabel = new Label("Diamètre du corps :");
        radiusLabel.setLabelFor(radiusField);
        grid.add(radiusLabel, 0, 2);
        grid.add(radiusField, 1, 2);

        grid.add(new Label("Position (pour les 3 axes) :"), 0, 3);
        TextField positionXField = new TextField();
        TextField positionYField = new TextField();
        TextField positionZField = new TextField();
        positionXField.setText(isBody ? Double.toString(this.body.getXPosition()) : "0");
        positionYField.setText(isBody ? Double.toString(this.body.getYPosition()) : "0");
        positionZField.setText(isBody ? Double.toString(this.body.getZPosition()) : "0");
        grid.add(positionXField, 1, 3);
        grid.add(positionYField, 2, 3);
        grid.add(positionZField, 3, 3);

        grid.add(new Label("Velocité (pour les 3 axes) :"), 0, 4);
        TextField velocityXField = new TextField();
        TextField velocityYField = new TextField();
        TextField velocityZField = new TextField();
        velocityXField.setText(isBody ? Double.toString(this.body.getXVelocity()) : "0");
        velocityYField.setText(isBody ? Double.toString(this.body.getYVelocity()) : "0");
        velocityZField.setText(isBody ? Double.toString(this.body.getZVelocity()) : "0");
        grid.add(velocityXField, 1, 4);
        grid.add(velocityYField, 2, 4);
        grid.add(velocityZField, 3, 4);

        ColorPicker colorField = new ColorPicker();
        colorField.setValue(isBody ? this.body.getColor() : Color.WHITE);
        Label colorLabel = new Label("Couleur du corps :");
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
        bodyName.textProperty().addListener(listener);
        massField.textProperty().addListener(listener);
        radiusField.textProperty().addListener(listener);
        positionXField.textProperty().addListener(listener);
        positionYField.textProperty().addListener(listener);
        positionZField.textProperty().addListener(listener);
        velocityXField.textProperty().addListener(listener);
        velocityYField.textProperty().addListener(listener);
        velocityZField.textProperty().addListener(listener);

        // On ajoute la grille à la boîte
        this.getDialogPane().setContent(grid);

        // On récupère les informations
        Optional<ButtonType> result = this.showAndWait();
        if (result.get() == this.confirm && bodyName != null && positionXField != null && positionYField != null
                && positionZField != null && massField != null && radiusField != null && velocityXField != null
                && velocityYField != null && velocityZField != null) {
            try {
                String name = bodyName.getText();
                double mass = Double.parseDouble(massField.getText());
                double radius = Double.parseDouble(radiusField.getText());
                double positionX = Double.parseDouble(positionXField.getText());
                double positionY = Double.parseDouble(positionYField.getText());
                double positionZ = Double.parseDouble(positionZField.getText());
                Vector3D position = new Vector3D(positionX, positionY, positionZ);
                double velocityX = Double.parseDouble(velocityXField.getText());
                double velocityY = Double.parseDouble(velocityYField.getText());
                double velocityZ = Double.parseDouble(velocityZField.getText());
                Vector3D velocity = new Vector3D(velocityX, velocityY, velocityZ);
                Color color = colorField.getValue();

                if (mass <= 0 || radius <= 0) {
                    throw new Exception("Les valeurs doivent être strictement positives !");
                }
                // On met à jour le corps s'il existe
                if (isBody) {
                    this.body.setObjectName(name);
                    this.body.setMass(mass);
                    this.body.setRadius(radius);
                    this.body.setPosition(position);
                    this.body.setVelocity(velocity);
                    this.body.setColor(color);
                } else {
                    // Sinon on le crée
                    this.body = new Body(name, mass, radius, position, velocity, color);
                }
            } catch (Exception e) {
                this.canceled = true;
            }
        } else {
            this.canceled = true;
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
     * Permet de récupérer le corps stocké.
     * 
     * @return Corps
     */
    public Body getBody() {
        return this.body;
    }

    /**
     * Permet de savoir si l'utilisateur a annulé son ajout de corps ou s'il y a eu
     * une erreur.
     * 
     * @return Booléen
     */
    public boolean getCanceled() {
        return this.canceled;
    }
}