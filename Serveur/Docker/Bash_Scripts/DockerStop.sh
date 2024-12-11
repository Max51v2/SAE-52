#!/bin/bash
#Auteur : Maxime VALLET
#Version 1.0


#Arrêt des conteneurs
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

clear

#Conteneurs
docker ps -a