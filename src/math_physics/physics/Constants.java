package math_physics.physics;

import math_physics.math.*;

/**
 * Classe stockant toutes les constantes
 */
public class Constants {
    /**
     * Constante gravitationnelle en N (CODATA 2018)
     */
    public static final double G = 6.67430e-11;

    /**
     * Unité astronomique.
     */
    public static final double AU = 1.496e11;

    /**
     * Masse du soleil en kg.
     */
    public static final double SUN_MASS = 1.989e30;

    /**
     * Masse de la Terre en kg.
     */
    public static final double EARTH_MASS = 5.972e24;

    /**
     * Masse de Mars en kg.
     */
    public static final double MARS_MASS = 6.39e23;

    /**
     * Masse de Jupiter en kg.
     */
    public static final double JUPITER_MASS = 1.898e27;

    /**
     * Distance moyenne entre le soleil et la Terre en m.
     */
    public static final double SUN_EARTH_DISTANCE = 1.496e11;

    /**
     * Distance moyenne entre le soleil et Mars en m.
     */
    public static final double SUN_MARS_DISTANCE = 2.27937e11;

    /**
     * Distance moyenne entre le soleil et Jupiter en m.
     */
    public static final double SUN_JUPITER_DISTANCE = 7.783e11;

    /**
     * Vecteur représentant le centre de l'espace simulé.
     */
    public static final Vector3D ORIGIN = new Vector3D();
}