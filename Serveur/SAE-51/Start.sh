#!/bin/bash
# Auteur : Maxime VALLET
# Version : 2.0
#Rajouté pour la SAE-51 du groupe : valentin, zian, ishac

#à faire : déploiement projet java


#Récupperation de la version de Java (lancement NetBEANS)
clear
cd /usr/java
Java_version=`ls | head -n 1`


clear
echo "Veuillez patienter"


#Récupération du status du daemon postgresql
PostgreSQL=`systemctl status postgresql | grep -o -E "Active: [A-Za-z]+" | sed 's/.*: //'`

#Demarrage de PostgreSQL si il est éteint
if [ "$PostgreSQL" = "inactive" ]
then
    #demarrage
    echo "demarrage de PostgreSQL"
    systemctl start postgresql

    echo
fi


#Copie des fichiers dans le répertoire d'Apache2 (NE PAS TOUCHER)
GitRep="/home/"$USER"/Bureau/SAE-52/Web/"
ApacheRep="/var/www/gmao"

#Vidage du Répertoire Apache
sudo rm -rf $ApacheRep"/"*

#Création répertoire Javadoc
sudo mkdir -p /var/www/gmao/Javadoc

#Copie des fichiers
sudo cp -r $GitRep* $ApacheRep"/"
sudo cp -r "/home/"$USER"/Bureau/SAE-52/NetBEANS/SAE52/dist/javadoc/"* $ApacheRep"/Javadoc"

# Fin NE PAS TOUCHER


#Récupération du status du daemon apache2
apache2=`systemctl status apache2 | grep -o -E "Active: [A-Za-z]+" | sed 's/.*: //'`

#Demarrage de apache2 si il est éteint
if [ "$apache2" = "inactive" ]
then
    #demarrage
    echo "demarrage de apache2"
    sudo systemctl start apache2

    echo
fi

#On recharge apache2 car le contenu du rep a changé
sudo systemctl daemon-reload

clear

################# à rajouter #################

#Build du projet java (.war)

#Déploiement du .war sur le serveur Tomcat

##############################################