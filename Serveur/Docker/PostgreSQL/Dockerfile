FROM debian:latest

LABEL maintainer="max.vallet@outlook.fr"

# MAJ OS et installation de PostgreSQL v15 et du client psql
RUN apt update && apt upgrade -y \
    && apt install -y postgresql-15 postgresql-client postgresql-common gosu \
    && apt-get clean

# Variables d'environnement
ENV PG_MAJOR=15
ENV PGDATA=/var/lib/postgresql/data
ENV PATH=$PATH:/usr/lib/postgresql/$PG_MAJOR/bin

# Copie des fichiers de configuration
COPY postgresql.conf /etc/postgresql/15/main/postgresql.conf
COPY postgresql.conf /var/lib/postgresql/data/postgresql.conf
COPY pg_hba.conf /etc/postgresql/15/main/pg_hba.conf
COPY PostgreSQL_config.sql /conf/PostgreSQL_config.sql
COPY docker-entrypoint.sh docker-ensure-initdb.sh /usr/local/bin/

# Initialiser la base de données
RUN su - postgres -c "/usr/lib/postgresql/$PG_MAJOR/bin/initdb -D $PGDATA"

# Démarrer PostgreSQL temporairement et exécuter les commandes SQL
RUN gosu postgres /usr/lib/postgresql/$PG_MAJOR/bin/pg_ctl start -D $PGDATA \
    && PGPASSWORD='leffe' psql -U postgres -d template1 -c "ALTER USER postgres WITH ENCRYPTED PASSWORD 'leffe';" \
    && PGPASSWORD='leffe' psql -U postgres -d template1 -f "/conf/PostgreSQL_config.sql" \
    && gosu postgres /usr/lib/postgresql/$PG_MAJOR/bin/pg_ctl stop -D $PGDATA

# Changer les droits sur le script d'entrée
RUN chmod 777 /usr/local/bin/docker-entrypoint.sh \
    && ln -s /usr/local/bin/docker-entrypoint.sh /

# Exposer le port PostgreSQL
EXPOSE 5432

# Commande par défaut
USER postgres
CMD ["postgres", "-c", "listen_addresses=*"]
