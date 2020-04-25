package math_physics.math;

/**
 * Représente une matrice avec quelques opérations de base entre matrice
 * disponibles. Utilisation de la classe <code>BinaryMatrix</code>.
 */
public class Matrix extends BinaryMatrix {
    /**
     * Constructeur de l'objet <code>Matrix</code>. Par défaut, les cases de la
     * matrice sont initialisées à 0.
     * 
     * @param n Nombre de ligne de la matrice ç créer
     * @param m Nombre de colonne de la matrice à créer
     */
    public Matrix(int n, int m) {
        super(n, m, 0);
    }

    /**
     * Constructeur de l'objet <code>Matrix</code>.
     * 
     * @param n         Nombre de ligne de la matrice à créer
     * @param m         Nombre de colonne de la matrice à créer
     * @param initValue Valeur initiale des cases de la matrice
     */
    public Matrix(int n, int m, double initValue) {
        super(n, m, initValue);
    }

    /**
     * Constructeur de l'objet <code>Matrix</code>.
     * 
     * @param data Grille de données de type <em>double</em>
     */
    public Matrix(double[][] data) {
        super(data);
    }

    /**
     * Permet de vérifier si le contenu de 2 matrices sont égaux.
     * 
     * @param obj Objet Java
     * @return Booléen (true si les contenus sont égaux, sinon false)
     */
    @Override
    public boolean equals(Object obj) {
        // Si l'objet passé est l'objet lui-même
        if (obj == this) {
            return true;
        }
        // Si l'objet ne contient rien ou n'est pas une instance d'un objet Matrix
        if (obj == null || !(obj instanceof Matrix)) {
            return false;
        }
        // Vérification des dimensions
        Matrix other = (Matrix) obj;
        if (this.n != other.n || this.m != other.m) {
            return false;
        }
        // Vérification pour chaque élément des deux matrices
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                if (this.board[i][j] != other.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Permet de générer une copie de la matrice actuelle dans un autre objet.
     * 
     * @return Copie de la matrice
     */
    public Matrix copy() {
        return new Matrix(this.board);
    }

    /**
     * Permet d'avoir la transposée de la matrice.
     * 
     * @return Nouvelle matrice
     */
    public Matrix transpose() {
        // On retourne la matrice (transposition)
        Matrix to_return = new Matrix(this.m, this.n);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                to_return.board[j][i] = this.board[i][j];
            }
        }
        return to_return;
    }

    /**
     * Permet d'ajouter une matrice A à une matrice B. La méthode renvoie <b>une
     * nouvelle matrice</b> qui est la <b>somme des 2</b>.
     * 
     * @param other Matrice à ajouter
     * @return Nouvelle matrice
     * @throws NDimensionsException Levé lorsque les dimensions entre les deux
     *                              matrices sont différentes
     */
    public Matrix plus(BinaryMatrix other) throws NDimensionsException {
        this.verifyDimensions(other);
        // On effectue l'addition pour chaque élément
        Matrix final_matrix = new Matrix(this.n, this.m);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                final_matrix.board[i][j] = this.board[i][j] + other.board[i][j];
            }
        }
        return final_matrix;
    }

    /**
     * Permet d'ajouter une valeur à tous les éléments de la matrice. La méthode
     * renvoie <b>une nouvelle matrice</b>.
     * 
     * @param value Valeur à ajouter
     * @return Nouvelle matrice
     */
    public Matrix plus(double value) {
        // On effectue l'addition pour chaque élément
        Matrix final_matrix = this;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                final_matrix.board[i][j] = this.board[i][j] + value;
            }
        }
        return final_matrix;
    }

    /**
     * Permet de soustraire une matrice B à une matrice A. La méthode renvoie <b>une
     * nouvelle matrice</b> qui est la <b>différence des 2</b>.
     * 
     * @param other Matrice qui soustrait
     * @return Nouvelle matrice
     * @throws NDimensionsException Levé lorsque les dimensions entre les deux
     *                              matrices sont différentes
     */
    public Matrix minus(BinaryMatrix other) throws NDimensionsException {
        this.verifyDimensions(other);
        // On effectue la soustraction pour chaque élément
        Matrix final_matrix = new Matrix(this.n, this.m);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                final_matrix.board[i][j] = this.board[i][j] - other.board[i][j];
            }
        }
        return final_matrix;
    }

    /**
     * Permet de soustraire une valeur à tous les éléments de la matrice. La méthode
     * renvoie <b>une nouvelle matrice</b>.
     * 
     * @param value Valeur à soustraire
     * @return Nouvelle matrice
     */
    public Matrix minus(double value) {
        // On effectue la soustraction pour chaque élément
        Matrix final_matrix = this;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                final_matrix.board[i][j] = this.board[i][j] - value;
            }
        }
        return final_matrix;
    }

    /**
     * Permet d'effectuer un produit matriciel. La méthode renvoie <b>une nouvelle
     * matrice</b> qui est le <b>produit des 2</b>.
     * 
     * @param other Matrice qui multiplie
     * @return Nouvelle matrice
     * @throws NDimensionsException Levé lorsque les dimensions entre les deux
     *                              matrices sont différentes
     */
    public Matrix product(BinaryMatrix other) throws NDimensionsException {
        // Vérification des dimensions
        if (this.n != other.m) {
            throw new NDimensionsException(
                    "Les dimensions des matrices ne correspondent pas au pattern suivant : (n,m) * (m,n)");
        }
        // On effectue le produit matriciel
        Matrix final_matrix = new Matrix(this.n, other.m);
        for (int i = 0; i < final_matrix.n; i++) {
            for (int j = 0; j < final_matrix.m; j++) {
                for (int k = 0; k < this.m; k++) {
                    final_matrix.board[i][j] += this.board[i][k] * other.board[k][j];
                }
            }
        }
        return final_matrix;
    }

    /**
     * Permet de multiplier tous les éléments de la matrice par une valeur. La
     * méthode renvoie <b>une nouvelle matrice</b>.
     * 
     * @param value Valeur pour mutiplier
     * @return Nouvelle matrice
     */
    public Matrix product(double value) {
        // On effectue la multiplication pour chaque élément
        Matrix final_matrix = this;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                final_matrix.board[i][j] = this.board[i][j] * value;
            }
        }
        return final_matrix;
    }

    /**
     * Permet de diviser tous les éléments de la matrice par une valeur. La méthode
     * renvoie <b>une nouvelle matrice</b>.
     * 
     * @param value Valeur pour diviser
     * @return Nouvelle matrice
     * @throws IllegalArgumentException Levé lorsque la valeur est égale à 0
     */
    public Matrix divide(double value) throws IllegalArgumentException {
        if (value == 0) {
            throw new IllegalArgumentException("Le diviseur ne peut être égale à 0");
        }
        // On effectue la division pour chaque élément
        Matrix final_matrix = this;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                final_matrix.board[i][j] = this.board[i][j] / value;
            }
        }
        return final_matrix;
    }

    /**
     * Permet de diviser tous les éléments de la matrice par une autre matrice. La
     * méthode renvoie <b>une nouvelle matrice</b>.
     * 
     * @param other Matrice pour diviser
     * @return Nouvelle matrice
     * @throws IllegalArgumentException Levé lorsqu'au moins une des valeurs
     *                                  contenues dans la matrice donnée est égale à
     *                                  0
     * @throws NDimensionsException     Levé lorsque les dimensions entre les deux
     *                                  matrices sont différentes
     */
    public Matrix divide(BinaryMatrix other) throws IllegalArgumentException, NDimensionsException {
        // Vérification des dimensions
        this.verifyDimensions(other);
        // On effectue la division pour chaque élément
        Matrix final_matrix = this;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                if (other.board[i][j] == 0) {
                    throw new IllegalArgumentException("Le diviseur ne peut être égale à 0");
                }
                final_matrix.board[i][j] = this.board[i][j] / other.board[i][j];
            }
        }
        return final_matrix;
    }
}