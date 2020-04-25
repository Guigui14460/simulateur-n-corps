package math_physics.physics;

import java.util.*;

import math_physics.math.*;

/**
 * Classe permettant de simuler un espace avec des corps en intéraction.
 * Utilisable en threads.
 */
public class Simulator extends Thread {
    /**
     * Vecteur représentant le centre de l'espace de simulation.
     */
    public Vector3D origin = new Vector3D(0, 0, 0);

    /**
     * Correspond au nombre de corps qui se trouve dans la simulation.
     */
    protected int numberBody = 0;

    /**
     * Générateur aléatoire.
     */
    protected Random generator = new Random();

    /**
     * Correspond à la liste contenant tous les corps du simulateur.
     */
    protected List<GenericObject> bodies = new ArrayList<>();

    /**
     * Correspond au nombre de secondes écoulées lors de chaque lancement de
     * simulation.
     */
    protected double deltaTime = 1;

    /**
     * Constructeur de la classe Simulator.
     */
    public Simulator() {
    }

    /**
     * Constructeur de la classe Simulator.
     * 
     * @param numberBodyInitial Entier représentant le nombre de corps initial à
     *                          créer
     * @param massMax           Masse maximum pour créer les objets
     * @param radius            Rayon de la sphère pour l'affichage
     * @param maxDistance       Distance initiale maximale pour placer les objets
     * @param maxVelocity       Vitesse initiale maximale pour déplacer les objets
     */
    public Simulator(int numberBodyInitial, double massMax, double radius, double maxDistance, double maxVelocity) {
        if (maxDistance < 0) { // Si la distance est négative (impossible en mathématiques)
            maxDistance *= -1; // On prend son opposé
        }
        // Ajout de 'numberBodyInitial' corps aléatoirement
        for (int i = 0; i < numberBodyInitial; i++) {
            this.addRandomBody(massMax, radius, maxDistance, maxVelocity);
        }
    }

    /**
     * Permet de lancer la simulation sur un autre Thread.
     */
    @Override
    public void run() {
        this.simulateAllBodies(); // Simule tous les corps (1 itération)
    }

    /**
     * Permet de générer un nombre flottant de type 'double' aléatoirement entre
     * deux valeurs.
     * 
     * @param min Valeur minimale
     * @param max Valeur maximale
     * @return Valeur aléatoirement tirée entre <em>min</em> et <em>max</em>
     */
    protected double generateDouble(double min, double max) {
        return min + generator.nextDouble() * (max - min);
    }

    /**
     * Permet de générer aléatoirement un vecteur 3D.
     * 
     * @param max Valeur maximale
     * @return Vecteur contenant des valeurs aléatoirement tirées entre <em>0</em>
     *         et <em>max</em>
     */
    protected Vector3D generateVector3D(double max) {
        return new Vector3D(this.generateDouble(-max, max), this.generateDouble(-max, max),
                this.generateDouble(-max, max));
    }

    /**
     * Permet de récupérer le nombre de corps de la simulation.
     * 
     * @return Nombre de corps simulé
     */
    public int getNumberBody() {
        return this.numberBody;
    }

    /**
     * Permet de récupérer la liste de tous les objets appartenant à la simulation.
     * 
     * @return Liste des objets
     */
    public List<GenericObject> getBodies() {
        return this.bodies;
    }

    /**
     * Permet de récupérer le nombre de seconde que l'on écoule lors de chaque
     * lancement de simulation.
     * 
     * @return Nombre de secondes
     */
    public double getDeltaTime() {
        return this.deltaTime;
    }

    /**
     * Permet de remplacer l'ancienne valeur de l'attribut <em>deltaTime</em>.
     * 
     * @param newDeltaTime Nouvelle valeur
     */
    public void setDeltaTime(double newDeltaTime) {
        this.deltaTime = newDeltaTime;
    }

    /**
     * Permet d'ajouter un objet créé aléatoirement à la liste des objets
     * appartenant au simulateur.
     * 
     * @param massMax     Masse maximum pour créer les objets
     * @param radius      Rayon de la sphère pour l'affichage
     * @param maxDistance Distance initiale maximale pour placer les objets
     * @param maxVelocity Vitesse initiale maximale pour déplacer les objets
     */
    public void addRandomBody(double massMax, double radius, double maxDistance, double maxVelocity) {
        Vector3D position = this.generateVector3D(maxDistance);
        while (position.distanceFromOtherVector(this.origin) > maxDistance) {
            position = this.generateVector3D(maxDistance);
        }
        this.addBody(this.generateDouble(0, massMax), radius, position, this.generateVector3D(maxVelocity));
    }

    /**
     * Permet d'ajouter un objet à la liste des objets appartenant au simulateur.
     * 
     * @param mass     Masse totale de l'objet
     * @param radius   Rayon de l'objet
     * @param position Coordonnées de la position de l'objet
     * @param velocity Vitesse initiale de déplacement de l'objet
     */
    public void addBody(double mass, double radius, Vector3D position, Vector3D velocity) {
        this.addBody(new MacroObject(mass, radius, position, velocity));
    }

    /**
     * Permet d'ajouter un objet à la liste des objets appartenant au simulateur.
     * 
     * @param name     Nom de l'objet
     * @param mass     Masse totale de l'objet
     * @param radius   Rayon de l'objet
     * @param position Coordonnées de la position de l'objet
     * @param velocity Vitesse initiale de déplacement de l'objet
     */
    public void addBody(String name, double mass, double radius, Vector3D position, Vector3D velocity) {
        this.addBody(new MacroObject(name, null, mass, radius, position, velocity));
    }

    /**
     * Permet d'ajouter un objet à la liste des objets appartenant au simulateur.
     * 
     * @param object Objet
     */
    public void addBody(GenericObject object) {
        this.bodies.add(object);
        this.numberBody++;
    }

    /**
     * Permet de supprimer un corps de la simulation.
     * 
     * @param instance Instance du corps que l'on veut retirer
     */
    public void removeBody(GenericObject instance) {
        this.bodies.remove(instance);
    }

    /**
     * Permet de retrouver un objet dans la simulation.
     * 
     * @param ID Identifiant unique de l'objet recherché
     * @return Objet recherché
     */
    public GenericObject search(int ID) {
        for (GenericObject o : this.bodies) {
            if (o.ObjectID == ID) {
                return o;
            }
        }
        return null;
    }

    /**
     * Permet de récupérer la masse totale du système simulé.
     * 
     * @return Masse totale
     */
    public double getTotalMass() {
        double sum = 0;
        for (GenericObject o : this.bodies) {
            sum += o.getMass();
        }
        return sum;
    }

    /**
     * Permet de simuler toutes les intéractions des objets.
     */
    public void simulateAllBodies() {
        for (GenericObject o : this.bodies) {
            o.simulate(this.bodies, this.deltaTime);
        }
    }

    /**
     * Méthode principale.
     * 
     * @param args Arguments passés dans le terminal
     */
    public static void main(String[] args) {
        int N = 5000;
        Simulator simulator = new Simulator(N, 10, 1, 100, 5);
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