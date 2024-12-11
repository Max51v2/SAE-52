#Auteur : Maxime VALLET (SAE 51)
#Version : 1.0
#Objectif : création du docker du projet

#à finir : voir "AutoInstall.sh" et "Start.sh"

#compléments d'info : 
- "AutoInstall.sh"
    => lancer à l'installation du projet
- "Start.sh" lance le projet automatiquement au démarrage
    => lancé au démarrage de la distro linux (basé sur Debian !!!)

#Installation et configuration du projet
cd ~
sudo mkdir -p Bureau
sudo mkdir -p Téléchargements
sudo apt install gh
sudo apt install git
gh repo clone Max51v2/SAE-52 /home/$USER/Bureau/SAE-52
/home/$USER/Bureau/SAE-52/Serveur/Docker/Bash_Scripts/AutoInstall.sh

Installer Docker :
    Debian : 
        Télécharger le paquet Deb => https://docs.docker.com/desktop/setup/install/linux/ubuntu/
        sudo apt-get install apt install docker-compose
    Windows :
        1) Installer WSL
        2) Installer un dérivé de Debian
        3) Réaliser les étapes pour Debian (point au dessus)

Créer l'image Docker :
    !!! ATTENTION !!! => si vous mettez à jour le projet java il faut build le projet dans NetBeans (icône avec le marteau et le balai)
    Debian : 
        sudo apt install docker-buildx
        docker build -t SAE52 [chemin]/SAE-52/Serveur/Docker/DockerFile

Lancement de l'image Docker :
    docker run -p 8443:8443 -p 443:443 SAE52