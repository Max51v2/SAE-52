--Auteur : Maxime VALLET
--Version : 0.4

--Base de données du projet
CREATE DATABASE sae_52;
\c sae_52

--Table contenant les MDP hashés des utilisateurs
CREATE TABLE password_Hash_MD5 (
    id SERIAL PRIMARY KEY,
    nom text,
    prenom text,
    hash text
);

--Compte admin par défaut (MDP "leffe")
INSERT INTO password_Hash_MD5 (nom, prenom, hash) VALUES ('Admin', 'Originel', '0ed4549b7fa20522eb4a81a86e334ec6');

--Droits table contenant les MDP hashés des utilisateurs
GRANT ALL ON password_hash_md5 TO administrateur;
GRANT USAGE, SELECT ON SEQUENCE password_hash_md5_id_seq TO administrateur;
--potentiel retrait selon le servlet
GRANT SELECT ON password_hash_md5 TO technicien;
GRANT SELECT ON password_hash_md5 TO utilisateur;
--

--Autres tables à faire


--Msg fin
\echo
\echo
\echo Fait