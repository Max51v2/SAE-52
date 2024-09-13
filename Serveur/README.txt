Auteur : Maxime VALLET
Version : 0.8
Status : en cours

à faire : 
    - configurer Tomcat afin d'ajouter le contenu du dossier "Web"
    - installer et configurer NetBEANS + intégration Tomcat + JDBC (driver...)
    - installer et configurer Tomcat + NetBEANS sur la VM (Maxime)
    - envoyer la VM (Maxime)
    - Servlets + BD
    - Web

+--------------------------VM-----------------------------
|
|    +-------------------------Général-------------------------  
|    |
|    |  Lien VM : TBD
|    |  MDP : leffe
|    |
|    |  *Demarrer les daemons
|    |  /home/$USER/Bureau/SAE-52/Serveur/Start.sh
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
|    |   Cloner un répertoire Github :
|    |   Cliquer sur l'onglet "Explorer" (pages), cliquer sur "Clone repository" > "Clone from Github" > "Max51v2/SAE-52" > Bureau (il faut être contributeur dans le projet tq rep privé)
|    |
|    |   *Modifier le nom et l'@ mail
|    |   git config --global user.name "[Prenom Nom]"
|    |   git config --global user.email "[@ Mail]"
|    |
|    |   *Pour actualiser le code dans le dossier Web sur le serveur apache, il faut tourner Start.sh
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
|    |   sudo snap install openjdk
|    |
|    |   cd /usr/java
|    |   *Récuppérer le numéro de version avec "ls"
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
|    +-------------------Apache (inutilisé)--------------------  
|    |   
|    |   sudo apt install apache2
|    |   sudo ufw allow 'Apache'
|    |   sudo mkdir /var/www/gmao
|    |   *(Start.sh déplacera automatiquement les fichiers dans le rep)
|    |
|    |   cd /etc/apache2/sites-available/
|    |   sudo cp 000-default.conf gmao.conf
|    |   sudo nano gmao.conf
|    |
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
|    |   sudo groupadd tomcat
|    |   sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat
|    |   cd /tmp
|    |   wget -c https://dlcdn.apache.org/tomcat/tomcat-10/v10.1.29/bin/apache-tomcat-10.1.29.tar.gz
|    |
|    |   sudo mkdir /opt/tomcat
|    |   cd /opt/tomcat
|    |   sudo tar xzvf /tmp/apache-tomcat-10.1.29.tar.gz -C /opt/tomcat --strip-components=1
|    |
|    |   cd /opt
|    |   sudo chown -R tomcat: tomcat
|    |   cd ./Tomcat
|    |   sudo chown -R tomcat webapps/ work/ temp/ logs/
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
|    |
|    |   sudo systemctl daemon-reload
|    |
|    |   sudo systemctl start tomcat
|    |
|    +---------------------------------------------------------
|
|    +------------------------NetBEANS------------------------- 
|    |   
|    |   
|    +---------------------------------------------------------
|
+---------------------------------------------------------