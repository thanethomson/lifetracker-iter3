#!/bin/bash
set -e

if [ -z "${LIFETRACKER_DATABASE_PASSWORD}" ]; then
    echo "Missing environment variable: LIFETRACKER_DATABASE_PASSWORD"
    exit 1
fi

gradle -Pflyway.url="jdbc:postgresql://localhost:5432/lifetracker_prod" \
    -Pflyway.user="lifetracker_prod" \
    -Pflyway.password="${LIFETRACKER_DATABASE_PASSWORD}" \
    flywayMigrate

