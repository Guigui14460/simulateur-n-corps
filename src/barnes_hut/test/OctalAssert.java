package barnes_hut.test;

import math_physics.math.*;
import math_physics.physics.*;
import barnes_hut.*;

/**
 * Classe de test qui permet de vérifier les méthodes de la classe
 * <code>Octal</code>.
 */
public class OctalAssert {
    /**
     * Teste si la boite ne peut pas contenir l'objet.
     * 
     * @param box    Boite
     * @param object Objet
     */
    private static void testNotContains(Octal box, GenericObject object) {
        assert !box.contains(object) : "La boite ne devrait pas contenir l'objet";
    }

    /**
     * Teste si la boite peut contenir l'objet.
     * 
     * @param box    Boite
     * @param object Objet
     */
    private static void testSubBoxContains(Octal box, GenericObject object) {
        assert box.contains(object) : "La boite devrait contenir l'objet";
        assert box.SWF().contains(object) : "La boite SWF doit contenir l'objet";
        assert !box.NEB().contains(object) : "La boite NEB ne doit pas contenir l'objet";
    }

    /**
     * Teste si les dimensions de la demi-boite sont bien exactes.
     * 
     * @param box Boite
     */
    private static void testHalfBox(Octal box) {
        assert box.getHalfBox()
                .equals(new Vector3D(500, 300, 100)) : "Le calcul de la moitié de la boite n'est pas correct";
    }

    /**
     * Teste si les dimensions du quart de la boite sont bien exactes.
     * 
     * @param box Boite
     */
    private static void testQuarterBox(Octal box) {
        assert box.getQuarterBox()
                .equals(new Vector3D(250, 150, 50)) : "Le calcul du quart de la boite n'est pas correct";
    }

    /**
     * Teste si le quart Nord-Est Arrière de la boite est bien fabriqué.
     * 
     * @param box Boite
     */
    private static void testNEB(Octal box) {
        Octal box2 = box.NEB();
        assert box.contains(box2.getCenter()) : "La boite doit contenir la sous-boite";
        assert box2.getDimensions().equals(new Vector3D(500, 300, 100)) : "Le calcul des dimensions est incorrect";
        assert box2.getCenter().equals(new Vector3D(250, 150, -50)) : "Le calcul du centre est incorrect";
    }

    /**
     * Méthode principale exécutable qui lance tous les tests.
     * 
     * @param args Arguments donnés au fichier
     */
    public static void main(String[] args) {
        System.out.println("Test ==> OCTAL");
        testNotContains(new Octal(new Vector3D(), new Vector3D(10, 10, 10)), new Particule(1, new Vector3D(-9, 1, 7)));
        testSubBoxContains(new Octal(new Vector3D(), new Vector3D(100, 100, 100)),
                new Particule(1, new Vector3D(-9, -1, 7)));
        testHalfBox(new Octal(new Vector3D(), 1000, 600, 200));
        testQuarterBox(new Octal(new Vector3D(), 1000, 600, 200));
        testNEB(new Octal(new Vector3D(), 1000, 600, 200));
    }
}