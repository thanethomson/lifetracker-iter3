#!/bin/bash
set -e

if [ -z "$1" ]; then
    echo "Usage: ./run-lifetracker.sh <docker-image-version>"
    echo "e.g. ./run-lifetracker.sh 0.1.0-stretch"
    exit 1
fi

docker run --name lifetracker \
    --link postgres9.6:postgres \
    -e SPRING_PROFILES_ACTIVE=prod \
    -e LIFETRACKER_DATABASE_JDBCURL="jdbc:postgresql://postgres:5432/lifetracker_prod" \
    -e LIFETRACKER_DATABASE_USERNAME="lifetracker_prod" \
    -e LIFETRACKER_DATABASE_PASSWORD="some-mean-password" \
    -p 8080:8080 \
    -it \
    "lifetracker:$1"

