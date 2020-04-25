package math_physics.physics;

import java.util.*;

import math_physics.math.*;

/**
 * Représente un objet de l'univers quelconque.
 */
public abstract class GenericObject {
    /**
     * Correspond aux nombres d'objets qui ont été créés.
     */
    public static int numberGenericObject = 0;

    /**
     * Correspond à l'identifiant unique de l'objet.
     */
    protected final int ObjectID;

    /**
     * Correspond à la masse totale de l'objet
     */
    protected double mass;

    /**
     * Correspond au rayon de l'objet.
     */
    protected double radius = 1;

    /**
     * Correspond aux coordonnées spatiales de l'objet sous forme vectorielle.
     */
    protected Vector3D position;

    /**
     * Correspond aux coordonnées de vitesses de l'objet sous forme vectorielle.
     */
    protected Vector3D velocity;

    /**
     * Correspond aux coordonnées d'accélération de l'objet sous forme vectorielle.
     */
    protected Vector3D acceleration = new Vector3D();

    /**
     * Correspond à la force exercée sur l'objet sous forme vectorielle.
     */
    protected Vector3D force = new Vector3D();

    /**
     * Constructeur d'un objet astronomique.
     * 
     * @param mass     Masse totale de l'objet
     * @param radius   Rayon de l'objet
     * @param position Coordonnées de la position de l'objet
     * @param velocity Vitesse initiale de déplacement de l'objet
     */
    public GenericObject(double mass, double radius, Vector3D position, Vector3D velocity) {
        if (mass < 0 || radius < 0) {
            throw new RuntimeException("La masse et le rayon doivent être positifs !");
        }
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.velocity = velocity;
        GenericObject.numberGenericObject++;
        this.ObjectID = GenericObject.numberGenericObject;
    }

    @Override
    public String toString() {
        return this.ObjectID + " : " + this.mass;
    }

    /**
     * Permet d'obtenir l'identifiant de l'objet.
     * 
     * @return ID
     */
    public int getObjectID() {
        return this.ObjectID;
    }

    /**
     * Permet de récupérer la masse de l'objet
     * 
     * @return Masse de l'objet
     */
    public double getMass() {
        return this.mass;
    }

    /**
     * Permet de mettre à jour la masse de l'objet.
     * 
     * @param newMass Nouvelle masse de l'objet
     */
    public void setMass(double newMass) {
        this.mass = newMass;
    }

    /**
     * Permet de récupérer le rayon de l'objet.
     * 
     * @return Rayon de l'objet
     */
    public double getRadius() {
        return this.radius;
    }

    /**
     * Permet de mettre à jour le rayon de l'objet.
     * 
     * @param newRadius Nouveau rayon de l'objet
     */
    public void setRadius(double newRadius) {
        this.radius = newRadius;
    }

    /**
     * Récupère le vecteur position de l'objet.
     * 
     * @return Coordonnées de l'objet
     */
    public Vector3D getPosition() {
        return this.position;
    }

    /**
     * Permet de mettre à jour la position de l'objet.
     * 
     * @param newPosition Nouvelle position
     */
    public void setPosition(Vector3D newPosition) {
        this.position = newPosition;
    }

    /**
     * Récupère le vecteur vitesse de l'objet.
     * 
     * @return Vitesse de l'objet sur tous les axes 3D
     */
    public Vector3D getVelocity() {
        return this.velocity;
    }

    /**
     * Permet de mettre à jour la vélocité de l'objet.
     * 
     * @param newVelocity Nouvelle vitesse
     */
    public void setVelocity(Vector3D newVelocity) {
        this.velocity = newVelocity;
    }

    /**
     * Récupère le vecteur accélération de l'objet.
     * 
     * @return Accélération de l'objet sur tous les axes 3D
     */
    public Vector3D getAcceleration() {
        return this.acceleration;
    }

    /**
     * Permet de mettre à jour l'accélération de l'objet.
     * 
     * @param newAcceleration Nouvelle accélération
     */
    public void setAcceleration(Vector3D newAcceleration) {
        this.acceleration = newAcceleration;
    }

    /**
     * Permet de récupérer le vecteur force de l'objet.
     * 
     * @return Force exercée sur l'objet sur tous les axes 3D
     */
    public Vector3D getForce() {
        return this.force;
    }

    /**
     * Permet de mettre à jour la force exercée sur l'objet.
     * 
     * @param newForce Nouvelle force
     */
    public void setForce(Vector3D newForce) {
        this.force = newForce;
    }

    /**
     * Permet de réinitialiser la force exercée sur l'objet.
     */
    public void resetForce() {
        this.force = new Vector3D(); // == new Vector3D(0, 0, 0)
    }

    /**
     * Permet d'ajouter la force nette entre cet objet simulé et un autre objet au
     * vecteur force de l'objet simulé.
     * 
     * @param body Corps qui exerce sa force sur cet objet simulé
     */
    public void addForce(GenericObject body) {
        this.force = this.force
                .plus(new Formula().newtonGravitationVectorForm(body.mass, this.mass, body.position, this.position));
    }

    /**
     * Permet d'ajouter la force nette entre cet objet simulé et un centre de
     * gravité au vecteur force de l'objet simulé.
     * 
     * @param body Centre de gravité qui exerce sa force sur cet objet simulé
     */
    public void addForce(CenterOfMass body) {
        this.force = this.force
                .plus(new Formula().newtonGravitationVectorForm(body.mass, this.mass, body.position, this.position));
    }

    /**
     * Permet de calculer les nouvelles accélérations de l'objet.
     * 
     * @param allObjects Liste contenant tous les objets
     */
    public void computeNewAccelerations(List<GenericObject> allObjects) {
        this.acceleration = new Formula().accelerationCalculus(this, allObjects);
    }

    /**
     * Permet de calculer les nouvelles vitesses de l'objet.
     * 
     * @param deltaTime Temps écoulé
     */
    public void computeNewVelocities(double deltaTime) {
        this.velocity = this.velocity.plus(this.acceleration.product(deltaTime)); // vf = vi + a*dt
    }

    /**
     * Permet de calculer les nouvelles positions de l'objet.
     * 
     * @param deltaTime Temps écoulé
     */
    public void computeNewPositions(double deltaTime) {
        this.position = this.position.plus(this.velocity.product(deltaTime)); // xf = xi + v*t
    }

    /**
     * Permet de simuler le déplacement des objets dans un espace 3D en fonction de
     * masses et des positions de tous les autres objets. La simulation met à jour
     * les données directement (calcul de l'accélération basique).
     * 
     * @param allObjects Liste contenant tous les objets
     * @param deltaTime  Temps écoulé
     */
    public void simulate(List<GenericObject> allObjects, double deltaTime) {
        this.computeNewAccelerations(allObjects); // Calcule et remplace l'accélération
        this.computeNewVelocities(deltaTime); // Met à jour la position
        this.computeNewPositions(deltaTime); // Met à jour la vitesse
    }

    /**
     * Permet de simuler le déplacement des objets dans un espace 3D en fonction de
     * masses et des positions de tous les autres objets. La simulation met à jour
     * les données directement (calcul de l'accélération directement à partir de la
     * force).
     * 
     * @param deltaTime Temps écoulé
     */
    public void simulate(double deltaTime) {
        this.acceleration = this.force.divide(this.mass); // Calcule et remplace l'accélération
        this.computeNewVelocities(deltaTime); // Met à jour la vitesse
        this.computeNewPositions(deltaTime); // Met à jour la position
    }
}