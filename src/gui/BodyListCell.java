package gui;

import javafx.scene.control.*;

import nbody.*;

/**
 * Classe permettant de représenter une liste de corps. Chaque corps est
 * afffiché dans une cellule.
 */
public class BodyListCell extends ListCell<Body> {
    /**
     * Permet de mettre à jour un item de la liste (ou d'en ajouter un). L'item est
     * donc un corps de type <em>Body</em>.
     * 
     * @param item  Corps à ajouter/modifier dans la liste
     * @param empty Booléen représentant si la cellule est vide ou non (si le corps
     *              a déjà été rajouté ou non)
     */
    @Override
    public void updateItem(Body item, boolean empty) {
        // On appelle la méthode initiale
        super.updateItem(item, empty);

        // On choisit le nom à faire apparaître dans la cellule
        int index = this.getIndex();
        String name = null;
        if (item != null && !empty) {
            name = (index + 1) + ". " + item.getObjectName() + " Masse : " + item.getMass();
        }

        // On affiche le nom et on dessine la cellule
        this.setText(name);
        this.setGraphic(null);
    }
}