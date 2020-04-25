package math_physics.physics.test;

import java.util.*;

import math_physics.math.*;
import math_physics.physics.*;

/**
 * Classe de test qui vérifie si un objet seul possède bien une vitesse dans une
 * direction rectiligne.
 */
public class ToInfinite {
    /**
     * Méthode principale.
     * 
     * @param args Arguments passés en paramètre d'appel du fichier
     */
    public static void main(String[] args) {
        List<GenericObject> list = new ArrayList<>();
        GenericObject test = new Particule(1e4, 10, new Vector3D(500, 300, -200), new Vector3D(100, -50000, 0));
        list.add(test);

        // Test d'un objet partant vers l'infini
        Vector3D initialPositionOfTest = test.getPosition();
        for (int i = 0; i < 100000; i++) {
            test.simulate(list, 3600 * 24 * 365);
        }
        Vector3D finalPositionOfTest = test.getPosition();

        // On regarde de combien l'objet a bougé
        System.out.println("Difference entre la position initiale et la position finale :");
        finalPositionOfTest.minus(initialPositionOfTest).show();
        System.out.println(finalPositionOfTest.distanceFromOtherVector(initialPositionOfTest) / 1000 + "km");
    }
}
