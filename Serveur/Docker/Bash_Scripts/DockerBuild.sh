#!/bin/bash
#Auteur : Maxime VALLET
#Version 1.0

clear

#Installation de docker
echo "Installation des dépendances (apt-transport-https / curl / ca-certificates / software-properties-common)"
sudo apt install -y apt-transport-https ca-certificates curl software-properties-common > /tmp/DockerBuildLogs.txt
clear
echo "Ajout des clés du répertoire de docker"
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - >> /tmp/DockerBuildLogs.txt
clear
echo "Ajout du répertoire de Docker"
sudo add-apt-repository -y "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable" >> /tmp/DockerBuildLogs.txt
clear
apt-cache policy docker-ce >> /tmp/DockerBuildLogs.txt
clear
echo "Installation de docker-ce"
sudo apt install -y docker-ce >> /tmp/DockerBuildLogs.txt
clear

clear

#Arrêt et suppression des conteneurs
sudo /home/$1/Bureau/SAE-52/Serveur/Docker/Bash_Scripts/DockerStop.sh "$1"

#SSL
mkdir /certs
cd /certs
if [ -e /certs/SAE52.crt ]; then
    echo "SSL ok"
else
    sudo openssl req -x509 -nodes -days 10000 -newkey rsa:4096 -keyout /certs/SAE52.key -out /certs/SAE52.crt -subj "/C=FR/ST=Meurthe-et-moselle/L=Nancy/O=VIM/OU=Meurthe-et-moselle/CN=www.tkt.fr/emailAddress=max.vallet@outlook.fr"
fi
if [ -e /certs/SAE52.p12 ]; then
    echo "keystore ok"
else
    sudo openssl pkcs12 -export -in SAE52.crt -inkey SAE52.key -out SAE52.p12 -name tomcat -passout pass:leffe
fi


#Apache
clear
echo "Apache"

DockerFilePath="/home/$1/Bureau/SAE-52/Serveur/Docker/Apache"
cd $DockerFilePath

mkdir -p $DockerFilePath/Web
sudo cp -r /home/$1/Bureau/SAE-52/Web/* $DockerFilePath/Web
sudo cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/gmao.conf $DockerFilePath/gmao.conf
sudo cp /certs/SAE52.crt $DockerFilePath/SAE52.crt
sudo cp /certs/SAE52.key $DockerFilePath/SAE52.key

docker build -t apache /home/$1/Bureau/SAE-52/Serveur/Docker/Apache

sudo rm -rf $DockerFilePath/Web
sudo rm $DockerFilePath/gmao.conf
sudo rm $DockerFilePath/SAE52.crt
sudo rm $DockerFilePath/SAE52.key


#PostgreSQL
clear
echo "PostgreSQL"

DockerFilePath="/home/$1/Bureau/SAE-52/Serveur/Docker/PostgreSQL"
cd $DockerFilePath

cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/postgresql.conf $DockerFilePath/postgresql.conf
cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/pg_hba.conf $DockerFilePath/pg_hba.conf
cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/StartPSQL.sh $DockerFilePath/StartPSQL.sh
cp /home/$1/Bureau/SAE-52/Serveur/PostgreSQL_config.sql $DockerFilePath/PostgreSQL_config.sql

docker build -t psql /home/$1/Bureau/SAE-52/Serveur/Docker/PostgreSQL

sudo rm $DockerFilePath/pg_hba.conf
sudo rm $DockerFilePath/postgresql.conf
sudo rm $DockerFilePath/PostgreSQL_config.sql
sudo rm $DockerFilePath/StartPSQL.sh


#Tomcat
clear
echo "Tomcat"

DockerFilePath="/home/$1/Bureau/SAE-52/Serveur/Docker/Tomcat"
cd $DockerFilePath

sudo cp /certs/SAE52.crt $DockerFilePath/SAE52.crt
sudo cp /certs/SAE52.key $DockerFilePath/SAE52.key
sudo cp /certs/SAE52.p12 $DockerFilePath/SAE52.p12
sudo cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/Tomcat.sh $DockerFilePath/Tomcat.sh
sudo cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/StartTomcat.sh $DockerFilePath/StartTomcat.sh
sudo cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/Tomcat.xml $DockerFilePath/Tomcat.xml
sudo cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/tomcat-users.xml $DockerFilePath/tomcat-users.xml
sudo cp /home/$1/Bureau/SAE-52/NetBEANS/SAE52/dist/SAE52.war $DockerFilePath/SAE52.war

docker build -t tomcat /home/$1/Bureau/SAE-52/Serveur/Docker/Tomcat

sudo rm $DockerFilePath/SAE52.crt
sudo rm $DockerFilePath/SAE52.key
sudo rm $DockerFilePath/SAE52.p12
sudo rm $DockerFilePath/Tomcat.sh
sudo rm $DockerFilePath/StartTomcat.sh
sudo rm $DockerFilePath/Tomcat.xml
sudo rm $DockerFilePath/tomcat-users.xml
sudo rm $DockerFilePath/SAE52.war



#Conteneurs
echo "Conteneurs existants :"
docker ps -a
echo
