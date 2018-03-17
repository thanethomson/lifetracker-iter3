#!/bin/bash
set -e

if [ -z "$1" ] || [ -z "$2" ]; then
    echo "Usage: ./run-compose.sh <lifetracker_db_password> <postgres_password> [optional_compose_cmd]"
    exit 1
fi

if [ -z "${IMAGE_TAG}" ]; then
    export IMAGE_TAG="0.1.0-stretch"
fi

if [ -z "$3" ]; then
    export COMPOSE_CMD=up
else
    export COMPOSE_CMD=$3
fi

IMAGE_TAG=${IMAGE_TAG} \
    LIFETRACKER_DATABASE_PASSWORD=$1 \
    POSTGRES_PASSWORD=$2 \
    docker-compose \
    $COMPOSE_CMD

