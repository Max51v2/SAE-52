#OS : Debian 12 (dérivés normalement fonctionnels)
#NE PAS METTRE DEBIAN / J'ai perdu 3H!!! à cause de ça
FROM ubuntu:latest

#Créé et maintenu par Maxime VALLET
LABEL maintainer="max.vallet@outlook.fr"

#MAJ OS, installation wget et curl
RUN apt update && apt upgrade && apt install -y wget curl openjdk-21-jdk && apt-get clean

#Copie des clés SSL
COPY SAE52.key /certs/SAE52.key
COPY SAE52.crt /certs/SAE52.crt
COPY SAE52.p12 /certs/SAE52.p12

#Copie du script de configuration de JDK et Tomcat et données de conf
COPY Tomcat.sh /conf/Tomcat.sh
COPY StartTomcat.sh /conf/StartTomcat.sh
COPY Tomcat.xml /conf/Tomcat.xml
COPY tomcat-users.xml /conf/tomcat-users.xml
COPY SAE52.war /conf/SAE52.war

#VAR env
ENV JAVA_HOME=/usr/lib/jvm/openjdk
ENV CATALINA_HOME=/opt/tomcat

#Execution du script de config
RUN chmod 777 /conf/Tomcat.sh && \
    /conf/Tomcat.sh && \
    chmod 777 /conf/StartTomcat.sh

#Ajout des répertoire de tomcat
RUN chmod 777 /opt/tomcat && \
    chown 777 /conf && \
    chown 777 /certs

RUN /usr/lib/jvm/java-21-openjdk-amd64/bin/keytool -importkeystore -deststorepass administrateur -destkeystore /opt/tomcat/conf/tomcat.keystore -srckeystore /certs/SAE52.p12 -srcstoretype PKCS12 -srcstorepass leffe -alias tomcat

#Ports
EXPOSE 8443
EXPOSE 8080

USER tomcat

#Commande par défaut
CMD ["/conf/StartTomcat.sh"]