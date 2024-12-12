#OS : Debian 12 (dérivés normalement fonctionnels)
FROM debian:12

#Créé et maintenu par Maxime VALLET
LABEL maintainer="max.vallet@outlook.fr"

#MAJ OS et installation apache
RUN apt update && apt upgrade && apt install -y apache2 && apt-get clean

#Ports
EXPOSE 50000

#Installation d'Apache
RUN apt update && apt install -y apache2

#Copie des pages Web
COPY Web/ /var/www/gmao
COPY gmao.conf /etc/apache2/sites-available/gmao.conf
COPY SAE52.key /certs/SAE52.key
COPY SAE52.crt /certs/SAE52.crt

#Configuration du serveur Apache
RUN a2enmod ssl && \
    a2enmod headers && \
    a2ensite gmao.conf && \
    a2dissite 000-default.conf

#Commande par défaut
CMD ["apache2ctl", "-D", "FOREGROUND"]