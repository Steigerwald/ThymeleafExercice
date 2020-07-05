# OpenClassRoom projet 6

Projet de conception d'un site de partage d'information sur l'escalade

## 1. Comment lancer l'application:
```
git clone https://github.com/Steigerwald/ThymeleafExercice.git
cd web-thymeleaf
mvn spring-boot:run
```
## 2. Comment lancer la base de données
L'application nécessite une base de données MySQL avec les informations suivantes:
- nom de la base de données: escalades
- port: 3306
- user: root
- password: Admin!1973

lancer ensuite l'application pour qu'elle crée les tables gràce aux entités

## 3. Remplir la base de données avec un jeu de données Test

exécuter le fichier data.sql situé dans le dossier ressources.

informations de connection:

role admin: 
- identifiant: admin@gmail.com 
- password: coco

role user: 
- identifiant: user@gmail.com
- password: coco