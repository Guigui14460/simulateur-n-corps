package math_physics.physics.test;

import java.util.*;

import math_physics.math.*;
import math_physics.physics.*;

public class SimulationTest {
    public static final int dt = 1;

    private static final Vector3D dSE = new Vector3D(100e6, 111e6, 3);
    private static final Vector3D dSJ = new Vector3D(550e6, 550e6, 9);

    public static void main(String[] args) {
        List<GenericObject> bodies = new ArrayList<>();
        MacroObject sunObject = new MacroObject("Sun", null, Constants.SUN_MASS, 10, new Vector3D(), new Vector3D());
        MacroObject earthObject = new MacroObject("Earth", sunObject, Constants.EARTH_MASS, 10, dSE,
                new Vector3D(-1000, 1000, 0));
        MacroObject jupiterObject = new MacroObject("Jupiter", sunObject, Constants.JUPITER_MASS, 10, dSJ,
                new Vector3D(-1000, 1000, 0));
        bodies.add(sunObject);
        bodies.add(earthObject);
        bodies.add(jupiterObject);

        int nbRotationDay = dt * 2;
        for (int i = 0; i < nbRotationDay; i += dt) {
            List<Vector3D> allPositions = new ArrayList<>();
            List<Double> allMass = new ArrayList<>();
            for (GenericObject o : bodies) {
                allPositions.add(o.getPosition());
                allMass.add(o.getMass());
            }
            for (GenericObject o : bodies) {
                o.simulate(bodies, dt);
                System.out.println(o + " : ");
                o.getPosition().showInline();
                System.out.println("");
                o.getVelocity().showInline();
                System.out.println("");
                o.getAcceleration().showInline();
                System.out.println("\n*******************************************");
            }
            System.out.println("\n\n");
        }
    }
}