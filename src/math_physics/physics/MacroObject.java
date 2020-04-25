package math_physics.physics;

import math_physics.math.*;

/**
 * Cette classe permet de représenter un objet macroscopique (planète, étoile,
 * galaxie, ...).
 */
public class MacroObject extends Particule {
    /**
     * Correspond au nom de l'objet.
     */
    protected String name;

    /**
     * Correspond à l'objet auquel il est rattaché.
     */
    protected MacroObject root;

    /**
     * Constructeur d'un objet macroscopique.
     * 
     * @param mass     Masse totale de l'objet
     * @param radius   Rayon de l'objet
     * @param position Coordonnées de la position de l'objet
     * @param velocity Vitesse initiale de déplacement de l'objet
     */
    public MacroObject(double mass, double radius, Vector3D position, Vector3D velocity) {
        this("", null, mass, radius, position, velocity);
    }

    /**
     * Constructeur d'un objet macroscopique.
     * 
     * @param name     Nom de l'objet
     * @param root     Instance de l'objet auquel il est rattaché
     * @param mass     Masse totale de l'objet
     * @param radius   Rayon de l'objet
     * @param position Coordonnées de la position de l'objet
     * @param velocity Vitesse initiale de déplacement de l'objet
     */
    public MacroObject(String name, MacroObject root, double mass, double radius, Vector3D position,
            Vector3D velocity) {
        super(mass, radius, position, velocity);
        this.name = name;
        this.root = root;
    }

    @Override
    public String toString() {
        return this.name + " : " + this.mass;
    }

    /**
     * Permet de récupérer le nom de l'objet.
     * 
     * @return Nom de l'objet
     */
    public String getObjectName() {
        return this.name;
    }

    /**
     * Permet d'affecter le nouveau nom de l'objet.
     * 
     * @param newName Nouveau nom de l'objet
     */
    public void setObjectName(String newName) {
        this.name = newName;
    }

    /**
     * Permet d'avoir l'objet macroscopique auquel il est rattaché.
     * 
     * @return Objet macroscopique
     */
    public MacroObject getRootObject() {
        return this.root;
    }
}