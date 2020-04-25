package barnes_hut;

import math_physics.math.*;
import math_physics.physics.*;

/**
 * Représente un noeud pour l'arbre utilisé dans l'algorithme de Barnes-Hut.
 * Représente aussi la boîte contenant des objets dans la simulation de
 * Barnes-Hut.
 */
public class Octal {
    /**
     * Correspond à la position central de la boîte.
     */
    private Vector3D center;

    /**
     * Correspond aux dimensions de la boîte.
     */
    private Vector3D dimensions;

    /**
     * Correspond aux dimensions de la demi-boîte (évite de recalculer tout le
     * temps).
     */
    private Vector3D halfBox;

    /**
     * Correspond aux dimensions du quart de la boîte (évite de recalculer tout le
     * temps).
     */
    private Vector3D quarterBox;

    /**
     * Contructeur du noeud.
     * 
     * @param center   Centre de la boîte
     * @param width    Largeur de la boîte
     * @param height   Hauteur de la boîte
     * @param deepness Profondeur de la boîte
     */
    public Octal(Vector3D center, double width, double height, double deepness) {
        this(center, new Vector3D(width, height, deepness));
    }

    /**
     * Contructeur du noeud.
     * 
     * @param center     Centre de la boîte
     * @param dimensions Dimensions de la boîte
     */
    public Octal(Vector3D center, Vector3D dimensions) {
        this.center = center;
        this.dimensions = dimensions;
        this.halfBox = this.dimensions.divide(2);
        this.quarterBox = this.dimensions.divide(4);
    }

    @Override
    public String toString() {
        return "Octal(Pos=" + this.center.toString() + "| Size=" + this.dimensions.toString() + ")";
    }

    /**
     * Permet de récupérer le vecteur représentant le centre de la boîte.
     * 
     * @return Vecteur position du centre
     */
    public Vector3D getCenter() {
        return this.center;
    }

    /**
     * Permet de récupérer le vecteur représentant les dimensions de la boîte.
     * 
     * @return Vecteur dimensions
     */
    public Vector3D getDimensions() {
        return this.dimensions;
    }

    /**
     * Permet de récupérer seulement la largeur de la boîte.
     * 
     * @return Largeur de la boîte
     */
    public double getWidth() {
        return this.dimensions.getX();
    }

    /**
     * Permet de récupérer seulement la hauteur de la boîte.
     * 
     * @return Hauteur de la boîte
     */
    public double getHeight() {
        return this.dimensions.getY();
    }

    /**
     * Permet de récupérer seulement la profondeur de la boîte.
     * 
     * @return Profondeur de la boîte
     */
    public double getDeepness() {
        return this.dimensions.getZ();
    }

    /**
     * Permet de récupérer les dimensions de la demi-boîte.
     * 
     * @return Dimensions de la demi-boîte.
     */
    public Vector3D getHalfBox() {
        return this.halfBox;
    }

    /**
     * Permet de récupérer les dimensions du quart de la boîte.
     * 
     * @return Dimensions du quart de la boîte.
     */
    public Vector3D getQuarterBox() {
        return this.quarterBox;
    }

    /**
     * Permet de créer le noeud/boîte correspondant au Nord-Est Avant du noeud/boîte
     * actuel.
     * 
     * @return Noeud au Nord-Est Avant
     */
    public Octal NEF() {
        double x = this.center.getX() + this.quarterBox.getX();
        double y = this.center.getY() + this.quarterBox.getY();
        double z = this.center.getZ() + this.quarterBox.getZ();
        return new Octal(new Vector3D(x, y, z), this.halfBox);
    }

    /**
     * Permet de créer le noeud/boîte correspondant au Nord-Est Arrière du
     * noeud/boîte actuel.
     * 
     * @return Noeud au Nord-Est Arrière
     */
    public Octal NEB() {
        double x = this.center.getX() + this.quarterBox.getX();
        double y = this.center.getY() + this.quarterBox.getY();
        double z = this.center.getZ() - this.quarterBox.getZ();
        return new Octal(new Vector3D(x, y, z), this.halfBox);
    }

    /**
     * Permet de créer le noeud/boîte correspondant au Nord-Ouest Avant du
     * noeud/boîte actuel.
     * 
     * @return Noeud au Nord-Ouest Avant
     */
    public Octal NWF() {
        double x = this.center.getX() - this.quarterBox.getX();
        double y = this.center.getY() + this.quarterBox.getY();
        double z = this.center.getZ() + this.quarterBox.getZ();
        return new Octal(new Vector3D(x, y, z), this.halfBox);
    }

    /**
     * Permet de créer le noeud/boîte correspondant au Nord-Ouest Arrière du
     * noeud/boîte actuel.
     * 
     * @return Noeud au Nord-Ouest Arrière
     */
    public Octal NWB() {
        double x = this.center.getX() - this.quarterBox.getX();
        double y = this.center.getY() + this.quarterBox.getY();
        double z = this.center.getZ() - this.quarterBox.getZ();
        return new Octal(new Vector3D(x, y, z), this.halfBox);
    }

    /**
     * Permet de créer le noeud/boîte correspondant au Sud-Est Avant du noeud/boîte
     * actuel.
     * 
     * @return Noeud au Sud-Est Avant
     */
    public Octal SEF() {
        double x = this.center.getX() + this.quarterBox.getX();
        double y = this.center.getY() - this.quarterBox.getY();
        double z = this.center.getZ() + this.quarterBox.getZ();
        return new Octal(new Vector3D(x, y, z), this.halfBox);
    }

    /**
     * Permet de créer le noeud/boîte correspondant au Sud-Est Arrière du
     * noeud/boîte actuel.
     * 
     * @return Noeud au Sud-Est Arrière
     */
    public Octal SEB() {
        double x = this.center.getX() + this.quarterBox.getX();
        double y = this.center.getY() - this.quarterBox.getY();
        double z = this.center.getZ() - this.quarterBox.getZ();
        return new Octal(new Vector3D(x, y, z), this.halfBox);
    }

    /**
     * Permet de créer le noeud/boîte correspondant au Sud-Ouest Avant du
     * noeud/boîte actuel.
     * 
     * @return Noeud au Sud-Ouest Avant
     */
    public Octal SWF() {
        double x = this.center.getX() - this.quarterBox.getX();
        double y = this.center.getY() - this.quarterBox.getY();
        double z = this.center.getZ() + this.quarterBox.getZ();
        return new Octal(new Vector3D(x, y, z), this.halfBox);
    }

    /**
     * Permet de créer le noeud/boîte correspondant au Sud-Ouest Arrière du
     * noeud/boîte actuel.
     * 
     * @return Noeud au Sud-Ouest Arrière
     */
    public Octal SWB() {
        double x = this.center.getX() - this.quarterBox.getX();
        double y = this.center.getY() - this.quarterBox.getY();
        double z = this.center.getZ() - this.quarterBox.getZ();
        return new Octal(new Vector3D(x, y, z), this.halfBox);
    }

    /**
     * Permet de vérifier si un corps précis se trouve ou pourrait se trouver dans
     * la boîte.
     * 
     * @param object Corps
     * @return Booléen : true s'il s'y trouve, false sinon
     */
    public boolean contains(GenericObject object) {
        return this.contains(object.getPosition());
    }

    /**
     * Permet de vérifier si une position représentant un corps se trouve ou
     * pourrait se trouver dans la boîte.
     * 
     * @param position Vecteur position d'un corps
     * @return Booléen : true s'il s'y trouve, false sinon
     */
    public boolean contains(Vector3D position) {
        return this.contains(position.getX(), position.getY(), position.getZ());
    }

    /**
     * Permet de vérifier si une position représentant un corps se trouve ou
     * pourrait se trouver dans la boîte.
     * 
     * @param x Coordonnées du corps sur l'axe des X
     * @param y Coordonnées du corps sur l'axe des Y
     * @param z Coordonnées du corps sur l'axe des Z
     * @return Booléen : true s'il s'y trouve, false sinon
     */
    public boolean contains(double x, double y, double z) {
        return (x <= this.center.getX() + this.halfBox.getX() && x > this.center.getX() - this.halfBox.getX()
                && y <= this.center.getY() + this.halfBox.getY() && y > this.center.getY() - this.halfBox.getY()
                && z <= this.center.getZ() + this.halfBox.getZ() && z > this.center.getZ() - this.halfBox.getZ());
    }
}