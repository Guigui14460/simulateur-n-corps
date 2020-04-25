package nbody;

import java.util.*;

import javafx.scene.paint.*;
import javafx.scene.shape.*;

import math_physics.math.*;
import math_physics.physics.*;

/**
 * Classe représentant un corps dans la simulation.
 */
public class Body extends MacroObject {
    /**
     * Correspond à l'objet visuelle représenté sur la fenêtre.
     */
    private Sphere sphere;

    /**
     * Correspond à la couleur à afficher pour l'objet.
     */
    private Color color;

    /**
     * Constructeur d'un corps.
     * 
     * @param mass     Masse totale de l'objet
     * @param radius   Rayon de l'objet
     * @param position Coordonnées de la position de l'objet
     * @param velocity Vitesse initiale de déplacement de l'objet
     * @param color    Couleur à utiliser pour afficher la sphère
     */
    public Body(double mass, double radius, Vector3D position, Vector3D velocity, Color color) {
        this("", null, mass, radius, position, velocity, color);
    }

    /**
     * Constructeur d'un corps.
     * 
     * @param name     Nom de l'objet
     * @param mass     Masse totale de l'objet
     * @param radius   Rayon de l'objet
     * @param position Coordonnées de la position de l'objet
     * @param velocity Vitesse initiale de déplacement de l'objet
     * @param color    Couleur à utiliser pour afficher la sphère
     */
    public Body(String name, double mass, double radius, Vector3D position, Vector3D velocity, Color color) {
        this(name, null, mass, radius, position, velocity, color);
    }

    /**
     * Constructeur d'un corps.
     * 
     * @param name     Nom de l'objet
     * @param root     Instance de l'objet auquel il est rattaché
     * @param mass     Masse totale de l'objet
     * @param radius   Rayon de l'objet
     * @param position Coordonnées de la position de l'objet
     * @param velocity Vitesse initiale de déplacement de l'objet
     * @param color    Couleur à utiliser pour afficher la sphère
     */
    public Body(String name, MacroObject root, double mass, double radius, Vector3D position, Vector3D velocity,
            Color color) {
        super(name, root, mass, radius, position, velocity);
        this.color = color;
        // Initialisation des paramètres de la sphère
        this.sphere = new Sphere(radius);
        this.sphere.setTranslateX(position.getX());
        this.sphere.setTranslateY(position.getY());
        this.sphere.setTranslateZ(position.getZ());
        PhongMaterial ph = new PhongMaterial();
        ph.setDiffuseColor(color);
        this.sphere.setMaterial(ph);
    }

    @Override
    public String toString() {
        return "Body " + this.ObjectID + super.toString();
    }

    /**
     * Permet de récupérer la couleur de la sphère.
     * 
     * @return Couleur
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Permet de changer la couleur de la sphère.
     * 
     * @param newColor Nouvelle couleur
     */
    public void setColor(Color newColor) {
        this.color = newColor;
        PhongMaterial ph = new PhongMaterial();
        ph.setDiffuseColor(newColor);
        this.sphere.setMaterial(ph);
    }

    /**
     * Permet de récupérer la sphère 3D.
     * 
     * @return Sphère 3D
     */
    public Sphere getSphere() {
        return this.sphere;
    }

    /**
     * Permet de récupérer la position de l'objet sur l'axe des abscisses.
     * 
     * @return Position sur l'axe des X
     */
    public double getXPosition() {
        return this.getPosition().getX();
    }

    /**
     * Permet de récupérer la position de l'objet sur l'axe des ordonnées.
     * 
     * @return Position sur l'axe des Y
     */
    public double getYPosition() {
        return this.getPosition().getY();
    }

    /**
     * Permet de récupérer la position de l'objet sur l'axe de la profondeur.
     * 
     * @return Position sur l'axe des Z
     */
    public double getZPosition() {
        return this.getPosition().getZ();
    }

    /**
     * Permet de remplacer la position de l'objet sur l'axe des abscisses.
     * 
     * @param newValue Nouvelle valeur
     */
    public void setXPosition(double newValue) {
        this.position.setX(newValue);
    }

    /**
     * Permet de remplacer la position de l'objet sur l'axe des ordonnées.
     * 
     * @param newValue Nouvelle valeur
     */
    public void setYPosition(double newValue) {
        this.position.setY(newValue);
    }

    /**
     * Permet de remplacer la position de l'objet sur l'axe de la profondeur.
     * 
     * @param newValue Nouvelle valeur
     */
    public void setZPosition(double newValue) {
        this.position.setZ(newValue);
    }

    /**
     * Permet de récupérer la vélocité de l'objet sur l'axe des abscisses.
     * 
     * @return Vélocité sur l'axe des X
     */
    public double getXVelocity() {
        return this.getVelocity().getX();
    }

    /**
     * Permet de récupérer la vélocité de l'objet sur l'axe des ordonnées.
     * 
     * @return Vélocité sur l'axe des Y
     */
    public double getYVelocity() {
        return this.getVelocity().getY();
    }

    /**
     * Permet de récupérer la vélocité de l'objet sur l'axe de la profondeur.
     * 
     * @return Vélocité sur l'axe des Z
     */
    public double getZVelocity() {
        return this.getVelocity().getZ();
    }

    /**
     * Permet de remplacer la vélocité de l'objet sur l'axe des abscisses.
     * 
     * @param newValue Nouvelle valeur
     */
    public void setXVelocity(double newValue) {
        this.velocity.setX(newValue);
    }

    /**
     * Permet de remplacer la vélocité de l'objet sur l'axe des ordonnées.
     * 
     * @param newValue Nouvelle valeur
     */
    public void setYVelocity(double newValue) {
        this.velocity.setY(newValue);
    }

    /**
     * Permet de remplacer la vélocité de l'objet sur l'axe de la profondeur.
     * 
     * @param newValue Nouvelle valeur
     */
    public void setZVelocity(double newValue) {
        this.velocity.setZ(newValue);
    }

    /**
     * Permet de récupérer tous les attributs importants du corps dans une chaîne de
     * caractères. Organisé comme ceci :
     * <ol>
     * <li>nom ;</li>
     * <li>masse ;</li>
     * <li>rayon ;</li>
     * <li>position ;</li>
     * <li>vélocité ;</li>
     * <li>couleur.</li>
     * </ol>
     * 
     * @return Chaîne de caractère résumant les attributs du corps
     */
    public String getAllData() {
        String nameFormated = this.name.replace(" ", "%20");
        String colorString = "(" + this.color.getRed() + "," + this.color.getGreen() + "," + this.color.getBlue() + ","
                + this.color.getOpacity() + ")";
        return nameFormated + " " + this.mass + " " + this.radius + " " + this.position + " " + this.velocity + " "
                + colorString;
    }

    /**
     * Permet de mettre à jour la position et le rayon de la sphère.
     */
    private void move() {
        sphere.setTranslateX(this.getXPosition());
        sphere.setTranslateY(this.getYPosition());
        sphere.setTranslateZ(this.getZPosition());
        sphere.setRadius(this.radius);
    }

    /**
     * Permet de faire simuler l'objet (simulation basique newtonnienne).
     * 
     * @param allObjects Liste contenant tous les objets
     * @param deltaTime  Durée d'avancement dans le temps
     */
    @Override
    public void simulate(List<GenericObject> allObjects, double deltaTime) {
        super.simulate(allObjects, deltaTime); // Simule l'objet
        this.move(); // Met à jour les caractéristiques visuelles de l'ojet
    }

    /**
     * Permet de faire simuler l'objet (simulation de Barnes-Hut).
     * 
     * @param deltaTime Durée d'avancement dans le temps
     */
    @Override
    public void simulate(double deltaTime) {
        super.simulate(deltaTime); // Simule l'objet
        this.move(); // Met à jour les caractéristiques visuelles de l'ojet
    }
}