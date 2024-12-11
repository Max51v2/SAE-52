#!/bin/bash

set -e

#Ajout du MDP de postgres + construction de la BD
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    ALTER USER postgres WITH ENCRYPTED PASSWORD 'leffe';
    \i /conf/PostgreSQL_config.sql
EOSQL
