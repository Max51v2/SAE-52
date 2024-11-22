En cas de problèmes d'affichage, merci d'ouvrir la doc depuis le projet et de séléctionner l'affichage de type "raw"

I) Présentation
 à faire

II) Comment installer le projet ?
 1) Installation et configuration des programmes
  a) Utiliser la VM contenant tous les programmes configurés :
    - Vous retrouverez les informations utiles dans la section "VM" du document situé ici : "/Serveur/README.txt"
    - Il est recommandé de procéder dans cet ordre : télécharger la VM > suivre les README donnés à chaque partie de l'installation > lire la partie "VM" du document suivant : "/Serveur/README.txt"
  b) Installer les programmes manuellement :
    - Vous retrouverez les informations dans la section "VM" du document situé ici : "/Serveur/README.txt"

 2) Démarrer le projet
    - Lancez Start.sh puis saisissez "o" pour reconstruire la BD (à faire lors du premier démarrage ou à chaque commit) puis "o" pour lancer NetBeans
    - Lancez le projet Java ouvert dans NetBeans et saisissez les logins+MDP si besoin (voir doc "/Serveur/README.txt")
    - Ouvrir le navigateur de la VM ou le navigateur de l'OS hôte (nécéssite d'ajouter les certificats > voir doc "/Serveur/README.txt")

III) Qui contacter en cas de question ?
 - Serveurs, VM, Authentification et gestion des utilisateurs : Maxime VALLET
 - HTML, CSS et tickets : Ishac HAMDANI
 - JS et Gestion du matériel : Valentin MILLOT




!!! SECTION À RETIRER AVANT LE RENDU !!!

Checklist des choses à faire avant rendu :
    - rajoutez votre nom là où vous avez contribué
    - finir la doc


A faire :
- faire un menu déroulant (ça doit être mieux)
- afficher le nom du compte connecté
- page technicien à faire, il doit pouvoir voir les demandes d'équipements (uniquement les voir) + pareil pour les demandes support mais sous forme de listing
- page visualisation des équipements
    

Fait :
- ajout utilisateur + afficher utilisateurs + suppression utilisateur
- Redirection => gmao.local
- les objets doivent être dans une table qui se situe dans la BD "sae_52" (4 -> 1 pour chaque type d'équipement)
-  ̶f̶a̶i̶r̶e̶ ̶l̶a̶ ̶p̶a̶g̶e̶ ̶p̶o̶u̶r̶ ̶l̶e̶ ̶p̶l̶a̶n̶n̶i̶n̶g̶   (Trop ambitieux, peur que l'on manque de temps => oui)
- DAO : ajouter + lister + supprimer => SW + Routeur + cable + PC (suppression)
- Servlet : ajouter + lister + supprimer => SW + Routeur + cable + PC
- tests DAO (pas les servlets car ça prendrait ENORMEMENT de temps au vu du nombre de servlets)
