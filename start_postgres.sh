#!/bin/bash

# Nome do container
container_name="Postgres"

# Verifica se o container existe
if docker ps -a --format '{{.Names}}' | grep -q "^$container_name$"; then
    # O container existe, então o iniciamos
    echo "Iniciando o container $container_name..."
    docker start $container_name
else
    # O container não existe, então o criamos
    echo "Criando e iniciando o container $container_name..."
    docker run -d -p 5432:5432 --name $container_name -e POSTGRES_PASSWORD=postgres --pull missing postgres:15-alpine
fi