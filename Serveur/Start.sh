#!/bin//bash
# Auteur : Maxime VALLET
# Version : 0.2

#Récupération du status du daemon postgresql
PostgreSQL=`systemctl status postgresql | grep -o -E "Active: [A-Za-z]+" | sed 's/.*: //'`
echo "Status PostgreSQL : "$PostgreSQL

#Demarrage de PostgreSQL si il est éteint
if [ "$PostgreSQL" = "inactive" ]
then
    #demarrage
    echo "demarrage de PostgreSQL"
    systemctl start postgresql

    #Récupération du status du daemon postgresql
    PostgreSQL=`systemctl status postgresql | grep -o -E "Active: [A-Za-z]+" | sed 's/.*: //'`
    echo "Status PostgreSQL : "$PostgreSQL
fi

#Récupération du status du daemon apache2
apache2=`systemctl status apache2 | grep -o -E "Active: [A-Za-z]+" | sed 's/.*: //'`
echo "Status apache2 : "$apache2

#Demarrage de apache2 si il est éteint
if [ "$apache2" = "inactive" ]
then
    #demarrage
    echo "demarrage de apache2"
    systemctl start apache2

    #Récupération du status du daemon apache2
    apache2=`systemctl status apache2 | grep -o -E "Active: [A-Za-z]+" | sed 's/.*: //'`
    echo "Status apache2 : "$apache2
fi