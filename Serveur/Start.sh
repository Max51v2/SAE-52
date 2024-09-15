#!/bin/bash
# Auteur : Maxime VALLET
# Version : 0.9

clear

#Récupération du status du daemon postgresql
PostgreSQL=`systemctl status postgresql | grep -o -E "Active: [A-Za-z]+" | sed 's/.*: //'`
echo "Status PostgreSQL : "$PostgreSQL

#Demarrage de PostgreSQL si il est éteint
if [ "$PostgreSQL" = "inactive" ]
then
    #demarrage
    echo "demarrage de PostgreSQL"
    systemctl start postgresql
fi

echo



#Copie des fichiers dans le répertoire d'Apache2 (à modifier si nouveaux fichiers)
GitRep="/home/"$USER"/Bureau/SAE-52/Web/"
ApacheRep="/var/www/gmao"
sudo cp $GitRep"login.html" $ApacheRep"/login.html"
sudo cp $GitRep"logout.html" $ApacheRep"/logout.html"
sudo cp $GitRep"manageuser.html" $ApacheRep"/manageuser.html"
sudo cp $GitRep"sae52.html" $ApacheRep"/sae52.html"

#Récupération du status du daemon apache2
apache2=`systemctl status apache2 | grep -o -E "Active: [A-Za-z]+" | sed 's/.*: //'`
echo "Status apache2 : "$apache2

#Demarrage de apache2 si il est éteint
if [ "$apache2" = "inactive" ]
then
    #demarrage
    echo "demarrage de apache2"
    sudo systemctl start apache2
fi

#On recharge apache2 car le contenu du rep a changé
sudo systemctl reload apache2

echo



#Récupération du status du daemon tomcat
#tomcat=`systemctl status tomcat | grep -o -E "Active: [A-Za-z]+" | sed 's/.*: //'`
#echo "Status tomcat : "$tomcat

#Demarrage de tomcat si il est éteint
#if [ "$tomcat" = "active" ]
#then
    #demarrage
    #echo "demarrage de tomcat"
    #sudo systemctl start tomcat
#fi

#echo



#Section reconstruction BD
#Recupération option utilisateur
echo "Souhaitez-vous reconstruire la Base de Données ? [O/N]
*Cela va effacer le contenu de la BD 
"
read  -n 1 -p "Option :" option

clear

#Reconstruction BD
if [ "$option" = "o" ] || [ "$option" = "O" ]
then
    #Connexion à la base
    psql -h localhost -U postgres -d template1 -c "DROP DATABASE sae_52;" -f "/home/"$USER"/Bureau/SAE-52/Serveur/PostgreSQL_config.sql"
fi



#Affichage du status des opérations
clear
echo "Compte rendu exécution :"

#Récupération du status du daemon postgresql
PostgreSQL=`systemctl status postgresql | grep -o -E "Active: [A-Za-z]+" | sed 's/.*: //'`
echo "Status PostgreSQL : "$PostgreSQL

echo

#Récupération du status du daemon apache2
apache2=`systemctl status apache2 | grep -o -E "Active: [A-Za-z]+" | sed 's/.*: //'`
echo "Status apache2 : "$apache2

echo

#Récupération du status du daemon tomcat
#tomcat=`systemctl status tomcat | grep -o -E "Active: [A-Za-z]+" | sed 's/.*: //'`
#echo "Status tomcat : "$tomcat

#echo

if [ "$option" = "o" ] || [ "$option" = "O" ]
then
    echo "Base de données SQL reconstruite"
fi

echo

#cmd NetBEANS
cd /usr/java
Java_version=`ls | head -n 1`

echo "Commande pour démarrer NetBEANS (ne pas fermer le terminal une fois NetBEANS ouvert):"
echo "sudo netbeans --jdkhome /usr/java/"$Java_version