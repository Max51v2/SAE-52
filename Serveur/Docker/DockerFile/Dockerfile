#OS : Debian 12 (dérivés normalement fonctionnels)
FROM debian:12

#Créé et maintenu par Maxime VALLET
LABEL maintainer="max.vallet@outlook.fr"

# Création d'un utilisateur root pour exécuter le script (dépendance du projet avec $USER, plus simple comme ça)
RUN apt update && apt upgrade && \
    apt install -y sudo && \
    useradd -m -s /bin/bash adminuser && \
    usermod -aG sudo adminuser && \
    echo "adminuser ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers
USER adminuser
WORKDIR /home/adminuser

#Ports
EXPOSE 8443
EXPOSE 443

# Installer les dépendances de base
RUN sudo apt install -y git && \
    cd ~ && \
    sudo mkdir -p Bureau && \
    sudo mkdir -p Téléchargements && \
    sudo git clone https://github.com/Max51v2/SAE-52.git /home/adminuser/Bureau/SAE-52 && \
    sudo chmod 777 /home/adminuser/Bureau/SAE-52/Serveur/Docker/Bash_Scripts/AutoInstall.sh && \
    su adminuser && \
    sudo /home/adminuser/Bureau/SAE-52/Serveur/Docker/Bash_Scripts/AutoInstall.sh 