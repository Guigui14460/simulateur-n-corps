# Simulateur à N corps

Projet universitaire proposé par par l'Université de Caen Normandie afin d'approfondir notre connaissance dans la conception de logiciel.

<img src="logo-UNICAEN.jpg" style="width: 100px;" />


## Table des matières
1. [Introduction au sujet](#introduction)
2. [Setup](#setup)
3. [Commandes](#commandes)
4. [Documentation](#documentation)
5. [Auteurs du projet](#auteurs-du-projet)


## Introduction
Le but du projet est de réaliser un **simulateur à N corps**. Ceci permet de simuler l'interaction gravitationnelle entre chaque corps. Ainsi, un corps exerce des forces sur tous les autres corps et ceux-ci exercent des forces sur ce corps.

Pour ce projet, nous avons utilisé les **équations de Newton** (d'où l'utilisation du terme "force")
![Aperçu de l'espace de simulation](simulator.png)


## Setup
Nous avons utilisé qu'une seule librairie externe. Cette librairie est [JavaFX 11](https://openjfx.io/). Une [documentation](https://openjfx.io/javadoc/11/) entière est à disposition.

Cette librairie permet de faire des interfaces graphiques poussées en Java en gardant une structure connue dans le monde de la programmation, celui du MVC pour Model-View-Controller.


## Commandes
**Note importante :** Pour utiliser ces commandes, il faut impérativement que le [logiciel Ant](https://ant.apache.org/) soit installé !

Les commandes principales sont :
- `sh dist.sh` pour distribuer le programme (`dist.bat` pour Windows)
- `sh scripts/run.sh` pour lancer le programme (resp. `scripts\run.bat`)
- `sh scripts/compile.sh` pour lancer la compilation de tous les fichiers du programme (resp. `scripts\compile.bat`)
- `sh scripts/test.sh` pour lancer l'exécution de tous les tests du programme (resp. `scripts\test.bat`)
- `sh scripts/makejar.sh` pour créer un fichier jar contenant tous les .class et ressources nécessaires (resp. `scripts\makejar.bat`)
- `sh scripts/makedoc.sh` pour générer la Javadoc (le fichier principal est docs/index.html) (resp. `scripts\makedoc.bat`)
- `sh scripts/clean.sh` pour supprimer tous les dossiers/fichiers générés (resp. `scripts\clean.bat`)

Ces commandes provoqueront l'installation des librairies externes (ce qui **nécessite une connexion
internet**).


## Documentation
Lien pour accéder à la documentation du projet : https://guigui14460.github.io/Simulateur-N-corps/


## Auteurs du projet
- AGBODJAN Wilfried
- MORLAY Antoine
- PIGNARD Alexandre
- [LETELLIER Guillaume](https://github.com/Guigui14460)
