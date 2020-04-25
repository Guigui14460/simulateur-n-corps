package barnes_hut.test;

import java.util.*;

import math_physics.math.*;
import math_physics.physics.*;
import math_physics.physics.test.*;
import barnes_hut.*;

/**
 * Classe de test qui permet de vérifier les méthodes de la classe
 * <code>MacroObject</code> dans le contexte d'une simulation de Barnes-Hut.
 */
public class BHMacroObjectAssert extends MacroObjectAssert {
    /**
     * Méthode principale exécutable qui lance tous les tests.
     * 
     * @param args Arguments donnés au fichier
     */
    public static void main(String[] args) {
        System.out.println("Test ==> BARNES-HUT_MACROOBJECT");
        double dt = 1;

        testDefaultAttributes(new MacroObject("Test", null, 1e4, 10, new Vector3D(500, 300, -200), velocity.copy()));

        List<GenericObject> allObjects = new ArrayList<>();
        allObjects.add(new MacroObject("Test", null, 1e4, 10, new Vector3D(500, 300, -200), velocity.copy()));
        Octal oct = new Octal(new Vector3D(), 5_000 * 2, 5_000 * 2, 5_000 * 2);
        BHTree tree = new BHTree(oct);
        for (GenericObject body : allObjects) {
            if (oct.contains(body)) {
                tree.insertion(body);
            }
        }
        for (GenericObject body : allObjects) {
            body.resetForce();
            tree.updateForceAboutObject(body);
        }
        for (GenericObject body : allObjects) {
            body.simulate(dt);
        }
        testSimulateOneBody(allObjects, dt);

        List<GenericObject> allObjects1 = new ArrayList<>();
        allObjects1.add(new MacroObject("Test", null, 1e4, 10, new Vector3D(500, 300, -200), velocity.copy()));
        allObjects1.add(new MacroObject("Test2", null, 1e5, 10, Constants.ORIGIN, Constants.ORIGIN));
        Octal oct1 = new Octal(new Vector3D(), 5_000, 5_000, 5_000);
        BHTree tree1 = new BHTree(oct1);
        for (GenericObject body : allObjects1) {
            tree1.insertion(body);
        }
        for (GenericObject body : allObjects1) {
            body.resetForce();
            tree1.updateForceAboutObject(body);
        }
        for (GenericObject body : allObjects1) {
            body.simulate(dt);
        }
        // testSimulateTwoBodies(allObjects1, dt);

        List<GenericObject> allObjects2 = new ArrayList<>();
        allObjects2.add(new MacroObject("Test", null, 1e4, 10, new Vector3D(500, 300, -200), velocity.copy()));
        allObjects2.add(new MacroObject("Test2", null, 1e5, 10, Constants.ORIGIN, Constants.ORIGIN));
        allObjects2.add(new MacroObject("Test3", null, 1e2, 10, new Vector3D(60, -100, -50), new Vector3D(5, 23, 15)));
        allObjects2.add(
                new MacroObject("Giant attractor", null, 1e10, 10, new Vector3D(-100, -100, -100), Constants.ORIGIN));
        Octal oct2 = new Octal(new Vector3D(), 5_000 * 2, 5_000 * 2, 5_000 * 2);
        BHTree tree2 = new BHTree(oct2);
        for (GenericObject body : allObjects2) {
            if (oct2.contains(body)) {
                tree2.insertion(body);
            }
        }
        for (GenericObject body : allObjects2) {
            body.resetForce();
            tree2.updateForceAboutObject(body);
        }
        for (GenericObject body : allObjects2) {
            body.simulate(dt);
        }
        // testSimulateMultipleBodies(allObjects2, dt);
    }
}