#!/bin/bash

# Nome do container
container_name="Tomcat"

# Verifica se o container existe
if docker ps -a --format '{{.Names}}' | grep -q "^$container_name$"; then
    # O container existe, então o iniciamos
    echo "Iniciando o container $container_name..."
    docker start $container_name
else
    # O container não existe, então o criamos
    echo "Criando e iniciando o container $container_name..."
    docker run -d -p 8080:8080 -v "$(pwd)/target:/usr/local/tomcat/webapps" --name $container_name --pull missing tomcat:latest
fi
