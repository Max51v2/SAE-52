#Auteur : Maxime VALLET (SAE 51)
#Version : 1.0

# à faire : Monter le contenu de la BD PostgreSQL sur un volume partagé afin qu'il ne soit pas effacé à chaque fois que le conteneur est recréé


Installer Docker :
    Debian : 
        Vous pouvez utiliser les scripts en dessous
            => les scripts suivants s'occupent d'installer les dépendances

    Windows :
        1) Installer WSL
        2) Installer un dérivé de Debian (microsoft store)
        3) Lire les étapes pour Debian (point au dessus)



!!! Avant tout, merci de cloner le projet sur le Bureau !!!
Merci de rediriger les ports qui suivent sur la machine : 8443(Tomcat) et 50000 (Apache)
=> sudo ufw allow 50000
=> sudo ufw allow 8443
!!! Si les ports sont déjà utilisés, il y'aura une erreur !!!


Créer les conteneurs Docker :
    !!! ATTENTION !!! => si vous mettez à jour le projet java il faut build le projet dans NetBeans (icône avec le marteau et le balai)
    Debian : 
        sudo /home/$USER/Bureau/SAE-52/Serveur/Docker/Bash_Scripts/DockerBuild.sh "$USER"

Lancement des conteneurs Docker :
    Debian :
        sudo /home/$USER/Bureau/SAE-52/Serveur/Docker/Bash_Scripts/DockerStart.sh "$USER"

Arrêt des conteneurs Docker :
    Debian :
        sudo /home/$USER/Bureau/SAE-52/Serveur/Docker/Bash_Scripts/DockerStop.sh "$USER"


sudo usermod -aG docker $USER
sudo chown root:docker-group /var/run/docker.sock
sudo chmod 660 /var/run/docker.sock
#deco > reco
sudo systemctl restart docker
