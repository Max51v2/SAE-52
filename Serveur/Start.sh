#!/bin/bash
# Auteur : Maxime VALLET
# Version : 1.8


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

echo "Souhaitez-vous reconstruire la Base de Données ? [O/N]
*Cela va effacer le contenu de la BD"

echo

read  -n 1 -p "Option :" option

clear

#Reconstruction BD
if [ "$option" = "o" ] || [ "$option" = "O" ]
then
    #Connexion à la base
    psql -h localhost -U postgres -d template1 -c "DROP DATABASE sae_52;" -f "/home/"$USER"/Bureau/SAE-52/Serveur/PostgreSQL_config.sql"
fi


#Demande de lancement NetBEANS s'il n'est pas en train de tourner
ProcNetBEANS=`ps -ef | grep -v grep | grep -o -E "sudo netbeans --jdkhome /usr/java/"$Java_version | head -n 1`

#Démarrage NetBEANS
if [ "$ProcNetBEANS" = "" ]
then
    #Recupération option utilisateur
    clear
    echo "Souhaitez-vous lancer NetBeans ? [O/N]"

    echo

    read  -n 1 -p "Option :" optionNetbeans

    #Lancement NetBEANS
    if [ "$optionNetbeans" = "o" ] || [ "$optionNetbeans" = "O" ]
    then
        clear

        #Lancement de NetBEANS dans un nouvel onglet
        gnome-terminal --tab -- /bin/sh -c 'echo "!!! Ne pas fermer cette fenêtre !!!"; echo; sudo netbeans --jdkhome /usr/java/'$Java_version

        sleep 1
    fi

    echo
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

#Statut DB
if [ "$option" = "o" ] || [ "$option" = "O" ]
then
    echo "Base de données SQL reconstruite"
    echo
fi

#Affichage status NetBEANS
ProcNetBEANS=`ps -ef | grep -v grep | grep -o -E "sudo netbeans --jdkhome /usr/java/"$Java_version | head -n 1`
if [ "$ProcNetBEANS" = "" ]
then
    echo "Status NetBEANS : inactive"
else
    echo "Status NetBEANS : active"
fi

echo

echo "Javadoc disponible ici : https://[@IP VM]/Javadoc/index.html"

echo