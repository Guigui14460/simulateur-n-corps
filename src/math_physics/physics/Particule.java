package math_physics.physics;

import math_physics.math.*;

/**
 * Cette classe permet de représenter une particule.
 */
public class Particule extends GenericObject {
    /**
     * Construction de particule
     * 
     * @param mass     Masse totale de l'objet
     * @param radius   Rayon de l'objet
     * @param position Coordonnées de la position de l'objet
     * @param velocity Vitesse initiale de déplacement de l'objet
     */
    public Particule(double mass, double radius, Vector3D position, Vector3D velocity) {
        super(mass, radius, position, velocity);
    }

    /**
     * Construction de particule
     * 
     * @param mass     Masse totale de l'objet
     * @param radius   Rayon de l'objet
     * @param position Coordonnées de la position de l'objet
     */
    public Particule(double mass, double radius, Vector3D position) {
        this(mass, radius, position, new Vector3D());
    }

    /**
     * Construction de particule
     * 
     * @param mass     Masse totale de l'objet
     * @param position Coordonnées de la position de l'objet
     */
    public Particule(double mass, Vector3D position) {
        this(mass, 10, position, new Vector3D());
    }
}