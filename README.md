# LifeTracker (iteration 3)

## Overview
This is the third iteration of the LifeTracker application, used for
demonstration purposes for a talk on the virtues of virtualisation (like
containerisation).

The primary requirements, as well as build/run/test instructions for local dev,
are available in the [first](https://github.com/thanethomson/lifetracker-iter1)
and [second](https://github.com/thanethomson/lifetracker-iter2) iterations'
repos. This iteration, however, does not require Ansible.

## Requirements
For the Docker build and compose, you will need:

* [Docker](https://www.docker.com/)

## Building the Image
Simply just do the following:

```bash
> cd /path/to/lifetracker-iter3/

# first build the JAR
> gradle clean build

# build the Docker image
> docker build -t lifetracker:0.1.0 .
```

## Running the Image (Manual)

### Database
First spin up a PostgreSQL container:

```bash
> docker pull postgres:9.6-alpine
> docker run --name postgres9.6 \
    -e POSTGRES_PASSWORD=postgres \
    -p 5432:5432 \
    -d \
    postgres:9.6-alpine
```

This exposes port 5432 so we can do our migration using our Gradle script.

```bash
# create the database (assumes you have a PostgreSQL client installed locally)
> psql -U postgres -W -h localhost

postgres> CREATE ROLE lifetracker WITH LOGIN PASSWORD lifetracker;
postgres> CREATE DATABASE lifetracker WITH OWNER lifetracker;
postgres> \q

# do the database migration
> gradle -Pflyway.url=jdbc:postgresql://localhost:5432/lifetracker \
    -Pflyway.user=lifetracker \
    -Pflyway.password=lifetracker \
    flywayMigrate
```

### Application
To run the application, do the following:

```bash
# runs it in the foreground (for detached mode, replace "-it" with "-d"
> docker run --name lifetracker \
    -e LIFETRACKER_DATABASE_JDBCURL="jdbc:postgresql://postgres:5432/lifetracker" \
    -p 8080:8080 \
    -it \
    lifetracker:0.1.0
```

## Running the Image (Compose)
To run the image using [Docker Compose](https://docs.docker.com/compose/),
simply do the following:

```bash
> IMAGE_TAG=0.1.0 \
    LIFETRACKER_DATABASE_PASSWORD=some-password \
    POSTGRES_PASSWORD=postgres \
    docker-compose up
```

You'll notice that, if you haven't yet created the database, your Java app will
continuously panic and restart until you do. You'll now need to create the
database and execute the migrations for it to stop panicking:

1. Navigate to [http://localhost:8000/](http://localhost:8000) - this will allow
   you to use the [adminer](https://www.adminer.org/) container specified in the
   `docker-compose.yml` file to interact with your PostgreSQL container.
2. Enter your `postgres` username and password (`postgres`), along with the
   database host being `postgres`.
3. Run your `CREATE ROLE...` and `CREATE DATABASE...` queries as before.

Then you'll need to run the migrations:

```bash
> gradle -Pflyway.url=jdbc:postgresql://localhost:5432/lifetracker \
    -Pflyway.user=lifetracker \
    -Pflyway.password=some-password \
    flywayMigrate
```

When your Java application restarts again, it should be stable and accessible
from `http://localhost:8080` (for example, try doing a GET request to
`http://localhost:8080/api/users` to interact with the `User` model).

