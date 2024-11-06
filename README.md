A faire :
- faire un menu déroulant (ça doit être mieux)
- afficher le nom du compte connecté
- administrateur doit pouvoir créer des rôles (C'est le JS qui check le rôle envoyé par le servlet donc modif JS quand tu ajoute un rôle !!!)
    => réponse ?
- page technicien à faire, il doit pouvoir voir les demandes d'équipements (uniquement les voir) + pareil pour les demandes support mais sous forme de listing
- page visualisation des équipements
- tests :
    - Servlets : tout
    - DAO : addPC
    

Fait :
- ajout utilisateur + afficher utilisateurs + suppression utilisateur
- Redirection => gmao.local
- les objets doivent être dans une table qui se situe dans la BD "sae_52" (4 -> 1 pour chaque type d'équipement)
-  ̶f̶a̶i̶r̶e̶ ̶l̶a̶ ̶p̶a̶g̶e̶ ̶p̶o̶u̶r̶ ̶l̶e̶ ̶p̶l̶a̶n̶n̶i̶n̶g̶   (Trop ambitieux, peur que l'on manque de temps => oui)
- DAO : ajouter + lister + supprimer => SW + Routeur + cable + PC (suppression)
- Servlet : ajouter + lister + supprimer => SW + Routeur + cable + PC
