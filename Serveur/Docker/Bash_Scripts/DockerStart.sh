#!/bin/bash
#Auteur : Maxime VALLET
#Version 0.1

clear

#Arrêt et suppression des conteneurs
sudo /home/$1/Bureau/SAE-52/Serveur/Docker/Bash_Scripts/DockerStop.sh "$1"

#Démarrage des conteneurs
sudo -u $1 docker compose -f /home/sae-52/Bureau/SAE-52/Serveur/Docker/Dockercompose.yml up -d



#Conteneurs
echo "Conteneurs existants :"
sleep 2
docker ps -a
echo