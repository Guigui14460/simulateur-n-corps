package math_physics.math.test;

import math_physics.math.*;

/**
 * Classe de test permettant de vérifier si l'implémentation des matrices est
 * bonne.
 */
public class MatrixAssert {
    /**
     * Teste l'égalité de deux objet <em>Matrix</em> différents mais de contenu
     * égaux s'il sont bien égaux.
     */
    private static void testEquality() {
        Matrix Z = new Matrix(2, 3);
        Z.setValue(1, 0, 1);
        Z.setValue(2, 0, 2);
        Z.setValue(5, 1, 0);
        Z.setValue(6, 1, 1);
        Z.setValue(7, 1, 2);
        double[][] d = { { 0, 1, 2 }, { 5, 6, 7 } };
        Matrix A = new Matrix(d);
        assert Z.equals(A) : "Les deux matrices devraient être égales";
    }

    /**
     * Teste l'addition entre deux matrices.
     */
    private static void testAddition() {
        double[][] d = { { 0, 1, 2 }, { 5, 6, 7 } };
        Matrix A = new Matrix(d);
        Matrix B = new Matrix(2, 3, 1);
        Matrix C = A.plus(B);
        double[][] d2 = { { 1, 2, 3 }, { 6, 7, 8 } };
        Matrix test1 = new Matrix(d2);
        assert C.equals(test1) : "Les deux matrices devraient être égales";
    }

    /**
     * Teste si la transposée de la matrice fonctionne.
     */
    private static void testTransposition() {
        double[][] d = { { 0, 1, 2 }, { 5, 6, 7 } };
        Matrix A = new Matrix(d);
        Matrix B = new Matrix(2, 3, 1);
        Matrix C = A.plus(B);
        Matrix D = C.transpose();
        double[][] d3 = { { 1, 6 }, { 2, 7 }, { 3, 8 } };
        assert D.equals(new Matrix(d3)) : "Les deux matrices devraient être égales";
    }

    /**
     * Teste le produit matriciel.
     */
    private static void testProduct() {
        double[][] d = { { 0, 1, 2 }, { 5, 6, 7 } };
        Matrix A = new Matrix(d);
        Matrix B = new Matrix(2, 3, 1);
        Matrix C = A.plus(B);
        Matrix D = C.transpose();
        Matrix E = D.product(C);
        double[][] d4 = { { 37, 44, 51 }, { 44, 53, 62 }, { 51, 62, 73 } };
        assert E.equals(new Matrix(d4)) : "Les deux matrices devraient être égales";
    }

    /**
     * Teste la comparaison sur les matrices.
     */
    private static void testCompare() {
        double[][] d = { { 0, 1, 2 }, { 5, 6, 7 } };
        Matrix A = new Matrix(d);
        assert !A.compareMatrix(ConditionOperator.EQ, 5) : "La comparaison n'est pas correcte";
        assert !A.compareMatrix(ConditionOperator.GE, 7) : "La comparaison n'est pas correcte";
        assert A.compareMatrix(ConditionOperator.LE, 7) : "La comparaison n'est pas correcte";
    }

    /**
     * Méthode principale exécutable qui lance tous les tests.
     * 
     * @param args Arguments donnés au fichier
     */
    public static void main(String[] args) {
        System.out.println("Test ==> MATRIX");
        testEquality();
        testAddition();
        testTransposition();
        testProduct();
        testCompare();
    }
}