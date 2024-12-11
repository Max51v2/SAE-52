#!/bin/bash
#Auteur : Maxime VALLET
#Version 0.1

clear

#Arrêt et suppression des conteneurs
sudo /home/$USER/Bureau/SAE-52/Serveur/Docker/Bash_Scripts/DockerStop.sh "$USER"

#SSL
sudo apt install -y docker-compose docker-buildx openssl
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
#rm $DockerFilePath/SAE52.crt
#rm $DockerFilePath/SAE52.key


#PostgreSQL
clear
echo "PostgreSQL"

DockerFilePath="/home/$1/Bureau/SAE-52/Serveur/Docker/PostgreSQL"
cd $DockerFilePath

cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/postgresql.conf $DockerFilePath/postgresql.conf
cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/pg_hba.conf $DockerFilePath/pg_hba.conf
cp /home/$1/Bureau/SAE-52/Serveur/Docker/Config/init.sh $DockerFilePath/init.sh
cp /home/$1/Bureau/SAE-52/Serveur/PostgreSQL_config.sql $DockerFilePath/PostgreSQL_config.sql

docker build -t postgresql /home/$1/Bureau/SAE-52/Serveur/Docker/PostgreSQL

rm $DockerFilePath/pg_hba.conf
rm $DockerFilePath/postgresql.conf
rm $DockerFilePath/init.sh
rm $DockerFilePath/PostgreSQL_config.sql


clear

#Conteneurs
docker ps -a