#Auteur : Maxime VALLET (SAE 51)
#Version : 1.0
#Objectif : création du docker du projet


Installer Docker :
    Debian : 
        sudo apt-get install apt install docker-compose

    Windows :
        1) Installer WSL
        2) Installer un dérivé de Debian
        3) Réaliser les étapes pour Debian (point au dessus)


Avant tout, merci de cloner le projet sur le Bureau !!!
Merci de rediriger les ports qui suivent sur la machine : 8443(Tomcat) et 50000 (Apache)
=> sudo ufw allow 50000
=> sudo ufw allow 8443

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