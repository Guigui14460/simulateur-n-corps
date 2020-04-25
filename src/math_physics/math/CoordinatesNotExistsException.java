package math_physics.math;

/**
 * Classe d'exception. Appelée lorsque les coordonnées de matrice renseignées
 * n'existent pas.
 */
public class CoordinatesNotExistsException extends RuntimeException {
    /**
     * Ajout d'un numéro <em>serialVersionUID</em> pour le code erreur.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur de la classe d'exception. Message par défaut : "Les coordonnées
     * renseignées n'existent pas".
     */
    public CoordinatesNotExistsException() {
        super("Les coordonnées renseignées n'existent pas");
    }
}