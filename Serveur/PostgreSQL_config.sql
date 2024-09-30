--Auteur : Maxime VALLET
--Version : 0.4

--Base de données du projet
CREATE DATABASE sae_52;
\c sae_52

--Table contenant les informations sur les utilisateurs (MDP hash : MD5)
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    login text,
    nom text,
    prenom text,
    role text,
    hash text,
    token text
);

--Compte admin par défaut (MDP "Admin")
INSERT INTO users (login, nom, prenom, role, hash, token) VALUES ('Admin1', 'Admin', 'Originel', 'Admin', 'e3afed0047b08059d0fada10f400c1e5','');
--Compte admin par défaut (MDP "Technicien")
INSERT INTO users (login, nom, prenom, role, hash, token) VALUES ('Technicien1', 'Technicien', 'Originel', 'Technicien', '61c42e9e5647205c90235b3361be8ad7','');
--Compte admin par défaut (MDP "Utilisateur")
INSERT INTO users (login, nom, prenom, role, hash, token) VALUES ('Utilisateur1', 'Utilisateur', 'Originel', 'Utilisateur', 'f628ae7eac054bc61babf042677832ee','');

--Droits table contenant les MDP hashés des utilisateurs
GRANT ALL ON users TO administrateur;
GRANT USAGE, SELECT ON SEQUENCE users_id_seq TO administrateur;

--Autres tables à faire

-- table pc
CREATE TABLE pc (
    processeur text,
    ram text,
    mac text,
    vlan text,
    nom text,
    sn text,
    Etat text
);


-- table routeur
CREATE TABLE routeur (
    ndp text,
    mac text,
    vlan text,
    nom text,
    sn text,
    Etat text
);

-- table switch
CREATE TABLE switch (
    speed text,
    mac text,
    vlan text,
    nom text,
    sn text,
    Etat text
);

-- table cable
CREATE TABLE cable (
    meter text,
    nom text,
    sn text,
    Etat text
);

--Msg fin
\echo
\echo
\echo Fait


