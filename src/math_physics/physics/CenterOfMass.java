package math_physics.physics;

import math_physics.math.*;

/**
 * Représente le centre de gravité.
 */
public class CenterOfMass {
    /**
     * Correspond à la masse du centre de gravité.
     */
    public double mass;

    /**
     * Correspond à la position du centre de gravité.
     */
    public Vector3D position;

    /**
     * Constructeur du centre de gravité.
     * 
     * @param mass     Masse
     * @param position Position
     */
    public CenterOfMass(double mass, Vector3D position) {
        this.mass = mass;
        this.position = position;
    }

    @Override
    public String toString() {
        return "COM(" + this.mass + " | " + this.position + ")";
    }
}