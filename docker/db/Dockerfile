FROM postgres

ENV POSTGRES_PASSWORD 'postgres'
ENV POSTGRES_USER 'postgres'
ENV POSTGRES_DB 'journal'

COPY init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 5432