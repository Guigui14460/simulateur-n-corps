package gui;

import javafx.beans.property.*;
import javafx.concurrent.*;
import nbody.*;

/**
 * Classe permettant d'exécuter un tour de simulation dans un autre Thread avec
 * un service proposé par JavaFX.
 */
public class SimulationService extends Service<Void> {
    /**
     * Simulateur.
     */
    private final SimpleObjectProperty<NBodySimulator> simulatorProperty = new SimpleObjectProperty<NBodySimulator>();

    /**
     * Constructeur de la classe.
     * 
     * @param simulator Simulateur sur lequel il faut simuler
     */
    public SimulationService(NBodySimulator simulator) {
        this.simulatorProperty.setValue(simulator);
    }

    /**
     * Permet de créer une tâche sur un Thread.
     */
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException {
                simulatorProperty.getValue().simulateAllBodies();
                return null;
            }
        };
    }
}