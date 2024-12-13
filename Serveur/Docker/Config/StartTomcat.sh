#!/bin/bash
#Auteur : Maxime VALLET
#Version 1.0


#Déploiement du .war sur le serveur Tomcat
sudo /opt/tomcat/bin/catalina.sh run &
sleep 2
sudo curl -u admin:leffe -T /conf/SAE52.war "http://localhost:8080/manager/text/deploy?path=/SAE52&update=true"