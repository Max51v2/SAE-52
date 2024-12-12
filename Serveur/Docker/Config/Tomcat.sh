#!/bin/bash
#Auteur : Maxime VALLET
#Version 1.0


#Tomcat
groupadd tomcat
useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat
cd /tmp
TOMCAT_URL=$(curl -sS https://tomcat.apache.org/download-90.cgi | grep '>tar.gz</a>' | head -1 | grep -E -o 'https://[a-z0-9:./-]+.tar.gz')
TOMCAT_NAME=$(echo $TOMCAT_URL | grep -E -o 'apache-tomcat-[0-9.]+[0-9]')
wget -c $TOMCAT_URL
mkdir /opt/tomcat
tar xzvf /tmp/$TOMCAT_NAME".tar.gz" -C /opt/tomcat --strip-components=1
cd /opt
chown -R tomcat: tomcat
cd ./tomcat
chown -R tomcat webapps/ work/ temp/ logs/ conf/
chmod o+x /opt/tomcat/bin/
cp /conf/tomcat-users.xml /opt/tomcat/conf/tomcat-users.xml
cd /certs
/usr/lib/jvm/java-21-openjdk-amd64/bin/keytool -importkeystore -deststorepass administrateur -destkeystore /opt/tomcat/conf/tomcat.keystore -srckeystore SAE52.p12 -srcstoretype PKCS12 -srcstorepass leffe -alias tomcat
cp /conf/Tomcat.xml /opt/tomcat/conf/server.xml