package math_physics.physics;

import java.util.*;

import math_physics.math.*;

/**
 * Classe permettant de stocker des formules physiques.
 */
public class Formula {
    /**
     * Permet de calculer la force exercée sur un corps B par un corps A.
     * 
     * @param massA Masse de l'objet A (en kg)
     * @param massB Masse de l'objet B (en kg)
     * @param d     Distance entre les 2 objets (en m)
     * @return Force de A sur B (en N)
     */
    public double newtonGravitation(double massA, double massB, double d) {
        return Constants.G * massA * massB / (d * d);
    }

    /**
     * Permet de calculer la force exercée sur un corps A par un corps B à l'aide de
     * vecteurs.
     * 
     * @param massA       Masse de l'objet A (en kg)
     * @param massB       Masse de l'objet B (en kg)
     * @param unitVectorA Coordonnées vectorielles de l'objet A (en m)
     * @param unitVectorB Coordonnées vectorielles de l'objet B (en m)
     * @return Force de B sur A sous forme vectorielle (en N)
     */
    public Vector3D newtonGravitationVectorForm(double massA, double massB, Vector3D unitVectorA,
            Vector3D unitVectorB) {
        Vector3D rAB = unitVectorA.minus(unitVectorB); // Distance entre les deux vecteurs
        double d = unitVectorA.distanceFromOtherVector(unitVectorB); // Norme de la distance entre les deux vecteurs
        double f = Constants.G * massA * massB / (d * d * d);
        return rAB.product(f);
    }

    /**
     * Permet de calculer l'accélération d'un objet par rapport aux autres masses
     * qui intéragissent avec ce même objet.
     * 
     * @param object     Objet que l'on veut calculer l'accélération
     * @param allObjects Liste des objets de l'espace simulé
     * @return Vecteur accélération de l'objet souhaité (en m/s^(-2))
     */
    public Vector3D accelerationCalculus(GenericObject object, List<GenericObject> allObjects) {
        Vector3D sumForces = new Vector3D();
        // Somme des forces exercées par les autres objets
        for (GenericObject o : allObjects) {
            if (object != o) {
                Vector3D rAB = object.getPosition().minus(o.getPosition()); // Distance entre les deux vecteurs
                double d = object.getPosition().distanceFromOtherVector(o.getPosition()); // Norme de la distance entre
                                                                                          // les deux vecteurs
                double f = o.getMass() / (d * d * d);
                sumForces = sumForces.plus(rAB.product(f));
            }
        }
        return sumForces.product(-Constants.G);
    }

    /**
     * Permet d'obtenir le centre de gravité de la masse entre deux objets.
     * 
     * @param massA     Masse de l'objet A
     * @param massB     Masse de l'objet B
     * @param positionA Position de l'objet A
     * @param positionB Position de l'objet B
     * @return Centre de gravité
     */
    public CenterOfMass centerOfMass(double massA, double massB, Vector3D positionA, Vector3D positionB) {
        double sumMass = massA + massB;
        return new CenterOfMass(sumMass, ((positionA.product(massA)).plus(positionB.product(massB))).divide(sumMass));
    }

    /**
     * Permet d'obtenir le centre de gravité de la masse entre tous les objets
     * appartenant à la liste
     * 
     * @param allObjectMass     Liste contenant les masses de tous les objets
     * @param allObjectPosition Liste contenant les positions de tous les objets
     * @return Centre de gravité
     */
    public CenterOfMass centerOfMass(List<Double> allObjectMass, List<Vector3D> allObjectPosition) {
        Vector3D sum = new Vector3D();
        double allMass = 0;
        for (int i = 0; i < allObjectMass.size(); i++) {
            allMass += allObjectMass.get(i);
            sum = sum.plus(allObjectPosition.get(i).product(allObjectMass.get(i)));
        }
        return new CenterOfMass(allMass, sum.divide(allMass));
    }
}