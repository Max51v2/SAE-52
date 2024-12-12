FROM debian:12

LABEL maintainer="max.vallet@outlook.fr"

#MAJ OS et installation de PostgreSQL v15 et du client psql
RUN apt update && apt upgrade && apt install -y postgresql-15 postgresql-client postgresql-common && apt-get clean

#Copie des fichiers de configuration
COPY postgresql.conf /etc/postgresql/15/main/postgresql.conf
COPY pg_hba.conf /etc/postgresql/15/main/pg_hba.conf
COPY PostgreSQL_config.sql /conf/PostgreSQL_config.sql

#Initialiser la base de données
RUN su - postgres -c "/usr/lib/postgresql/15/bin/initdb -D /var/lib/postgresql/data"

#Copie du script d'initialisation
COPY init.sh /docker-entrypoint-initdb.d/init.sh
RUN chmod +x /docker-entrypoint-initdb.d/init.sh

#Commande de démarrage du serveur PostgreSQL
USER postgres
CMD ["/usr/lib/postgresql/15/bin/postgres", "-D", "/var/lib/postgresql/data", "-c", "listen_addresses='*'", "-c", "port=5432", "-c", "unix_socket_directories='/var/run/postgresql'"]
