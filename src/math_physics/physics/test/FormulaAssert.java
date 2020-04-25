package math_physics.physics.test;

import java.util.*;

import math_physics.math.*;
import math_physics.physics.*;

/**
 * Classe de test permettant de vérifier si les implémentations de formule de
 * physique sont bonnes.
 */
public class FormulaAssert {
    /**
     * Instance de la classe à tester.
     */
    private static final Formula formula = new Formula();

    /**
     * Teste si l'implémentation de la formule de calcul de force de Newton est
     * bonne.
     */
    private static void testNewtonGravitation() {
        assert 3.5423960813684973E22 - 1e21 <= formula.newtonGravitation(Constants.EARTH_MASS, Constants.SUN_MASS,
                Constants.SUN_EARTH_DISTANCE)
                && formula.newtonGravitation(Constants.EARTH_MASS, Constants.SUN_MASS,
                        Constants.SUN_EARTH_DISTANCE) <= 3.5423960813684973E22
                                + 1e21 : "Le calcul de la force est mauvais";
    }

    /**
     * Teste si l'implémentation de la formule de calcul viectorielle de force de
     * Newton est bonne.
     */
    private static void testNewtonGravitationVectorForm() {
        Vector3D F2 = formula.newtonGravitationVectorForm(Constants.EARTH_MASS, Constants.SUN_MASS, Constants.ORIGIN,
                new Vector3D(106e9, 106e9, 0));
        assert 3.5423960813684973E22 - 1e21 <= F2.distanceFromOrigin() && F2
                .distanceFromOrigin() <= 3.5423960813684973E22 + 1e21 : "Le calcul vectorielle de la force est mauvais";
    }

    /**
     * Teste si l'implémentation de la formule pour calculer l'accélération de
     * l'objet est bonne.
     */
    private static void testAccelerationCalculus() {
        List<GenericObject> objects = new ArrayList<>();
        objects.add(new MacroObject("Sun", null, Constants.SUN_MASS, 10, Constants.ORIGIN, Constants.ORIGIN));
        objects.add(new MacroObject("Earth", null, Constants.SUN_MASS, 10,
                new Vector3D(Constants.SUN_EARTH_DISTANCE, 0, 0), Constants.ORIGIN));
        objects.add(new MacroObject("Jupiter", null, Constants.JUPITER_MASS, 10,
                new Vector3D(0, Constants.SUN_JUPITER_DISTANCE, 0), Constants.ORIGIN));
        Vector3D A = formula.accelerationCalculus(objects.get(1), objects);
        assert A.getX() < 0 && A.getY() > 0 && A.getZ() == 0 : "Le calcul de l'accélération n'est pas correcte";
        objects.add(new MacroObject("Mars", null, Constants.MARS_MASS, 10,
                new Vector3D(0, 0, -Constants.SUN_MARS_DISTANCE), Constants.ORIGIN));
        Vector3D B = formula.accelerationCalculus(objects.get(1), objects);
        assert B.getX() < 0 && B.getY() > 0 && B.getZ() < 0 : "Le calcul de l'accélération n'est pas correcte";
    }

    /**
     * Méthode principale exécutable qui lance tous les tests.
     * 
     * @param args Arguments donnés au fichier
     */
    public static void main(String[] args) {
        System.out.println("Test ==> FORMULA");
        testNewtonGravitation();
        testNewtonGravitationVectorForm();
        testAccelerationCalculus();
    }
}