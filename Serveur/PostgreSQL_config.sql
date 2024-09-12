-- INACHEVE

--Auteur : Maxime VALLET
--Version : 0.2

--Base de données du projet
CREATE DATABASE sae_52;
\c sae_52

--Table contenant les MDP hashés des utilisateurs
CREATE TABLE Password_Hash_SHA512 (
    id in UNIQUE,
    nom text,
    prenom text,
    hash text
);

--Compte admin par défaut (MDP "leffe")
INSERT INTO Password_Hash_SHA512 (1 ,"Admin", "Originel", 
"4f8152e4b53af9906822eb92e4a6c1a6651c2fcfc690847a3e76cb27d4b3e0659ecc72bbc3ed22aa561a4f95edf39046d0e1933bea44e185feab12ec8cf4ed03"
);