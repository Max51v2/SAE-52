#Fait en aléatoire par : Maxime VALLET
#J'ai Hacksmisth des bouts de la config du projet et le rep officiel contenant le dockerfile de postgres

#OS
FROM debian:latest

LABEL maintainer="max.vallet@outlook.fr"

#MAJ OS et installation de PostgreSQL v15 et du client psql
RUN apt update && apt upgrade -y && \
    apt install -y postgresql-15 postgresql-client postgresql-common gosu sudo && \
    apt-get clean

#Variables d'environnement
ENV PG_MAJOR=15
ENV PGDATA=/var/lib/postgresql/data
ENV PATH=$PATH:/usr/lib/postgresql/$PG_MAJOR/bin

#Copie des fichiers de configuration
COPY postgresql.conf /etc/postgresql/15/main/postgresql.conf
COPY postgresql.conf /conf/postgresql.conf
COPY pg_hba.conf /etc/postgresql/15/main/pg_hba.conf
COPY pg_hba.conf /conf/pg_hba.conf
COPY PostgreSQL_config.sql /conf/PostgreSQL_config.sql
COPY docker-entrypoint.sh docker-ensure-initdb.sh /usr/local/bin/
COPY StartPSQL.sh /conf/StartPSQL.sh

#Supprimer le répertoire de données s'il existe déjà
RUN rm -rf /var/lib/postgresql/data

#Créer le répertoire de données et définir les autorisations
RUN mkdir -p /var/lib/postgresql/data && \
    chown -R postgres:postgres /var/lib/postgresql && \
    chown -R postgres:postgres /etc/postgresql/15 && \
    chown -R postgres:postgres /usr/local/bin && \
    chown -R postgres:postgres /conf && \
    chmod -R 755 /var/lib/postgresql/data

#Initialiser la base de données
RUN su - postgres -c "/usr/lib/postgresql/$PG_MAJOR/bin/initdb -D $PGDATA"

#Démarrer PostgreSQL temporairement et exécuter les commandes SQL
RUN gosu postgres /usr/lib/postgresql/$PG_MAJOR/bin/pg_ctl start -D $PGDATA && \
    psql -U postgres -d template1 -c "ALTER USER postgres WITH ENCRYPTED PASSWORD 'leffe';" && \
    PGPASSWORD='leffe' psql -U postgres -d template1 -f "/conf/PostgreSQL_config.sql" && \
    gosu postgres /usr/lib/postgresql/$PG_MAJOR/bin/pg_ctl stop -D $PGDATA

#Changement des droits des scripts
RUN chmod 777 /usr/local/bin/docker-entrypoint.sh && \
    chmod 777 /usr/local/bin/docker-ensure-initdb.sh && \
    chmod 777 /conf/StartPSQL.sh && \
    ln -s /usr/local/bin/docker-entrypoint.sh /

RUN  usermod -aG sudo postgres

#Exposer le port PostgreSQL
EXPOSE 5432

ENV PASSWORD=leffe
RUN touch /conf/restart && \
    echo "postgres:$PASSWORD" | chpasswd

#Commande par défaut
USER postgres
CMD ["/conf/StartPSQL.sh"]
