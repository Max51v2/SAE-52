#!/bin/bash
#Auteur : Maxime VALLET
#Version 0.1

clear

#Installation de docker
sudo apt install apt-transport-https ca-certificates curl software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"
apt-cache policy docker-ce
sudo apt install -y docker-ce

clear

#Arrêt et suppression des conteneurs
sudo /home/$1/Bureau/SAE-52/Serveur/Docker/Bash_Scripts/DockerStop.sh "$1"

#SSL
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

rm -rf $DockerFilePath/Web
rm $DockerFilePath/gmao.conf
rm $DockerFilePath/SAE52.crt
rm $DockerFilePath/SAE52.key


#PostgreSQL
clear
echo "PostgreSQL"

DockerFilePath="/home/$1/Bureau/SAE-52/Serveur/Docker/PostgreSQL"
cd $DockerFilePath

cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/postgresql.conf $DockerFilePath/postgresql.conf
cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/pg_hba.conf $DockerFilePath/pg_hba.conf
cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/docker-entrypoint.sh $DockerFilePath/docker-entrypoint.sh
cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/docker-ensure-initdb.sh $DockerFilePath/docker-ensure-initdb.sh
cp /home/$1/Bureau/SAE-52/Serveur/PostgreSQL_config.sql $DockerFilePath/PostgreSQL_config.sql

docker build -t postgresql /home/$1/Bureau/SAE-52/Serveur/Docker/PostgreSQL

rm $DockerFilePath/pg_hba.conf
rm $DockerFilePath/postgresql.conf
rm $DockerFilePath/docker-entrypoint.sh
rm $DockerFilePath/docker-ensure-initdb.sh
rm $DockerFilePath/PostgreSQL_config.sql


#Tomcat
clear
echo "Tomcat"

DockerFilePath="/home/$1/Bureau/SAE-52/Serveur/Docker/Tomcat"
cd $DockerFilePath

sudo cp /certs/SAE52.crt $DockerFilePath/SAE52.crt
sudo cp /certs/SAE52.key $DockerFilePath/SAE52.key
sudo cp /certs/SAE52.p12 $DockerFilePath/SAE52.p12
sudo cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/Tomcat.sh $DockerFilePath/Tomcat.sh
sudo cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/Tomcat.xml $DockerFilePath/Tomcat.xml
sudo cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/tomcat-users.xml $DockerFilePath/tomcat-users.xml
sudo cp /home/$1/Bureau/SAE-52/NetBEANS/SAE52/dist/SAE52.war $DockerFilePath/SAE52.war

docker build -t tomcat /home/$1/Bureau/SAE-52/Serveur/Docker/Tomcat

rm $DockerFilePath/SAE52.crt
rm $DockerFilePath/SAE52.key
rm $DockerFilePath/SAE52.p12
rm $DockerFilePath/Tomcat.sh
rm $DockerFilePath/Tomcat.xml
rm $DockerFilePath/tomcat-users.xml
rm $DockerFilePath/SAE52.war



#Conteneurs
echo "Conteneurs existants :"
docker ps -a
echo