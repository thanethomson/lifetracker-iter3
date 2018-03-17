#!/bin/bash
set -e

docker run --name postgres \
    -e POSTGRES_PASSWORD=postgres \
    -d \
    postgres:9.6-alpine

