#!/bin/bash

mkdir webappsTomcat -p
mkdir target -p

# Verifica se o container existe
if docker ps -a --format '{{.Names}}' | grep -q "Tomcat" && docker ps -a --format '{{.Names}}' | grep -q "Postgres"; then
    echo "Ambos os containers Tomcat e Postgres foram criados."
else
    echo "Um ou ambos os containers não foram criados.\nCriando-os"
    docker compose up -d
fi

if ! command -v inotifywait >/dev/null 2>&1; then
    echo "O inotify-tools não está instalado."
    sudo apt-get update
    sudo apt-get install inotify-tools -y

fi


# Diretórios de origem e destino
origem="./target/"
destino="./webappsTomcat/"

# Monitora o diretório de origem para alterações em arquivos .war
inotifywait -m -e close_write --format "%w%f" "$origem" | while read -r arquivo
do
    if [[ "$arquivo" == *.war ]]; then
        echo "Arquivo $arquivo alterado. Copiando para $destino"
        cp "$arquivo" "$destino"
    fi
done