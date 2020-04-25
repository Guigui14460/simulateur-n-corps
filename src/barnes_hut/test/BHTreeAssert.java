package barnes_hut.test;

import math_physics.math.*;
import math_physics.physics.*;
import barnes_hut.*;

/**
 * Classe de test qui permet de vérifier les méthodes de la classe
 * <code>BHTree</code>.
 */
public class BHTreeAssert {
    /**
     * Teste si le noeud de l'arbre n'a pas de fils.
     * 
     * @param tree Arbre/Noeud de la l'arbre
     */
    private static void testHasNoSubTree(BHTree tree) {
        assert !tree.boxDivided() : "L'arbre ne devrait pas avoir de fils";
    }

    /**
     * Teste si le noeud de l'arbre a des noeuds fils.
     * 
     * @param tree Arbre/Noeud de la l'arbre
     */
    private static void testHasSubTree(BHTree tree) {
        assert tree.boxDivided() : "L'arbre devrait avoir des fils";
    }

    /**
     * Teste si la formation de l'arbre pour 2 objets est correcte.
     */
    private static void testWith2Objects() {
        MacroObject object = new MacroObject("Test", null, 1, 1, new Vector3D(-9, 1, 7), new Vector3D());
        BHTree tree = new BHTree(new Octal(new Vector3D(), 1000, 1000, 1000));
        testHasNoSubTree(tree);
        tree.insertion(object);
        testHasNoSubTree(tree);
        // System.out.println(tree);

        MacroObject object2 = new MacroObject("Test 2", null, 1, 1, new Vector3D(100, 0, -9), new Vector3D());
        tree.insertion(object2);
        testHasSubTree(tree);
        // System.out.println(tree);

        tree.updateForceAboutObject(object);
        tree.updateForceAboutObject(object2);
        object.simulate(1);
        object2.simulate(1);
    }

    /**
     * Teste si la formation de l'arbre pour une liste d'objets aléatoires est
     * correcte.
     */
    private static void testWithListObjects() {
        Simulator simulator = new Simulator(10, 1, 1, 100, 0);
        BHTree tree = new BHTree(new Octal(new Vector3D(), 1000, 1000, 1000));
        for (GenericObject o : simulator.getBodies()) {
            tree.insertion(o);
        }
        testHasSubTree(tree);
        // System.out.println(tree);

        for (GenericObject o : simulator.getBodies()) {
            tree.updateForceAboutObject(o);
        }
        for (GenericObject o : simulator.getBodies()) {
            o.simulate(1);
        }
    }

    /**
     * Méthode principale exécutable qui lance tous les tests.
     * 
     * @param args Arguments donnés au fichier
     */
    public static void main(String[] args) {
        System.out.println("Test ==> BHTREE");
        testWith2Objects();
        testWithListObjects();
    }
}