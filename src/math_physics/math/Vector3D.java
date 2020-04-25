package math_physics.math;

/**
 * Représente un vecteur en 3 dimensions. Utilisation de la classe
 * <code>BinaryMatrix</code> pour effectuer les calculs vectoriels.
 */
public class Vector3D extends BinaryMatrix {
    /**
     * Constructeur par défaut. Construit un nouveau vecteur où les valeurs sont
     * initialisées à 0.
     */
    public Vector3D() {
        super(3, 1, 0);
    }

    /**
     * Constructeur de vecteur. Construit un nouveau vecteur à partir de données
     * séparées.
     * 
     * @param x Donnée pour la première ligne
     * @param y Donnée pour la deuxième ligne
     * @param z Donnée pour la troisième ligne
     */
    public Vector3D(double x, double y, double z) {
        super(3, 1, 0);
        this.setValue(x, 0, 0);
        this.setValue(y, 1, 0);
        this.setValue(z, 2, 0);
    }

    /**
     * Constructeur de vecteur. Construit un nouveau vecteur à partir de données.
     * 
     * @param data Données pour créer le vecteur
     */
    public Vector3D(double[][] data) throws NDimensionsException {
        super(data);
        // Vérification des dimensions du tableau de données
        if (data.length != 3 && data[0].length != 1) {
            throw new NDimensionsException("Les données doivent être de la forme (3,1).");
        }
    }

    @Override
    public String toString() {
        return "(" + this.getX() + "," + this.getY() + "," + this.getZ() + ")";
    }

    /**
     * Permet de vérifier si le contenu de 2 vecteurs sont égaux.
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
        // Si l'objet ne contient rien ou n'est pas une instance d'un objet Vector3D
        if (obj == null || !(obj instanceof Vector3D)) {
            return false;
        }
        // Vérification des dimensions
        Vector3D other = (Vector3D) obj;
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
     * Permet d'afficher le vecteur en une seule ligne.
     */
    public void showInline() {
        for (int i = 0; i < this.n; i++) {
            System.out.print(this.getValue(i, 0) + " ");
        }
        System.out.print("\n");
    }

    /**
     * Permet de retourner le premier élément du vecteur.
     * 
     * @return Premier élément du vecteur
     */
    public double getX() {
        return this.getValue(0, 0);
    }

    /**
     * Permet de changer le premier élément du vecteur.
     * 
     * @param newValueOnX Valeur à placer
     */
    public void setX(double newValueOnX) {
        this.setValue(newValueOnX, 0, 0);
    }

    /**
     * Permet de retourner le deuxième élément du vecteur.
     * 
     * @return Deuxième élément du vecteur
     */
    public double getY() {
        return this.getValue(1, 0);
    }

    /**
     * Permet de changer le deuxième élément du vecteur.
     * 
     * @param newValueOnY Valeur à placer
     */
    public void setY(double newValueOnY) {
        this.setValue(newValueOnY, 1, 0);
    }

    /**
     * Permet de retourner le troisième et dernier élément du vecteur.
     * 
     * @return Troisième et dernier élément du vecteur
     */
    public double getZ() {
        return this.getValue(2, 0);
    }

    /**
     * Permet de changer le troisième et dernier élément du vecteur.
     * 
     * @param newValueOnZ Valeur à placer
     */
    public void setZ(double newValueOnZ) {
        this.setValue(newValueOnZ, 2, 0);
    }

    /**
     * Permet de générer une copie du vecteur actuel dans un autre objet.
     * 
     * @return Copie du vecteur
     */
    public Vector3D copy() {
        return new Vector3D(this.board);
    }

    /**
     * Permet d'avoir la transposée du vecteur.
     * 
     * @return Nouvelle matrice
     */
    public Matrix transpose() {
        Matrix a = new Matrix(this.m, this.n);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                a.setValue(this.board[i][j], j, i);
            }
        }
        return a;
    }

    /**
     * Permet d'ajouter un vecteur A à un vecteur B. La méthode renvoie <b>un
     * nouveau vecteur</b> qui est la <b>somme des 2</b>.
     * 
     * @param other Vecteur à ajouter
     * @return Nouveau vecteur
     * @throws NDimensionsException Levé lorsque les dimensions entre les deux
     *                              matrices sont différentes
     */
    public Vector3D plus(BinaryMatrix other) throws NDimensionsException {
        this.verifyDimensions(other);
        return new Vector3D(this.board[0][0] + other.board[0][0], this.board[1][0] + other.board[1][0],
                this.board[2][0] + other.board[2][0]);
    }

    /**
     * Permet d'ajouter une valeur à tous les éléments du vecteur. La méthode
     * renvoie <b>un nouveau vecteur</b>.
     * 
     * @param value Valeur à ajouter
     * @return Nouveau vecteur
     */
    public Vector3D plus(double value) {
        return new Vector3D(this.board[0][0] + value, this.board[1][0] + value, this.board[2][0] + value);
    }

    /**
     * Permet de soustraire un vecteur B à un vecteur A. La méthode renvoie <b>un
     * nouveau vecteur</b> qui est la <b>différence des 2</b>.
     * 
     * @param other Vecteur qui soustrait
     * @return Nouveau vecteur
     * @throws NDimensionsException Levé lorsque les dimensions entre les deux
     *                              matrices sont différentes
     */
    public Vector3D minus(BinaryMatrix other) throws NDimensionsException {
        this.verifyDimensions(other);
        return new Vector3D(this.board[0][0] - other.board[0][0], this.board[1][0] - other.board[1][0],
                this.board[2][0] - other.board[2][0]);
    }

    /**
     * Permet de soustraire une valeur à tous les éléments du vecteur. La méthode
     * renvoie <b>un nouveau vecteur</b>.
     * 
     * @param value Valeur à soustraire
     * @return Nouveau vecteur
     */
    public Vector3D minus(double value) {
        return new Vector3D(this.board[0][0] - value, this.board[1][0] - value, this.board[2][0] - value);
    }

    /**
     * Permet d'effectuer un produit matriciel. La méthode renvoie <b>un nouveau
     * vecteur</b> qui est le <b>produit des 2</b>.
     * 
     * @param other Vecteur qui multiplie
     * @return Nouveau vecteur
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
     * Permet de multiplier tous les éléments du vecteur par une valeur. La méthode
     * renvoie <b>un nouveau vecteur</b>.
     * 
     * @param value Valeur pour mutiplier
     * @return Nouveau vecteur
     */
    public Vector3D product(double value) {
        return new Vector3D(this.board[0][0] * value, this.board[1][0] * value, this.board[2][0] * value);
    }

    /**
     * Permet de diviser tous les éléments du vecteur par une valeur. La méthode
     * renvoie <b>un nouveau vecteur</b>.
     * 
     * @param value Valeur pour diviser
     * @return Nouveau vecteur
     * @throws IllegalArgumentException Levé lorsque le diviseur est égale à 0
     */
    public Vector3D divide(double value) throws IllegalArgumentException {
        if (value == 0) {
            throw new IllegalArgumentException("Le diviseur ne peut être égale à 0");
        }
        return new Vector3D(this.board[0][0] / value, this.board[1][0] / value, this.board[2][0] / value);
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
    public Vector3D divide(BinaryMatrix other) throws IllegalArgumentException, NDimensionsException {
        this.verifyDimensions(other);
        Vector3D other_vector = (Vector3D) other;
        if (other_vector.getX() == 0 || other_vector.getY() == 0 || other_vector.getZ() == 0) {
            throw new IllegalArgumentException("Le diviseur ne peut pas être égale à 0");
        }
        return new Vector3D(this.getX() / other_vector.getX(), this.getY() / other_vector.getY(),
                this.getZ() / other_vector.getZ());
    }

    /**
     * Permet de calculer la distance entre le vecteur actuel et l'origine du plan.
     * 
     * @return Distance entre le vecteur et l'origine du plan
     */
    public double distanceFromOrigin() {
        return Math.sqrt(this.getValue(0, 0) * this.getValue(0, 0) + this.getValue(1, 0) * this.getValue(1, 0)
                + this.getValue(2, 0) * this.getValue(2, 0));
    }

    /**
     * Permer de calculer la distance entre le vecteur actuel et un autre vecteur du
     * même plan.
     * 
     * @param other Vecteur position de l'autre vecteur
     * @return Distance entre les deux vecteurs
     */
    public double distanceFromOtherVector(Vector3D other) {
        Vector3D difference = (Vector3D) this.minus(other);
        return difference.distanceFromOrigin();
    }
}