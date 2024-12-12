#!/bin/bash
#Auteur : Maxime VALLET
#Version 0.1

clear

#Arrêt et suppression des conteneurs
sudo /home/$1/Bureau/SAE-52/Serveur/Docker/Bash_Scripts/DockerStop.sh "$1"

#Démarrage des conteneurs
sudo docker run -d -p 5432:5432 postgresql & sudo docker run -d -p 50000:443 apache & sudo docker run -d -p 8443:8443 tomcat



#Conteneurs
echo "Conteneurs existants :"
sleep 2
docker ps -a
echo