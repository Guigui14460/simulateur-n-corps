package gui;

import javafx.scene.control.*;
import javafx.util.*;

import nbody.*;

/**
 * Classe permettant de générer la liste qui permet de gérer les corps.
 */
public class BodyFactory implements Callback<ListView<Body>, ListCell<Body>> {
    /**
     * Permet de créer la liste des cellules (liste des corps de la simulation).
     * 
     * @param listView Liste de vue des corps
     * @return Une liste de cellules représentant la liste de vue des corps
     */
    @Override
    public BodyListCell call(ListView<Body> listView) {
        return new BodyListCell();
    }
}