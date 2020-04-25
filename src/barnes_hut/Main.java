package barnes_hut;

import java.util.*;

import math_physics.math.*;
import math_physics.physics.*;

/**
 * Classe principale du package <em>barnes_hut</em> permettant de lancer une
 * simulation de Barnes-Hut.
 */
public class Main {
    /**
     * Méthode principale.
     * 
     * @param args Argument venant du terminal
     */
    public static void main(String[] args) {
        ArrayList<GenericObject> bodies = new ArrayList<>();
        Random generator = new Random();
        double maxRadius = 200;
        int N = 8_000;
        // int N = 320_000;

        // Création des corps
        for (int i = 0; i < N; i++) {
            bodies.add(new Particule(1, 1,
                    new Vector3D(generator.nextDouble() * maxRadius, generator.nextDouble() * maxRadius,
                            generator.nextDouble() * maxRadius),
                    new Vector3D(generator.nextDouble() * 10, generator.nextDouble() * 10,
                            generator.nextDouble() * 10)));
        }

        // Boucle de simulation
        for (int t = 0; t < 10; t++) {
            System.out.println("Tour " + (t + 1));

            Octal oct = new Octal(new Vector3D(), maxRadius * 4, maxRadius * 4, maxRadius * 4);
            BHTree tree = new BHTree(oct);

            // Construction de l'arbre de Barnes-Hut
            for (GenericObject body : bodies) {
                tree.insertion(body);
            }
            // Mise à jour de la force
            for (GenericObject body : bodies) {
                body.resetForce();
                tree.updateForceAboutObject(body);
                body.simulate(1);
            }
        }
        System.out.println(bodies);
    }
}