package math_physics.math;

/**
 * Représente la base d'une matrice avec quelques opérations de base entre
 * matrice disponibles.
 */
public abstract class BinaryMatrix {
    /**
     * Correspond à la taille de la matrice. <em>n</em> : nombre de lignes.
     */
    public final int n;

    /**
     * Correspond à la taille de la matrice. <em>m</em> : nombre de colonnes.
     */
    public final int m;

    /**
     * Correspond au tableau stockant les valeurs de la matrice.
     */
    protected double[][] board;

    /**
     * Constructeur de l'objet <code>BinaryMatrix</code>. Par défaut, les cases de
     * la matrice sont initialisées à 0.
     * 
     * @param n Nombre de ligne de la matrice ç créer
     * @param m Nombre de colonne de la matrice à créer
     */
    public BinaryMatrix(int n, int m) {
        this(n, m, 0);
    }

    /**
     * Constructeur de l'objet <code>BinaryMatrix</code>.
     * 
     * @param n         Nombre de ligne de la matrice à créer
     * @param m         Nombre de colonne de la matrice à créer
     * @param initValue Valeur initiale des cases de la matrice
     */
    public BinaryMatrix(int n, int m, double initValue) {
        this.n = n;
        this.m = m;
        this.board = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.board[i][j] = initValue;
            }
        }
    }

    /**
     * Constructeur de l'objet <code>BinaryMatrix</code>.
     * 
     * @param data Grille de données de type <em>double</em>
     */
    public BinaryMatrix(double[][] data) {
        this.n = data.length;
        this.m = data[0].length;
        this.board = new double[n][m];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                this.board[i][j] = data[i][j];
            }
        }
    }

    @Override
    public String toString() {
        return "Matrix (" + this.n + ", " + this.m + ")";
    }

    /**
     * Permet d'affiche proprement la matrice.
     */
    public void show() {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                System.out.print(this.board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Permet de générer une copie de la matrice actuelle dans un autre objet.
     * 
     * @return Copie de la matrice
     */
    public abstract BinaryMatrix copy();

    /**
     * Permet de récupérer les données brutes contenues dans la matrice.
     * 
     * @return Données
     */
    public double[][] getData() {
        return this.board;
    }

    /**
     * Permet de récupérer un élément aux coordonnées (i,j) de la matrice.
     * 
     * @param i La ligne où l'on veut récupérer la valeur
     * @param j La colonne où l'on veut récupérer la valeur
     * @return Valeur aux coordonnées (i,j)
     * @throws CoordinatesNotExistsException Levé lorsque les coordonnées
     *                                       renseignées n'existent pas
     */
    public double getValue(int i, int j) throws CoordinatesNotExistsException {
        if (i < 0 || i > this.board.length - 1 || j < 0 || j > this.board[0].length - 1) {
            throw new CoordinatesNotExistsException();
        }
        return this.board[i][j];
    }

    /**
     * Permet de placer un élément aux coordonnées (i,j) de la matrice.
     * 
     * @param value Valeur à ajouter
     * @param i     La ligne où l'on veut placer la valeur
     * @param j     La colonne où l'on veut placer la valeur
     * @throws CoordinatesNotExistsException Levé lorsque les coordonnées
     *                                       renseignées n'existent pas
     */
    public void setValue(double value, int i, int j) throws CoordinatesNotExistsException {
        if (i < 0 || i > this.board.length - 1 || j < 0 || j > this.board[0].length - 1) {
            throw new CoordinatesNotExistsException();
        }
        this.board[i][j] = value;
    }

    /**
     * Permet de vérifier que les deux objets ont les mêmes dimensions.
     * 
     * @param other Autre matrice
     * @return Booléen s'il n'y a pas d'erreur
     * @throws NDimensionsException Levé lorsque les dimensions des deux matrices
     *                              sont différentes
     */
    public boolean verifyDimensions(BinaryMatrix other) throws NDimensionsException {
        if (other.n != this.n || other.m != this.m) {
            throw new NDimensionsException();
        }
        return true;
    }

    /**
     * Permet d'avoir la transposée de la matrice.
     * 
     * @return Nouvelle matrice
     */
    public abstract BinaryMatrix transpose();

    /**
     * Permet d'ajouter une matrice A à une matrice B. La méthode renvoie <b>une
     * nouvelle matrice</b> qui est la <b>somme des 2</b>.
     * 
     * @param other Matrice à ajouter
     * @return Nouvelle matrice
     * @throws NDimensionsException Levé lorsque les dimensions entre les deux
     *                              matrices sont différentes
     */
    public abstract BinaryMatrix plus(BinaryMatrix other) throws NDimensionsException;

    /**
     * Permet d'ajouter une valeur à tous les éléments de la matrice. La méthode
     * renvoie <b>une nouvelle matrice</b>.
     * 
     * @param value Valeur à ajouter
     * @return Nouvelle matrice
     */
    public abstract BinaryMatrix plus(double value);

    /**
     * Permet de soustraire une matrice B à une matrice A. La méthode renvoie <b>une
     * nouvelle matrice</b> qui est la <b>différence des 2</b>.
     * 
     * @param other Matrice qui soustrait
     * @return Nouvelle matrice
     * @throws NDimensionsException Levé lorsque les dimensions entre les deux
     *                              matrices sont différentes
     */
    public abstract BinaryMatrix minus(BinaryMatrix other) throws NDimensionsException;

    /**
     * Permet de soustraire une valeur à tous les éléments de la matrice. La méthode
     * renvoie <b>une nouvelle matrice</b>.
     * 
     * @param value Valeur à soustraire
     * @return Nouvelle matrice
     */
    public abstract BinaryMatrix minus(double value);

    /**
     * Permet d'effectuer un produit matriciel. La méthode renvoie <b>une nouvelle
     * matrice</b> qui est le <b>produit des 2</b>.
     * 
     * @param other Matrice qui multiplie
     * @return Nouvelle matrice
     * @throws NDimensionsException Levé lorsque les dimensions entre les deux
     *                              matrices sont différentes
     */
    public abstract BinaryMatrix product(BinaryMatrix other) throws NDimensionsException;

    /**
     * Permet de multiplier tous les éléments de la matrice par une valeur. La
     * méthode renvoie <b>une nouvelle matrice</b>.
     * 
     * @param value Valeur pour mutiplier
     * @return Nouvelle matrice
     */
    public abstract BinaryMatrix product(double value);

    /**
     * Permet de diviser tous les éléments de la matrice par une valeur. La méthode
     * renvoie <b>une nouvelle matrice</b>.
     * 
     * @param value Valeur pour diviser
     * @return Nouvelle matrice
     * @throws IllegalArgumentException Levé lorsque la valeur donnée est égale à 0
     */
    public abstract BinaryMatrix divide(double value) throws IllegalArgumentException;

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
    public abstract BinaryMatrix divide(BinaryMatrix other) throws IllegalArgumentException, NDimensionsException;

    /**
     * Permet de comparer toutes les valeurs d'une matrice avec une autre matrice
     * donnée par un opérateur donné.
     * 
     * @param operator Opérateur de comparaison
     * @param other    Matrice avec qui il faut comparer
     * @return Booléen vérifiant la condition donnée
     * @throws NDimensionsException Levé lorsque les dimensions entre les deux
     *                              matrices sont différentes
     */
    public boolean compareMatrix(ConditionOperator operator, BinaryMatrix other) throws NDimensionsException {
        this.verifyDimensions(other);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                if (operator.equals(ConditionOperator.EQ)) {
                    if (this.board[i][j] != other.board[i][j]) {
                        return false;
                    }
                } else if (operator.equals(ConditionOperator.NE)) {
                    if (this.board[i][j] == other.board[i][j]) {
                        return false;
                    }
                } else if (operator.equals(ConditionOperator.LE)) {
                    if (this.board[i][j] > other.board[i][j]) {
                        return false;
                    }
                } else if (operator.equals(ConditionOperator.L)) {
                    if (this.board[i][j] >= other.board[i][j]) {
                        return false;
                    }
                } else if (operator.equals(ConditionOperator.GE)) {
                    if (this.board[i][j] < other.board[i][j]) {
                        return false;
                    }
                } else if (operator.equals(ConditionOperator.G)) {
                    if (this.board[i][j] <= other.board[i][j]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Permet de comparer toutes les valeurs d'une matrice avec une valeur donnée
     * par un opérateur donné.
     * 
     * @param operator Opérateur de comparaison
     * @param value    Valeur avec qui il faut comparer
     * @return Booléen vérifiant la condition donnée
     */
    public boolean compareMatrix(ConditionOperator operator, double value) {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                if (operator.equals(ConditionOperator.EQ)) {
                    if (this.board[i][j] != value) {
                        return false;
                    }
                } else if (operator.equals(ConditionOperator.NE)) {
                    if (this.board[i][j] == value) {
                        return false;
                    }
                } else if (operator.equals(ConditionOperator.LE)) {
                    if (this.board[i][j] > value) {
                        return false;
                    }
                } else if (operator.equals(ConditionOperator.L)) {
                    if (this.board[i][j] >= value) {
                        return false;
                    }
                } else if (operator.equals(ConditionOperator.GE)) {
                    if (this.board[i][j] < value) {
                        return false;
                    }
                } else if (operator.equals(ConditionOperator.G)) {
                    if (this.board[i][j] <= value) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
