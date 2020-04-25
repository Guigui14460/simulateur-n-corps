package nbody;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import javafx.scene.paint.*;
import math_physics.math.*;
import math_physics.physics.*;

/**
 * Classe permettant de charger des simulations à partir de fichiers et de
 * sauvegarder des simulations sur des fichiers
 */
public class NBodyFileParser {
    /**
     * Permet de charger une simulation à partir d'un fichier.
     * 
     * @param filename Nom du fichier à charger (chemin)
     * @return Simulateur prêt
     * @throws IOException Exception généré par les fichiers si quelque chose ne va
     *                     pas
     */
    public static NBodySimulator load(String filename) throws IOException {
        // Initialisation du système de lecture des fichiers
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = null;
        // Création du simulateur
        NBodySimulator simulator = new NBodySimulator();

        // On lit chaque ligne du fichier tant qu'il y a une ligne qui est remplit
        while ((line = reader.readLine()) != null) {
            String[] lineSplited = line.split(" ");
            // Prépare les éléments pour la position
            ArrayList<Double> positionClean = new ArrayList<>();
            for (String el : lineSplited[3].split(",")) {
                positionClean.add(Double.parseDouble(el.replace("(", "").replace(")", "")));
            }
            // Prépare les éléments pour la vitesse
            ArrayList<Double> velocityClean = new ArrayList<>();
            for (String el : lineSplited[4].split(",")) {
                velocityClean.add(Double.parseDouble(el.replace("(", "").replace(")", "")));
            }
            // Prépare les éléments pour la couleur
            ArrayList<Double> colorClean = new ArrayList<>();
            for (String el : lineSplited[5].split(",")) {
                colorClean.add(Double.parseDouble(el.replace("(", "").replace(")", "")));
            }
            // On crée les différents paramètres pour instancier le corps
            Vector3D position = new Vector3D(positionClean.get(0), positionClean.get(1), positionClean.get(2));
            Vector3D velocity = new Vector3D(velocityClean.get(0), velocityClean.get(1), velocityClean.get(2));
            Color color = new Color(colorClean.get(0), colorClean.get(1), colorClean.get(2), colorClean.get(3));

            // On ajoute le corps à la simulation
            simulator.addBody(new Body(lineSplited[0].replace("%20", " "), Double.parseDouble(lineSplited[1]),
                    Double.parseDouble(lineSplited[2]), position, velocity, color));
        }
        reader.close();
        return simulator;
    }

    /**
     * Permet de sauvegarder une simulation dans un fichier temporaire.
     * 
     * @param simulator Simulateur
     * @param filename  Nom du fichier (chemin)
     * @return Le fichier temporaire sur lequel la simulation est stockée
     * @throws IOException Exception généré par les fichiers si quelque chose ne va
     *                     pas
     */
    public static File saveInTemporaryFile(NBodySimulator simulator, String filename) throws IOException {
        File temp = File.createTempFile(filename, ".txt");
        // On initialise le système d'écriture des fichiers
        BufferedWriter br = new BufferedWriter(new FileWriter(temp));
        // On écrit chaque corps sur le fichier
        for (GenericObject o : simulator.getBodies()) {
            Body body = (Body) o;
            br.write(body.getAllData());
            br.newLine();
        }
        br.close();
        return temp;
    }

    /**
     * Permet de sauvegarder une simulation (stockée sur un fichier temporaire) sur
     * un fichier choisi par l'utilisateur.
     * 
     * @param tempFile Fichier temporaire où se trouve la simulation
     * @param destFile Fichier de destination où doit être stockée la simulation
     *                 (choisi par l'utilisateur)
     * @return Booléen qui dit si le fichier a bien été sauvegarder ou non
     * @throws IOException Exception généré par les fichiers si quelque chose ne va
     *                     pas
     */
    public static boolean saveFile(File tempFile, File destFile) throws IOException {
        FileOutputStream output = new FileOutputStream(destFile, false);
        Files.copy(tempFile.toPath(), output);
        return Files.exists(destFile.toPath());
    }
}