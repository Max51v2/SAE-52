#!/bin/bash
#Auteur : Maxime VALLET
#Version 0.1


#Arrêt et suppression des conteneurs
sudo /home/$USER/Bureau/SAE-52/Serveur/Docker/Bash_Scripts/DockerStop.sh "$USER"

#Démarrage des conteneurs
cd /home/$USER/Bureau/SAE-52/Serveur/Docker
docker-compose up -d


#Conteneurs
docker ps -a