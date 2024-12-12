#!/bin/bash

if [ -e restart ]; then
    # Copie des fichiers de config (pour une raison obscure ils sont reset après le build de l'image)
    cd /conf
    cp pg_hba.conf /etc/postgresql/15/main/pg_hba.conf
    cp postgresql.conf /etc/postgresql/15/main/postgresql.conf

    pg_ctl restart
else

    postgres
fi