#Auteur : Maxime VALLET (SAE 51)
#Version : 1.0
#Objectif : création du docker du projet

#à finir : voir "AutoInstall.sh" et "Start.sh"

#compléments d'info : 
- "AutoInstall.sh"
    => lancer à l'installation du projet
- "Start.sh" lance le projet automatiquement au démarrage
    => lancer au démarrage de la distro linux (basé sur Debian !!!)

#Installation et configuration du projet
sudo apt install gh
sudo apt install git
gh repo clone Max51v2/SAE-51 /home/$USER/Bureau/SAE-52
/home/$USER/Bureau/SAE-51/Serveur/SAE-51/AutoInstall.sh