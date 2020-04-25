package math_physics.physics.test;

import java.util.*;

import math_physics.math.*;
import math_physics.physics.*;

/**
 * Classe de test qui permet de vérifier les méthodes de la classe
 * <code>MacroObject</code>.
 */
public class MacroObjectAssert {
    /**
     * Définition de la vélocité de base de l'objet.
     */
    protected static Vector3D velocity = new Vector3D(-40, 20, 5);

    /**
     * Teste les différents attributs de l'objet.
     * 
     * @param object Objet à tester
     */
    protected static void testDefaultAttributes(MacroObject object) {
        assert object.getMass() == 1e4;
        assert object.getPosition().equals(new Vector3D(500, 300, -200));
        assert !object.getVelocity().equals(new Vector3D(-40, -20, 5));
        assert object.getAcceleration().equals(Constants.ORIGIN);
    }

    /**
     * Teste la simulation d'un espace simulé avec un seul objet.
     * 
     * @param allObjects Liste contenant tous les objets de l'espace simulé
     * @param dt         Durée (en s) d'avancement dans le temps
     */
    protected static void testSimulateOneBody(List<GenericObject> allObjects, double dt) {
        GenericObject object = allObjects.get(0);

        assert object.getAcceleration().equals(Constants.ORIGIN) : "Il ne devraient pas subir d'accélération";
        assert object.getVelocity().equals(velocity) : "La vitesse doit être celle initiale";
        assert object.getPosition().equals(new Vector3D(500 + velocity.getX() * dt, 300 + velocity.getY() * dt,
                -200 + velocity.getZ() * dt)) : "L'objet ne se trouve pas à la position attendue";
    }

    /**
     * Teste la simulation d'un espace simulé avec deux objets.
     * 
     * @param allObjects Liste contenant tous les objets de l'espace simulé
     * @param dt         Durée (en s) d'avancement dans le temps
     */
    protected static void testSimulateTwoBodies(List<GenericObject> allObjects, double dt) {
        GenericObject object = allObjects.get(0);
        GenericObject object2 = allObjects.get(1);

        assert object.getAcceleration().getX() < 0 && object.getAcceleration().getY() < 0
                && object.getAcceleration().getZ() > 0 : "Le calcul de l'accélération n'est pas correct";
        assert object.getVelocity()
                .equals(velocity.plus(object.getAcceleration().product(dt))) : "La nouvelle vitesse n'est pas bonne";
        assert !object.getVelocity()
                .equals(new Vector3D(-40, 20, 5)) : "La nouvelle vitesse doit être différente de celle de départ";
        assert object.getVelocity().getX() < velocity.getX() && object.getVelocity().getY() < velocity.getY()
                && object.getVelocity().getZ() > velocity.getZ() : "Le calcul de la nouvelle vitesse est incorrect";
        assert !object.getPosition()
                .equals(new Vector3D(500, 300, -200)) : "La nouvelle position doit être différente de celle de départ";
        assert object.getPosition().getX() < 500 && object.getPosition().getY() > 300
                && object.getPosition().getZ() > -200 : "Le calcul de la nouvelle position est incorrect";

        assert object2.getMass() == 1e5 : "La masse ne doit pas être impactée";
        assert object2.getAcceleration().getX() > 0 && object2.getAcceleration().getY() > 0
                && object2.getAcceleration().getZ() < 0 : "Le calcul de l'accélération n'est pas correct";
        assert !object2.getVelocity()
                .equals(new Vector3D()) : "La nouvelle vitesse doit être différente de celle de départ (origine)";
        assert object2.getVelocity().getX() > 0 && object2.getVelocity().getY() > 0
                && object2.getVelocity().getZ() < 0 : "Le calcul de la nouvelle vitesse est incorrect";
        assert !object2.getPosition().equals(new Vector3D()) : "La nouvelle position ne pas être l'origine du repère";
        assert object2.getPosition().getX() > 0 && object2.getPosition().getY() > 0
                && object2.getPosition().getZ() < 0 : "Le calcul de la nouvelle position est incorrect";
    }

    /**
     * Teste la simulation d'un espace simulé avec plusieurs objets.
     * 
     * @param allObjects Liste contenant tous les objets de l'espace simulé
     * @param dt         Durée (en s) d'avancement dans le temps
     */
    protected static void testSimulateMultipleBodies(List<GenericObject> allObjects, double dt) {
        GenericObject object = allObjects.get(0);
        GenericObject object2 = allObjects.get(1);
        GenericObject object3 = allObjects.get(2);
        GenericObject object4 = allObjects.get(3);

        assert object.getMass() == 1e4 : "La masse ne doit pas être impactée";
        assert object.getAcceleration().getX() < 0 && object.getAcceleration().getY() < 0
                && object.getAcceleration().getZ() > 0 : "Le calcul de l'accélération n'est pas correct";
        assert !object.getVelocity().equals(Constants.ORIGIN) : "La vitesse ne doit pas être nulle";
        assert !object.getVelocity().equals(velocity) : "La vitesse ne doit pas être celle d'origine";
        assert object.getVelocity().getX() < velocity.getX() && object.getVelocity().getY() < velocity.getY()
                && object.getVelocity().getZ() > velocity.getZ() : "Le calcul de la nouvelle vitesse est incorrect";
        assert !object.getPosition().equals(Constants.ORIGIN) : "La nouvelle position ne pas être l'origine du repère";
        assert object.getPosition().getX() < 500 && object.getPosition().getY() > 300
                && object.getPosition().getZ() > -200 : "Le calcul de la nouvelle position est incorrect";

        assert object2.getMass() == 1e5 : "La masse ne doit pas être impactée";
        assert object2.getAcceleration().getX() < 0 && object2.getAcceleration().getY() < 0
                && object2.getAcceleration().getZ() < 0 : "Le calcul de l'accélération n'est pas correct";
        assert !object2.getVelocity().equals(Constants.ORIGIN) : "La vitesse ne doit pas être nulle(origine)";
        assert object2.getVelocity().getX() < 0 && object2.getVelocity().getY() < 0
                && object2.getVelocity().getZ() < 0 : "Le calcul de la nouvelle vitesse est incorrect";
        assert !object2.getPosition()
                .equals(Constants.ORIGIN) : "La nouvelle position ne pas être l'origine du repère (point d'origine)";
        assert object2.getPosition().getX() < 0 && object2.getPosition().getY() < 0
                && object2.getPosition().getZ() < 0 : "Le calcul de la nouvelle position est incorrect";

        assert object3.getMass() == 1e2 : "La masse ne doit pas être impactée";
        assert object3.getAcceleration().getX() < 0 && object3.getAcceleration().getY() >= 0
                && object3.getAcceleration().getZ() < 0 : "Le calcul de l'accélération n'est pas correct";
        assert !object3.getVelocity().equals(Constants.ORIGIN) : "La vitesse ne doit pas être nulle";
        assert object3.getVelocity().getX() < 5 && object3.getVelocity().getY() > 23
                && object3.getVelocity().getZ() < 15 : "Le calcul de la nouvelle vitesse est incorrect";
        assert !object3.getPosition().equals(Constants.ORIGIN) : "La nouvelle position ne pas être l'origine du repère";
        assert object3.getPosition().getX() > 60 && object3.getPosition().getY() > -100
                && object3.getPosition().getZ() > -50 : "Le calcul de la nouvelle position est incorrect";

        assert object4.getMass() == 1e10 : "La masse ne doit pas être impactée";
        assert object4.getAcceleration().getX() > 0 && object4.getAcceleration().getY() > 0
                && object4.getAcceleration().getZ() > 0 : "Le calcul de l'accélération n'est pas correct";
        assert !object4.getVelocity().equals(Constants.ORIGIN) : "La vitesse ne doit pas être nulle";
        assert object4.getVelocity().getX() > 0 && object4.getVelocity().getY() > 0
                && object4.getVelocity().getZ() > 0 : "Le calcul de la nouvelle vitesse est incorrect";
        assert !object4.getPosition().equals(Constants.ORIGIN) : "La nouvelle position ne pas être l'origine du repère";
        assert object4.getPosition().getX() > -100 && object4.getPosition().getY() > -100
                && object4.getPosition().getZ() > -100 : "Le calcul de la nouvelle position est incorrect";
    }

    /**
     * Méthode principale exécutable qui lance tous les tests.
     * 
     * @param args Arguments donnés au fichier
     */
    public static void main(String[] args) {
        System.out.println("Test ==> MACROOBJECT");
        double dt = 1;

        testDefaultAttributes(new MacroObject("Test", null, 1e4, 10, new Vector3D(500, 300, -200), velocity.copy()));

        List<GenericObject> allObjects = new ArrayList<>();
        allObjects.add(new MacroObject("Test", null, 1e4, 10, new Vector3D(500, 300, -200), velocity.copy()));
        for (GenericObject o : allObjects) {
            o.simulate(allObjects, dt);
        }
        testSimulateOneBody(allObjects, dt);

        List<GenericObject> allObjects1 = new ArrayList<>();
        allObjects1.add(new MacroObject("Test", null, 1e4, 10, new Vector3D(500, 300, -200), velocity.copy()));
        allObjects1.add(new MacroObject("Test2", null, 1e5, 10, Constants.ORIGIN, Constants.ORIGIN));
        for (GenericObject o : allObjects1) {
            o.simulate(allObjects1, dt);
        }
        testSimulateTwoBodies(allObjects1, dt);

        List<GenericObject> allObjects2 = new ArrayList<>();
        allObjects2.add(new MacroObject("Test", null, 1e4, 10, new Vector3D(500, 300, -200), velocity.copy()));
        allObjects2.add(new MacroObject("Test2", null, 1e5, 10, Constants.ORIGIN, Constants.ORIGIN));
        allObjects2.add(new MacroObject("Test3", null, 1e2, 10, new Vector3D(60, -100, -50), new Vector3D(5, 23, 15)));
        allObjects2.add(
                new MacroObject("Giant attractor", null, 1e10, 10, new Vector3D(-100, -100, -100), Constants.ORIGIN));
        for (GenericObject o : allObjects2) {
            o.simulate(allObjects2, dt);
        }
        testSimulateMultipleBodies(allObjects2, dt);
    }
}