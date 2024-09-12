-- INACHEVE

--Auteur : Maxime VALLET
--Version : 0.2

CREATE TABLE Password_Hash_SHA512 (
    id uuid PRIMARY KEY DEFAULT UUID_GENERATE_V4(),
    nom String,
    prenom String,
    hash String
);

INSERT INTO Password_Hash_SHA512 (1, "Admin", "Originel", 
4f8152e4b53af9906822eb92e4a6c1a6651c2fcfc690847a3e76cb27d4b3e0659ecc72bbc3ed22aa561a4f95edf39046d0e1933bea44e185feab12ec8cf4ed03
)