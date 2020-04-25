package barnes_hut;

import math_physics.math.*;
import math_physics.physics.*;

/**
 * Représente un noeud-arbre à huit branches de l'algorithme de Barnes-Hut.
 */
public class BHTree {
    /**
     * Correspond à une valeur permettant la vérification de la distance d'un objet
     * au centre de gravité du noeud-arbre.
     */
    private double THETA;

    /**
     * Correspond à un corps. Si l'attribut est à <em>null</em>, alors il n'y a pas
     * de corps stocké et donc le noeud-arbre n'est pas une feuille. Sinon, le
     * noeud-arbre est une feuille et on stocke le corps associé à cette feuille.
     */
    private GenericObject body = null;

    /**
     * Correspond au centre de gravité (centre de la masse) du noeud-arbre.
     */
    private CenterOfMass COM;

    /**
     * Correspond au noeud/boîte associé à ce noeud-arbre.
     */
    private Octal box;

    /**
     * Correspond au sous-arbre placé au Nord-Ouest Avant.
     */
    private BHTree NWF = null;

    /**
     * Correspond au sous-arbre placé au Nord-Ouest Arrière.
     */
    private BHTree NWB = null;

    /**
     * Correspond au sous-arbre placé au Nord-Est Avant.
     */
    private BHTree NEF = null;

    /**
     * Correspond au sous-arbre placé au Nord-Est Arrière.
     */
    private BHTree NEB = null;

    /**
     * Correspond au sous-arbre placé au Sud-Ouest Avant.
     */
    private BHTree SWF = null;

    /**
     * Correspond au sous-arbre placé au Sud-Ouest Arrière.
     */
    private BHTree SWB = null;

    /**
     * Correspond au sous-arbre placé au Sud-Est Avant.
     */
    private BHTree SEF = null;

    /**
     * Correspond au sous-arbre placé au Sud-Est Arrière.
     */
    private BHTree SEB = null;

    /**
     * Constructeur du noeud-arbre.
     * 
     * @param box Noeud/boîte associé au noeud-arbre
     */
    public BHTree(Octal box) {
        this(box, 0.5);
    }

    /**
     * Constructeur du noeud-arbre.
     * 
     * @param box   Noeud/boîte associé au noeud-arbre
     * @param theta Valeur permettant de vérifier une distance d'un objet du centre
     *              de gravité du noeud-arbre.
     */
    public BHTree(Octal box, double theta) {
        this.box = box;
        this.THETA = theta;
        this.COM = new CenterOfMass(0, this.box.getCenter());
    }

    @Override
    public String toString() {
        return "BHTree[Body=" + this.body + ", " + this.box.toString() + "](" + this.NWB + "," + this.NWF + ","
                + this.NEB + "," + this.NEF + "," + this.SWB + "," + this.SWF + "," + this.SEB + "," + this.SEF + ")";
    }

    /**
     * Permet de récupérer la boite liée au noeud de l'arbre.
     * 
     * @return Boite
     */
    public Octal getBox() {
        return this.box;
    }

    /**
     * Permet de récupérer la valeur actuelle du Théta.
     * 
     * @return Valeur du Théta
     */
    public double getTheta() {
        return this.THETA;
    }

    /**
     * Permet de remplacer la valeur du Théta.
     * 
     * @param newTheta Nouvelle valeur pour le Théta
     */
    public void setTheta(double newTheta) {
        this.THETA = newTheta;
    }

    /**
     * Permet de vérifier si le noeud-arbre est divisé ou non.
     * 
     * @return Booléen : true s'il est divisé, false sinon.
     */
    public boolean boxDivided() {
        return this.NWB != null; // On peut en vérifier qu'un car les fils sont tous créés en même temps
    }

    /**
     * Permet d'insérer un corps dans le noeud-arbre.
     * 
     * @param bodyToInsert Corps à insérer
     */
    public void insertion(GenericObject bodyToInsert) {
        // S'il n'y a aucun corps et qu'il n'y a pas de fils
        if (this.body == null && !this.boxDivided()) {
            this.body = bodyToInsert;
            this.COM = new CenterOfMass(bodyToInsert.getMass(), bodyToInsert.getPosition());
        } else { // S'il y a déjà un corps à la racine de l'arbre
            if (!this.boxDivided()) { // Si le noeud-arbre ne contient pas encore de fils
                this.divideBox();
                this.putBodyInBHTree(this.body);
            }
            Formula F = new Formula();
            this.COM = F.centerOfMass(this.COM.mass, bodyToInsert.getMass(), this.COM.position,
                    bodyToInsert.getPosition());
            this.putBodyInBHTree(bodyToInsert);
        }
    }

    /**
     * Permet d'insérer un corps dans les sous-arbres du noeud-arbre actuel.
     * 
     * @param bodyToInsert Corps à insérer
     */
    private void putBodyInBHTree(GenericObject bodyToInsert) {
        if (this.box.NWB().contains(bodyToInsert)) {
            this.NWB.insertion(bodyToInsert);
        } else if (this.box.NWF().contains(bodyToInsert)) {
            this.NWF.insertion(bodyToInsert);
        } else if (this.box.NEB().contains(bodyToInsert)) {
            this.NEB.insertion(bodyToInsert);
        } else if (this.box.NEF().contains(bodyToInsert)) {
            this.NEF.insertion(bodyToInsert);
        } else if (this.box.SWB().contains(bodyToInsert)) {
            this.SWB.insertion(bodyToInsert);
        } else if (this.box.SWF().contains(bodyToInsert)) {
            this.SWF.insertion(bodyToInsert);
        } else if (this.box.SEB().contains(bodyToInsert)) {
            this.SEB.insertion(bodyToInsert);
        } else if (this.box.SEF().contains(bodyToInsert)) {
            this.SEF.insertion(bodyToInsert);
        }
    }

    /**
     * Permet de créer les huit fils pour le noeud-arbre.
     */
    private void divideBox() {
        this.NWB = new BHTree(this.box.NWB());
        this.NWF = new BHTree(this.box.NWF());
        this.NEB = new BHTree(this.box.NEB());
        this.NEF = new BHTree(this.box.NEF());
        this.SWB = new BHTree(this.box.SWB());
        this.SWF = new BHTree(this.box.SWF());
        this.SEF = new BHTree(this.box.SEF());
        this.SEB = new BHTree(this.box.SEB());
    }

    /**
     * Permet de mettre à jour les forces exercées sur l'objet par le centre de
     * gravité.
     * 
     * @param bodyToUpdate Corps à mettre à jour
     */
    public void updateForceAboutObject(GenericObject bodyToUpdate) {
        if (this.body != null) {
            // Si l'arbre contient un corps
            if (this.boxDivided()) {
                if (this.body != bodyToUpdate) {
                    // Si le corps à mettre à jour est différent du corps contenu dans le noeud
                    // actuel de l'arbre
                    Vector3D boxDimension = this.box.getDimensions();
                    double distance = this.body.getPosition().distanceFromOtherVector(bodyToUpdate.getPosition());
                    if (boxDimension.divide(distance).compareMatrix(ConditionOperator.LE, THETA)) {
                        // Si l'objet est trop loin du centre de gravité
                        bodyToUpdate.addForce(this.COM);
                    } else {
                        this.NWB.updateForceAboutObject(bodyToUpdate);
                        this.NWF.updateForceAboutObject(bodyToUpdate);
                        this.NEB.updateForceAboutObject(bodyToUpdate);
                        this.NEF.updateForceAboutObject(bodyToUpdate);
                        this.SWB.updateForceAboutObject(bodyToUpdate);
                        this.SWF.updateForceAboutObject(bodyToUpdate);
                        this.SEF.updateForceAboutObject(bodyToUpdate);
                        this.SEB.updateForceAboutObject(bodyToUpdate);
                    }
                }
            } else {
                if (this.body != bodyToUpdate) {
                    // Si le corps à mettre à jour est différent du corps contenu dans le noeud
                    // actuel de l'arbre
                    bodyToUpdate.addForce(this.body);
                }
            }
        }
    }
}