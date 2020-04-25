package nbody;

import java.util.*;

import javafx.scene.paint.*;
import javafx.scene.shape.*;

import math_physics.math.*;
import math_physics.physics.*;
import barnes_hut.*;

/**
 * Classe simulant N corps intéragissant gravitationnellement (utilisation de la
 * simulation de Barnes-Hut possible).
 */
public class NBodySimulator extends Simulator {
    /**
     * Correspond au type de simulation à utiliser. false : utilisation de la
     * simulation newtonienne basique (précise mais lente). true : utilisation de la
     * simulation de Barnes-Hut (rapide mais moins précise).
     */
    private boolean useBarnesHutSimulation = true;

    /**
     * Distance maximale du centre de la simulation.
     */
    private double maxDistance = 1000;

    /**
     * Correspond à une valeur permettant la vérification de la distance d'un objet
     * au centre de gravité du noeud-arbre dans l'algorithme de Barnes-Hut.
     */
    private double theta = 0.5;

    /**
     * Constructeur de la classe NBodySimulator.
     */
    public NBodySimulator() {
    }

    /**
     * Constructeur de la classe NBodySimulator.
     * 
     * @param numberBodyInitial Entier représentant le nombre de corps initial à
     *                          créer
     * @param massMax           Masse maximum pour créer les objets
     * @param radius            Rayon de la sphère pour l'affichage
     * @param maxDistance       Distance initiale maximale pour placer les objets
     * @param maxVelocity       Vitesse initiale maximale pour déplacer les objets
     * @param color             Couleur à afficher sur la surface de sphère
     */
    public NBodySimulator(int numberBodyInitial, double massMax, double radius, double maxDistance, double maxVelocity,
            Color color) {
        if (maxDistance < 0) { // Si la distance est négative (impossible en mathématiques)
            maxDistance *= -1; // On prend son opposé
        }
        this.maxDistance = maxDistance;
        // Ajout de 'numberBodyInitial' corps aléatoirement
        for (int i = 0; i < numberBodyInitial; i++) {
            this.addRandomBody(massMax, radius, maxDistance, maxVelocity, color);
        }
    }

    /**
     * Permet de récupérer la valeur de la distance maximale.
     * 
     * @return Distance maximale
     */
    public double getMaxDistance() {
        return this.maxDistance;
    }

    /**
     * Permet de remplacer la valeur de la distance maximale.
     * 
     * @param newMaxDistance Nouvelle valeur
     */
    public void setMaxDistance(double newMaxDistance) {
        this.maxDistance = newMaxDistance;
    }

    /**
     * Permet de savoir quel type de simulation est en cours d'utilisation.
     * 
     * @return Booléen représentant si la simulation de Barnes-Hut est utilisée
     */
    public boolean getUseBarnesHutSimulation() {
        return this.useBarnesHutSimulation;
    }

    /**
     * Permet de changer manuellement le type de simulation en cours d'utilisation.
     * 
     * @param newValue true : simulation de Barnes-Hut, false : simulation basique
     */
    public void setUseBarnesHutSimulation(boolean newValue) {
        this.useBarnesHutSimulation = newValue;
    }

    /**
     * Permet de changer automatiquement le type de simulation en cours
     * d'utilisation.
     */
    public void toggleUseBarnesHutSimulation() {
        this.useBarnesHutSimulation = !this.useBarnesHutSimulation;
    }

    /**
     * Permet de récupérer le paramètre Théta utilisé dans l'algorithme de
     * Barnes-Hut.
     * 
     * @return Théta
     */
    public double getTheta() {
        return this.theta;
    }

    /**
     * Permet de changer la valeur du paramètre Théta.
     * 
     * @param newTheta Nouvelle valeur
     */
    public void setTheta(double newTheta) {
        this.theta = newTheta;
    }

    /**
     * Permet d'ajouter un objet créé aléatoirement à la liste des objets
     * appartenant au simulateur.
     * 
     * @param massMax     Masse maximum pour créer les objets
     * @param radius      Rayon de la sphère pour l'affichage
     * @param maxDistance Distance initiale maximale pour placer les objets
     * @param maxVelocity Vitesse initiale maximale pour déplacer les objets
     * @param color       Couleur à afficher sur la surface de sphère
     */
    public void addRandomBody(double massMax, double radius, double maxDistance, double maxVelocity, Color color) {
        Vector3D position = this.generateVector3D(maxDistance);
        while (position.distanceFromOtherVector(this.origin) > maxDistance) {
            position = this.generateVector3D(maxDistance);
        }
        this.addBody("Body", this.generateDouble(0, massMax), radius, position, this.generateVector3D(maxVelocity),
                color);
    }

    /**
     * Permet d'ajouter un objet à la liste des objets appartenant au simulateur.
     * 
     * @param name     Nom de l'objet
     * @param mass     Masse totale de l'objet
     * @param radius   Rayon de l'objet
     * @param position Coordonnées de la position de l'objet
     * @param velocity Vitesse initiale de déplacement de l'objet
     * @param color    Couleur à afficher sur la surface de sphère
     */
    public void addBody(String name, double mass, double radius, Vector3D position, Vector3D velocity, Color color) {
        this.addBody(new Body(name, mass, radius, position, velocity, color));
    }

    /**
     * Permet d'ajouter un objet à la liste des objets appartenant au simulateur.
     * 
     * @param bodyToAdd Corps à ajouter à la liste
     */
    public void addBody(Body bodyToAdd) {
        this.bodies.add(bodyToAdd);
        this.numberBody++;
    }

    /**
     * Permet d'ajouter un objet à la liste des objets appartenant au simulateur.
     * 
     * @param bodiesToAdd Collection de corps à ajouter à la liste
     */
    public void addBodies(Collection<Body> bodiesToAdd) {
        this.bodies.addAll(bodiesToAdd);
        this.numberBody += bodiesToAdd.size();
    }

    /**
     * Permet d'ajouter un objet à la liste des objets appartenant au simulateur.
     * 
     * @param bodiesToAdd Suite de corps à ajouter à la liste
     */
    public void addBodies(Body... bodiesToAdd) {
        for (Body body : bodiesToAdd) {
            this.addBody(body);
        }
    }

    /**
     * Permet de retrouver un objet dans la simulation.
     * 
     * @param name Identifiant unique de l'objet recherché
     * @return Objet recherché
     */
    public Body search(String name) {
        for (GenericObject object : this.bodies) {
            Body body = (Body) object;
            if (body.getObjectName().equals(name)) {
                return body; // L'objet est trouvé
            }
        }
        return null; // L'objet n'a pas été trouvé
    }

    /**
     * Permet de supprimer un corps de la simulation.
     * 
     * @param instance Instance du corps que l'on veut retirer
     * @return Sphère associée au corps (enlever manuellement de la simulation)
     */
    public Sphere removeBody(Body instance) {
        Sphere sphere = instance.getSphere();
        this.bodies.remove(instance);
        this.numberBody--;
        return sphere;
    }

    /**
     * Permet de simuler toutes les intéractions des objets.
     */
    public void simulateAllBodies() {
        if (!this.useBarnesHutSimulation) {
            // Simule tous les objets
            for (GenericObject body : this.bodies) {
                body.simulate(this.bodies, this.deltaTime);
            }
        } else {
            Octal oct = new Octal(this.origin, maxDistance * 4, maxDistance * 4, maxDistance * 4);
            BHTree tree = new BHTree(oct);
            tree.setTheta(this.theta);

            // Construction des noeuds l'arbre de Barnes-Hut
            for (GenericObject body : this.bodies) {
                tree.insertion(body);
            }
            // Mise à jour de la force, de l'accélération, de la vitesse et de la position
            for (GenericObject body : this.bodies) {
                body.resetForce();
                tree.updateForceAboutObject(body);
                body.simulate(this.deltaTime);
            }
        }
    }

    /**
     * Méthode principale.
     * 
     * @param args Arguments passés dans le terminal
     */
    public static void main(String[] args) {
        int N = 5000;
        NBodySimulator simulator = new NBodySimulator(N, 10, 1, 100, 5, Color.WHITE);
        simulator.setTheta(2);
        // System.out.println(simulator.getTotalMass());
        long startTime = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            System.out.println("Tour " + (i + 1));
            simulator.simulateAllBodies();
        }
        // System.out.println(simulator.getBodies());
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1e9;
        System.out.println(
                "Durée d'exécution pour " + N + " corps et 10 tours de simulation : " + duration + " secondes.");
    }
}