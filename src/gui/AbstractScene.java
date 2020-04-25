package gui;

import javafx.scene.*;
import javafx.stage.*;

/**
 * Classe permettant de faire notre propre scène.
 */
public class AbstractScene extends Scene {
    /**
     * Permet de garder l'objet utilisé par la fenêtre pour afficher dessus.
     */
    protected Stage stage;

    /**
     * Constructeur de la classe.
     * 
     * @param root  Élément parent de la scène
     * @param stage Objet sur lequel la fenêtre est dessinée
     */
    public AbstractScene(Parent root, Stage stage) {
        super(root);
        this.stage = stage;
    }

    /**
     * Permet de modifier l'objet parent afin d'afficher les objets que l'on
     * souhaite.
     */
    protected void modifyRoot() {
    }
}