#!/bin/bash
#Auteur : Maxime VALLET
#Version 0.1


psql -h localhost -U postgres -d template1 -c "ALTER USER postgres WITH ENCRYPTED PASSWORD 'leffe';"
PGPASSWORD='leffe' sudo psql -h localhost -U postgres -d template1 -c "DROP DATABASE sae_52;" -f "/home/adminuser/Bureau/SAE-52/Serveur/PostgreSQL_config.sql"