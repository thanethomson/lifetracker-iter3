#!/bin/bash
set -e

if [ -z "${HOST}" ]; then
    export HOST=localhost
fi

if [ -z "${DB}" ]; then
    export DB=lifetracker
fi

if [ -z "${USER}" ]; then
    export USER=lifetracker
fi

if [ -z "${PASSWORD}" ]; then
    export PASSWORD=lifetracker
fi

if [ -z "${CMD}" ]; then
    export CMD=flywayMigrate
fi

gradle -Pflyway.url="jdbc:postgresql://${HOST}:5432/${DB}" \
    -Pflyway.user="${USER}" \
    -Pflyway.password="${PASSWORD}" \
    $CMD

