#!/bin/bash
#Auteur : Maxime VALLET (SAE-52)
#Version 2.0

#Rajouté pour la SAE-52 du groupe : valentin, zian, ishac
#port serveur Apache : 50000
#port serveur Tomcat : 8080 (HTTP) / 8443 (HTTPS)
# à modifier : clés SSL ?
#   => si tu host avec accès à internet, essaye d'utiliser Let's encrypt (sinon faudra valider les clés SSL sur le site comme dab)

sudo clear

echo "Script d'installation du projet pour Debian 12"

clear

echo "Mise à jour du système"
echo
sudo apt update
sudo apt upgrade

clear

echo "Installation d'ufw"
echo
sudo apt install -y ufw
sudo systemctl enable ufw
sudo ufw enable

clear

echo "Installation de curl"
echo
sudo apt install -y curl

clear

echo "Installation de wget"
echo
sudo apt install -y wget

clear

echo "Installation du JDK"
echo
sudo mkdir /usr/java
cd /usr/java
sudo mkdir ./openjdk
JDK="openjdk-22.0.2_linux-x64_bin.tar.gz"
sudo wget -c https://download.java.net/java/GA/jdk22.0.2/c9ecb94cd31b495da20a27d4581645e8/9/GPL/$JDK
sudo tar xzvf ./$JDK -C ./openjdk --strip-components=1
sudo rm $JDK

clear

echo "Installation de PostgreSQL"
echo
sudo apt install -y postgresql
clear
cd /etc/postgresql/
postgreSQLVersion=`ls | head -n 1`
cd /etc/postgresql/$postgreSQLVersion/main/
sudo cp /home/adminuser/Bureau/SAE-52/Serveur/Docker/Config/postgresql.conf /etc/postgresql/$postgreSQLVersion/main/postgresql.conf
sudo cp /home/adminuser/Bureau/SAE-52/Serveur/Docker/Config/pg_hba.conf /etc/postgresql/$postgreSQLVersion/main/pg_hba.conf
sudo systemctl disable postgresql
sudo systemctl restart postgresql.service
sudo apt install -y postgresql-client

clear

echo "Création des clés SSL"
echo
sudo mkdir /certs
cd /certs
echo
sudo openssl req -x509 -nodes -days 10000 -newkey rsa:4096 -keyout /certs/SAE52.key -out /certs/SAE52.crt -subj "/C=FR/ST=Meurthe-et-moselle/L=Nancy/O=VIM/OU=Meurthe-et-moselle/CN=www.tkt.fr/emailAddress=max.vallet@outlook.fr"

clear

echo "Installation d'Apache"
echo
sudo apt install -y apache2
sudo ufw allow 'Apache'
sudo mkdir /var/www/gmao
cd /etc/apache2/sites-available/
sudo cp /home/adminuser/Bureau/SAE-52/Serveur/Docker/Config/gmao.conf gmao.conf
sudo a2ensite gmao.conf
sudo a2enmod headers
sudo a2dissite 000-default.conf
sudo systemctl daemon-reload
sudo systemctl restart apache2
sudo ufw allow 50000

clear
cd /usr/java
Java_version=`ls | head -n 1`

clear

echo "Installation de Tomcat"
echo
sudo groupadd tomcat
sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat
cd /tmp
#Liens de téléchargement tomcat car il change à chaque version
TOMCAT_URL=$(curl -sS https://tomcat.apache.org/download-90.cgi | grep '>tar.gz</a>' | head -1 | grep -E -o 'https://[a-z0-9:./-]+.tar.gz')
TOMCAT_NAME=$(echo $TOMCAT_URL | grep -E -o 'apache-tomcat-[0-9.]+[0-9]')
sudo wget -c $TOMCAT_URL
sudo mkdir /opt/tomcat
sudo tar xzvf /tmp/$TOMCAT_NAME".tar.gz" -C /opt/tomcat --strip-components=1
cd /opt
sudo chown -R tomcat: tomcat
cd ./tomcat
sudo chown -R tomcat webapps/ work/ temp/ logs/ conf/
sudo chmod o+x /opt/tomcat/bin/
sudo cp /home/adminuser/Bureau/SAE-52/Serveur/Docker/Config/tomcat.service /etc/systemd/system/tomcat.service
sudo sed -i 's/\[VERSION JDK\]/'$Java_version'/g' /etc/systemd/system/tomcat.service
sudo ufw allow 8080
sudo ufw allow 8443
sudo cp /home/adminuser/Bureau/SAE-52/Serveur/Docker/Config/tomcat-users.xml /opt/tomcat/conf/tomcat-users.xml
cd /certs
clear
sudo openssl pkcs12 -export -in SAE52.crt -inkey SAE52.key -out SAE52.p12 -name tomcat -passout pass:leffe
cd /certs
sudo /usr/java/$Java_version/bin/keytool -importkeystore -deststorepass administrateur -destkeystore /opt/tomcat/conf/tomcat.keystore -srckeystore SAE52.p12 -srcstoretype PKCS12 -srcstorepass leffe -alias tomcat
sudo cp /home/adminuser/Bureau/SAE-52/Serveur/Docker/Config/Tomcat.xml /opt/tomcat/conf/server.xml
sudo systemctl daemon-reload
sudo systemctl disable tomcat 
sudo systemctl stop tomcat 

clear

#Création de la BD
sudo psql -h localhost -U postgres -d template1 -c "DROP DATABASE sae_52;" -f "/home/adminuser/Bureau/SAE-52/Serveur/PostgreSQL_config.sql"
sudo psql -h localhost -U postgres -d template1 -c "ALTER USER postgres with encrypted password 'leffe';"

#Lancement de start.sh au démarrage
sudo echo "[Unit]
Description=Exécution de Start.sh au démarrage
After=network.target

[Service]
ExecStart=/bin/bash /home/adminuser/Bureau/SAE-52/Serveur/Docker/Bash_Scripts/Start.sh
Restart=on-failure

[Install]
WantedBy=multi-user.target" > /etc/systemd/system/Startsh.service
sudo systemctl enable Startsh.service