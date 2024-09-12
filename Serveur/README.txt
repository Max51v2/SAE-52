Auteur : Maxime VALLET
Version : 0.4


+--------------------------VM-----------------------------
|
|    +-------------------------Général-------------------------  
|    |
|    |  Lien VM : TBD
|    |  MDP : leffe
|    |
|    |  *Demarrer les daemons
|    |  cd /home/sae-52/Bureau/
|    |  ./Start
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
|    |   *Script construction BD
|    |   \i /home/sae-52/Bureau/SAE-52/Serveur/postgreSQL_config.sql
|    |
|    |   *Script reconstruction BD
|    |   \i /home/sae-52/Bureau/SAE-52/Serveur/postgreSQL_act_config.sql
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
|    |   mv [chemin_relatif]/Start.sh /home/sae-52/Bureau/
|    |   chmod u+x /home/sae-52/Bureau/Start.sh
|    |   chmod u+x ./Start.sh
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
|    |   cd /etc/postgresql/[Version PostgreSQL]/main/
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
|    |   \i /home/sae-52/Bureau/SAE-52/Serveur/postgreSQL_config.sql
|    |
|    +---------------------------------------------------------
|
|    +--------------------------JDK----------------------------  
|    |   
|    |   sudo snap install openjdk
|    |   
|    +---------------------------------------------------------
|
|    +-------------------------Apache--------------------------  
|    |   
|    |   
|    +---------------------------------------------------------
|
|    +-------------------------Tomcat--------------------------  
|    |   
|    |   
|    +---------------------------------------------------------
|
|    +------------------------NetBEANS------------------------- 
|    |   
|    |   
|    +---------------------------------------------------------
|
+---------------------------------------------------------