!!! Nouvelle VM disponible (facultatif) !!!
A faire :
- faire un menu déroulant (ça doit être mieux)
- afficher le nom du compte connecté
- faire la page pour le planning
- administrateur doit pouvoir créer des rôles (C'est le JS qui check le rôle envoyé par le servlet donc modif JS quand tu ajoute un rôle !!!)
    => réponse ?
- page technicien à faire, il doit pouvoir voir les demandes d'équipements (uniquement les voir) + pareil pour les demandes support mais sous forme de listing
- page visualisation des équipements
- DAO : ajouter + lister + supprimer => SW + Routeur + cable + PC (suppression)
- Servlet : ajouter + lister + supprimer => SW + Routeur + cable + PC
- tests :
    - Servlets : tout
    - DAO : addPC
    

Fait :
- Mise à jour de la page principale d'apache (voir rubrique "VM" > "Modifications A LIRE" du README Serveur)
- Ajout du logo (Rappel : il faut modifier Start.sh (ligne 30) pour ajouter toute ressource sur le serveur Apache)
- ajout utilisateur + afficher utilisateurs + suppression utilisateur
- Redirection => gmao.local
- les objets doivent être dans une table qui se situe dans la BD "sae_52" (4 -> 1 pour chaque type d'équipement)
