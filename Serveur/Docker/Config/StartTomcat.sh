#!/bin/bash
#Auteur : Maxime VALLET
#Version 1.0


#Déploiement du .war sur le serveur Tomcat
/opt/tomcat/bin/catalina.sh run &
sleep 5
curl -u admin:leffe -T /conf/SAE52.war "http://localhost:8080/manager/text/deploy?path=/SAE52&update=true"

#Boucle infinie pour garder le conteneur actif
while true
do
sleep 5
done
