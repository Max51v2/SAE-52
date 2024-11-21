--Auteur : Maxime VALLET, Valentin MILLOT, Ishac HAMDANI
--Version : 1.0


--####################### BD sae_52 #######################

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
INSERT INTO users (login, nom, prenom, role, hash, token) VALUES ('Admin1', 'Originel', 'Admin', 'Admin', 'e3afed0047b08059d0fada10f400c1e5','');
--Compte admin par défaut (MDP "Technicien")
INSERT INTO users (login, nom, prenom, role, hash, token) VALUES ('Technicien1', 'Originel', 'Technicien', 'Technicien', '61c42e9e5647205c90235b3361be8ad7','');
--Compte admin par défaut (MDP "Utilisateur")
INSERT INTO users (login, nom, prenom, role, hash, token) VALUES ('Utilisateur1', 'Originel', 'Utilisateur', 'Utilisateur', 'f628ae7eac054bc61babf042677832ee','');

--Droits table contenant les MDP hashés des utilisateurs
GRANT ALL ON users TO administrateur;
GRANT USAGE, SELECT ON SEQUENCE users_id_seq TO administrateur;


-- table pc
CREATE TABLE pc (
    id SERIAL PRIMARY KEY,
    processor text,
    ram text,
    mac_address text,
    vlan text,
    name text,
    serial_number text,
    status text,
    other text
);


-- table routeur
CREATE TABLE router (
    id SERIAL PRIMARY KEY,
    router_ports text,
    mac_address text,
    vlan text,
    name text,
    serial_number text,
    status text
);

-- table switch
CREATE TABLE switch (
    id SERIAL PRIMARY KEY,
    switch_speed text,
    mac_address text,
    vlan text,
    name text,
    serial_number text,
    status text
);

-- table cable
CREATE TABLE cable (
    id SERIAL PRIMARY KEY,
    cable_lenght text,
    name text,
    serial_number text,
    status text
);


CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,  -- id unique et auto-incrémenté
    description TEXT NOT NULL,
    service TEXT NOT NULL,
    status TEXT DEFAULT 'En attente'  -- Valeur par défaut pour le statut
);
-- Ajouter les colonnes supplémentaires
ALTER TABLE ticket
ADD COLUMN sent_to_admin BOOLEAN DEFAULT FALSE,
ADD COLUMN admin_status VARCHAR(20) DEFAULT 'PENDING';


--######################## BD test ########################
DROP DATABASE test;

--identique à sae_52 hormis qu'il n'y a pas de contenu dans les tables !!!

--Base de données test
CREATE DATABASE test;
\c test

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

CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,  -- id unique et auto-incrémenté
    description TEXT NOT NULL,
    service TEXT NOT NULL,
    status TEXT DEFAULT 'En attente'  -- Valeur par défaut pour le statut
);
-- Ajouter les colonnes supplémentaires
ALTER TABLE ticket
ADD COLUMN sent_to_admin BOOLEAN DEFAULT FALSE,
ADD COLUMN admin_status VARCHAR(20) DEFAULT 'PENDING';


-- table pc
CREATE TABLE pc (
    id SERIAL PRIMARY KEY,
    processor text,
    ram text,
    mac_address text,
    vlan text,
    name text,
    serial_number text,
    status text,
    other text
);


-- table routeur
CREATE TABLE router (
    id SERIAL PRIMARY KEY,
    router_ports text,
    mac_address text,
    vlan text,
    name text,
    serial_number text,
    status text
);

-- table switch
CREATE TABLE switch (
    id SERIAL PRIMARY KEY,
    switch_speed text,
    mac_address text,
    vlan text,
    name text,
    serial_number text,
    status text
);

-- table cable
CREATE TABLE cable (
    id SERIAL PRIMARY KEY,
    cable_lenght text,
    name text,
    serial_number text,
    status text
);



--########################## FIN ##########################

--Msg fin
\echo
\echo
\echo Fait


