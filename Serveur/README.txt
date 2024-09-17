Auteur : Maxime VALLET
Version : 1.2

à faire : 
    - Servlets + BD
    - Web

+--------------------------VM-----------------------------
|
|    +-------------------------Général-------------------------  
|    |
|    |   Lien VM : TBD
|    |   MDP : leffe
|    |
|    |   *Demarrer les daemons + actualiser BD + Web
|    |   /home/$USER/Bureau/SAE-52/Serveur/Start.sh
|    |
|    |   *Le copier-coller est suporté entre la VM et l'hôte et vise-versa
|    |
|    +---------------------------------------------------------
|
|    +-------------------------VSCode--------------------------
|    |
|    |   VSCode est intégré dans la VM
|    |
|    |   Se connecter à GitHub dans VSCode :
|    |   Cliquer sur l'onglet "Compte" (en bas à gauche) et sélectionner l'option pour se connecter à Github
|    |
|    |   *Modifier le nom et l'@ mail
|    |   git config --global user.name "[Prenom Nom]"
|    |   git config --global user.email "[@ Mail]"
|    |
|    |   *Pour actualiser le code dans le dossier Web sur le serveur apache, il faut tourner Start.sh
|    |   => en cas d'ajout de fichiers (HTML/CSS/JS), il faut ajouter les fichiers (cf. ligne 23 > Start.sh)
|    |
|    +---------------------------------------------------------
|
|    +-----------------------PostgreSQL-----------------------  
|    |
|    |   *Connexion à la BD
|    |   sudo -u postgres psql template1
|    |
|    |   *Aide commandes 
|    |   \?
|    |
|    |   *Script reconstruction BD
|    |   ./Start.sh a une option pour reconstruire la base à partir du script "PostgreSQL_config.sql"
|    |   => !!! toute modification de la BD doit se faire dans ce script sql (il faut refaire tourner Start.sh) !!!
|    |
|    +---------------------------------------------------------
|
|    +-------------------------Tomcat-------------------------- 
|    |
|    |   Tomcat se lance lorsqu'on lance le projet dans NetBEANS (ne JAMAIS le lancer manuellement sinon le serveur Tomcat ne fonctonnera PAS)
|    |
|    +---------------------------------------------------------
|
|    +------------------------NetBEANS------------------------- 
|    |
|    |   *Lancer NetBEANS (obligatoire / commande donnée par Start.sh)
|    |   => sudo netbeans --jdkhome /usr/java/[version JDK]
|    |   
|    |   *Au lancement du projet, si la fenêtre requesting keyreing apparait mais que NetBEANS ne demande pas de MDP, il faut redémarrer NetBEANS
|    |
|    +---------------------------------------------------------
|
|    +-------------------CONCLUSION A LIRE--------------------- 
|    |
|    |   Pour lancer les daemons, actualiser les fichiers Web et reconstruire la DB, lancez Start.sh (cf. section VM > Général)
|    |
|    |   Pour sauvegarder le projet > VSCode
|    |   => icon source control (branche à gauche) > survoler menu déroulant "Changes" > cliquer sur le + pour ajouter tous les fichiers (tt dans être dans "staged changes")
|    |   => menu détaillé bouton commit > commit and push > Ajouter un commentaire (non commenté) > valider (en haut à droite)
|    |
|    |   Cloner un répertoire Github sur le BUREAU (obligatoire avant de commencer) :
|    |   Cliquer sur l'onglet "Explorer" (pages), cliquer sur "Clone repository" > "Clone from Github" > "Max51v2/SAE-52" > Bureau NetBEANS)
|    |
|    |   Remplacer le répertoire Github local par celui en ligne (si tu veux reset les modifs du projet)
|    |   => icon source control (branche à gauche) > survoler menu déroulant "Source control graph" > cliquer sur l'icon pull
|    |
|    |   *Ajouter le certificat de l'authorité de certification (déjà fait sur la VM)
|    |   => *Même après ajout, le navigateur affiche toujours que la connexion n'est pas sécurisé car le certificat est auto-signé (mais ça marche)
|    |   => Pour ajouter un certificat, merci de regarder la section "procédure d'installation", rubrique "Ajout certificat"
|    |
|    |
|    |   Mis à part la partie Web (gérée par Start.sh), tous les autres fichiers sont placés correctement
|    |   => Il n'a pas besoin de toucher au contenu du répertoire Github local et tout est sauvegardé en faisant un "commit and push"
|    |   => Web et Serveur > VSCode | Servlets (dossier NetBEANS) > NetBEANS
|    |   => Il n'y a besoin du terminal que pour lancer Start.sh et NetBEANS
|    |
|    |   Adresses serveurs (@IP VM peut être remplacé par "localhost" si connexion sur le navigateur de la VM) :
|    |   => Apache : https://[@IP VM]/[NomPage]
|    |   => Tomcat (administration) : http://[@IP VM]:8443
|    |   => Tomcat (servlets) : https://[@IP VM]:8443/SAE52/[NomServlet]
|    |
|    |   *Cartes réseau :
|    |   => il y'a deux cartes réseaux : une en mode bridge et une en mode NAT
|    |   => dans le cas ou la première fonctionne (enp0s3), les serveurs sont accessibles à partir de l'IP de l'OS hôte (donc accessible au réseau local)
|    |   => dans le cas ou la deuxième est la seule qui fonctionne (enp0s8), les serveurs sont accessibles à partir de l'IP de la carte virtuelle VirtualBox (donc accessible à l'OS hôte uniquement)
|    |   => aucune modif requise/ raison : impossible d'utiliser le mode bridge sur eduroam
|    | 
|    +---------------------------------------------------------
|
+---------------------------------------------------------




+-----Procédure d'installation Ubuntu 24.04 (sauf VM)-----
|
|    +-------------------------Général------------------------
|    |
|    |   sudo apt update
|    |   sudo apt upgrade
|    |   sudo apt-get install git
|    |
|    |   *Script de demarrage des daemons
|    |   chmod u+x /home/$USER/Bureau/SAE-52/Serveur/Start.sh
|    |   
|    |   *Demarrage deamons (une fois installation terminée): voir section VM > Général
|    |
|    +--------------------------------------------------------
|
|    +-------------------------VSCode-------------------------  
|    |   
|    |   sudo snap install code --classic
|    |   *installer le module Github pull requests et modifier le nom ainsi que l'@ mail (instructions d'utilisation dans VM > VSCode)
|    |
|    +--------------------------------------------------------
|
|    +-----------------------PostgreSQL-----------------------  
|    |
|    |   !!! En cas d'utilisation en dehors du cadre de ce projet, remplacez les MDP !!!
|    |
|    |   Installation (ubuntu):
|    |   sudo apt install postgresql
|    |
|    |   cd /etc/postgresql/[Version PostgreSQL]/main/
|    |
|    |   sudo nano postgresql.conf
|    |
|    |   *remplacer " #listen_addresses = 'localhost' " par " listen_addresses = 'localhost' "
|    |   *sauvegarder et fermer le fichier
|    |
|    |   sudo -u postgres psql template1
|    |
|    |   ALTER USER postgres with encrypted password 'leffe';
|    |   
|    |   \q
|    |
|    |   sudo nano /etc/postgresql/[Version PostgreSQL]/main/pg_hba.conf
|    |
|    |   *En dessous de cette ligne "# TYPE  DATABASE        USER            ADDRESS                 METHOD"
|    |   *=> ajouter "host all       all        localhost        md5"
|    |
|    |   sudo systemctl restart postgresql.service
|    |   
|    |   *Test du fontionnement (MDP : "leffe")
|    |   => sudo apt install postgresql-client
|    |   => psql -h localhost -U postgres -d template1
|    |   *Si vous accedez à la Bd "Template1", cela fontionne sinon reprenez les étapes précédentes
|    |
|    |   \q
|    |
|    |   sudo -u postgres psql template1
|    |   
|    |   create role Administrateur WITH LOGIN PASSWORD 'Administrateur';
|    |   create role Technicien WITH LOGIN PASSWORD 'Technicien';
|    |   create role Utilisateur WITH LOGIN PASSWORD 'Utilisateur';
|    |
|    |   \i /home/[nom session]/Bureau/SAE-52/Serveur/PostgreSQL_config.sql
|    |
|    +---------------------------------------------------------
|
|    +--------------------------JDK----------------------------  
|    |   
|    |   sudo mkdir /usr/java
|    |   cd /usr/java
|    |   sudo mkdir ./openjdk-22.0.2
|    |
|    |   sudo wget -c https://download.java.net/java/GA/jdk22.0.2/c9ecb94cd31b495da20a27d4581645e8/9/GPL/openjdk-22.0.2_linux-x64_bin.tar.gz
|    |   sudo tar xzvf ./openjdk-22.0.2_linux-x64_bin.tar.gz -C ./openjdk-22.0.2 --strip-components=1
|    |
|    |   sudo nano /etc/profile
|    |   => JAVA_HOME=/usr/java/[version JDK]
|    |   => PATH=$PATH:$HOME/bin:$JAVA_HOME/bin
|    |   => export JAVA_HOME
|    |   => export JRE_HOME
|    |   => export PATH
|    |
|    +---------------------------------------------------------
|
|    +-------------------------Apache--------------------------  
|    |   
|    |   sudo apt install apache2
|    |   sudo ufw allow 'Apache'
|    |   sudo mkdir /var/www/gmao
|    |
|    |   *(Start.sh déplacera automatiquement les fichiers dans le rep)
|    |
|    |   cd /etc/apache2/sites-available/
|    |   sudo cp 000-default.conf gmao.conf
|    | 
|    |   sudo nano gmao.conf
|    |   *Remplacer la ligne commencant par "DocumentRoot" par "DocumentRoot /var/www/gmao"
|    |
|    |   sudo a2ensite gmao.conf
|    |   sudo a2dissite 000-default.conf
|    |   sudo systemctl reload apache2
|    |
|    +---------------------------------------------------------
|
|    +-------------------------Tomcat--------------------------  
|    |
|    |   !!! Ne pas utiliser une version de tomcat supérieure à 9 car Spring 5 ne supporte pas Jakarta
|    |
|    |   sudo groupadd tomcat
|    |   sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat
|    |   cd /tmp
|    |   wget -c https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.94/bin/apache-tomcat-9.0.94.tar.gz
|    |
|    |   sudo mkdir /opt/tomcat
|    |   cd /opt/tomcat
|    |   sudo tar xzvf /tmp/apache-tomcat-9.0.94.tar.gz -C /opt/tomcat --strip-components=1
|    |
|    |   cd /opt
|    |   sudo chown -R tomcat: tomcat
|    |   cd ./tomcat
|    |   sudo chown -R tomcat webapps/ work/ temp/ logs/ conf/
|    |   sudo chmod o+x /opt/tomcat/bin/
|    |
|    |   sudo cp /home/$USER/Bureau/SAE-52/Serveur/tomcat.service /etc/systemd/system/tomcat.service
|    |   sudo nano /etc/systemd/system/tomcat.service
|    |   *Modifier cette ligne "Environment=JAVA_HOME=/usr/java[VERSION JDK]" en changeant "[VERSION JDK]"
|    |   => vous pouvez trouver la version en tapant les commandes suivantes :
|    |   ==> cd /usr/java
|    |   ==> ls                                                               (prendre le nom du dossier)
|    |
|    |   sudo ufw allow 8080
|    |   sudo ufw allow 8443
|    |
|    |   sudo systemctl daemon-reload
|    |
|    |   sudo systemctl start tomcat
|    |   *Test fonctionnement "localhost:8080"
|    |
|    |   sudo nano /opt/tomcat/conf/tomcat-users.xml
|    |
|    |   *Ajouter "<role rolename="admin-gui"/><role rolename="manager-gui"/><user username="admin" password="leffe" roles="admin-gui,manager-gui,manager-script"/>"
|    |   => entre les deux balises "<tomcat-users>"
|    |
|    |   sudo systemctl disable tomcat 
|    |   sudo systemctl stop tomcat                                                     (une fois les test terminés)
|    |   
|    |   *Il est nécéssaire de passer par "localhost:8080" afin d'accéder à l'interface admin
|    |
|    +---------------------------------------------------------
|
|    +------------------------NetBEANS------------------------- 
|    |
|    |   sudo snap install netbeans --classic
|    |
|    |   sudo netbeans --jdkhome /usr/java/[version jdk]
|    |
|    |   *Ouvrir le projet (il faut cloner le projet avant) : /home/sae-52/Bureau/SAE-52/NetBEANS/SAE52  
|    |
|    |   *Ajout serveur Tomcat
|    |   Tools > Server > Apache Tomcat or TomEE > Server location : "/opt/tomcat/" | username : "admin | login : "leffe"
|    |
|    |   *Importer driver JDBC PostgreSQL
|    |   => File > projet properties > libraries > add library > PostgreSQL JDBC library
|    |
|    |   *Lancer NetBEANS (obligatoire)
|    |   => sudo netbeans --jdkhome /usr/java/[version JDK]
|    |
|    +---------------------------------------------------------
|
|    +---------------------Certificat SSL---------------------- 
|    |
|    |   sudo mkdir /certs
|    |   cd /certs
|    |
|    |   *Entrer un MDP (ici leffe) et les informations demandées (peu importe le contenu)
|    |   sudo openssl req -x509 -nodes -days 10000 -newkey rsa:4096 -keyout /certs/SAE52.key -out /certs/SAE52.crt
|    |
|    |   *Apache
|    |   sudo nano /etc/apache2/sites-available/gmao.conf
|    |   => 1ère ligne : remplacer le port 80 par 443
|    |   *Ajouter les lignes suivantes :
|    |   ==> SSLEngine on
|    |   ==> SSLCertificateFile /certs/SAE52.crt
|    |   ==> SSLCertificateKeyFile /certs/SAE52.key
|    |
|    |   sudo a2enmod ssl
|    |   sudo systemctl daemon-reload
|    |   sudo systemctl restart apache2
|    |
|    |   *Tomcat (à finir)
|    |   cd /certs
|    |   sudo openssl pkcs12 -export -in SAE52.crt -inkey SAE52.key -out SAE52.p12 -name tomcat
|    |   => MDP : leffe
|    |   
|    |   sudo /usr/java/[version JDK] bin/keytool -importkeystore -deststorepass administrateur -destkeystore /opt/tomcat/conf/tomcat.keystore -srckeystore SAE52.p12 -srcstoretype PKCS12 -srcstorepass leffe -alias tomcat
|    |   => MDP keytool "administrateur"
|    |
|    |   sudo cp /home/sae-52/Bureau/SAE-52/Serveur/server.xml /opt/tomcat/conf/server.xml
|    |
|    +---------------------------------------------------------
|
|    +---------------------Ajout Certificat-------------------- 
|    |
|    |   *Le certificat (de la VM) est disponible dans le dossier "Serveur" => "SAE52.crt"
|    |
|    |   *Linux
|    |   cd /usr/local/share/ca-certificates/
|    |   sudo mkdir ./SSL
|    |   sudo chmod 755 ./SSL/
|    |
|    |   cp /certs/SAE52.crt /usr/local/share/ca-certificates/SSL/SAE52.crt
|    |   sudo chmod 644 ./SSL/SAE52.crt
|    |
|    |   sudo update-ca-certificates
|    |
|    |   *Windows (flemme)
|    |   https://support.securly.com/hc/en-us/articles/360026808753-How-do-I-manually-install-the-Securly-SSL-certificate-on-Windows
|    |
|    +---------------------------------------------------------
|
+---------------------------------------------------------
