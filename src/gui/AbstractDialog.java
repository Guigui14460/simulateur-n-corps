package gui;

import javafx.scene.control.*;

/**
 * Classe permettant de faire notre propre boîte de dialogue.
 */
public class AbstractDialog extends Dialog<ButtonType> {
    /**
     * Bouton de confirmation.
     */
    protected ButtonType confirm;

    /**
     * Constructeur de la classe.
     * 
     * @param title Titre de la boîte de dialogue
     */
    public AbstractDialog(String title) {
        this(title, "");
    }

    /**
     * Constructeur de la classe.
     * 
     * @param title      Titre de la boîte de dialogue
     * @param headerText Text à afficher dans la boîte
     */
    public AbstractDialog(String title, String headerText) {
        this.setTitle(title);
        this.setHeaderText(headerText);
        this.getDialogPane().getScene().getStylesheets()
                .add(this.getClass().getResource("Dialog.css").toExternalForm());
    }

    /**
     * Permet de créer tous les champs pour la boîte de dialogue.
     */
    protected void createFields() {
    }

    /**
     * Permet de créer les boutons de la boîte de dialogue.
     */
    protected void createButtons() {
    }
}