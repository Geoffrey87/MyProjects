FROM postgres:16

# Definir variáveis do banco
ENV POSTGRES_DB=myappdbv2
ENV POSTGRES_USER=myappuser
ENV POSTGRES_PASSWORD=1234567890

COPY ./init.sql /docker-entrypoint-initdb.d/

EXPOSE 5432

CMD ["postgres"]
