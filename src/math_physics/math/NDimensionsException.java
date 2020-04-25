package math_physics.math;

/**
 * Classe d'exception. Permet de dire que 2 matrices n'ont pas les dimensions
 * attendues pour effectuer une certaine action.
 */
public class NDimensionsException extends RuntimeException {
    /**
     * Ajout d'un numéro <em>serialVersionUID</em> pour le code erreur.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur de la classe d'exception. Message par défaut : "Les deux
     * matrices n'ont pas les mêmes dimensions".
     */
    public NDimensionsException() {
        super("Les deux matrices n'ont pas les mêmes dimensions");
    }

    /**
     * Constructeur de la classe d'exception. Permet d'afficher un autre message que
     * celui par défaut.
     * 
     * @param message Message à afficher lors de l'erreur
     */
    public NDimensionsException(String message) {
        super(message);
    }
}