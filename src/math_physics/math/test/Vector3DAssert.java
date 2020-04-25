package math_physics.math.test;

import math_physics.math.*;

/**
 * Classe de test permettant de vérifier si l'implémentation des vecteurs est
 * bonne.
 */
public class Vector3DAssert {
    /**
     * Teste l'égalité de deux objet <em>Vector3D</em> différents mais de contenu
     * égaux s'il sont bien égaux.
     */
    private static void testEqualityVectors() {
        Vector3D a = new Vector3D(1, 1, 1);
        Vector3D b = new Vector3D();
        b.setValue(-95, 1, 0);
        b.setValue(975321655, 2, 0);
        Vector3D c = b.plus(a);
        Vector3D d = new Vector3D(1, 0, 0);
        assert !c.equals(d) : "Le contenu des deux vecteurs devraient être différents";
    }

    /**
     * Teste l'addition entre deux vecteurs.
     */
    private static void testAdditionVectors() {
        Vector3D a = new Vector3D(1, 1, 1);
        Vector3D b = new Vector3D();
        b.setValue(-95, 1, 0);
        b.setValue(975321655, 2, 0);
        Vector3D c = b.plus(a);
        assert c.equals(new Vector3D(1, -94, 975321656)) : "Le contenu des deux vecteurs devrait être égal";
    }

    /**
     * Teste le produit entre un vecteur et un nombre.
     */
    private static void testProduct() {
        Vector3D a = new Vector3D(1, 1, 1);
        assert a.product(10).equals(new Vector3D(10, 10, 10)) : "Les vecteurs devraient être égaux";
    }

    /**
     * Teste si les distances entre vecteur/origine et vecteur/vecteur sont égaux.
     */
    private static void testDistances() {
        Vector3D e = new Vector3D(10, 50, 100);
        assert e.distanceFromOrigin() == e.product(2)
                .distanceFromOtherVector(e) : "Les distances devraient être les mêmes";
    }

    /**
     * Méthode principale exécutable qui lance tous les tests.
     * 
     * @param args Arguments donnés au fichier
     */
    public static void main(String[] args) {
        System.out.println("Test ==> VECTOR3D");
        testEqualityVectors();
        testAdditionVectors();
        testProduct();
        testDistances();
    }
}