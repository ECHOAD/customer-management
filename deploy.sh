#!/bin/bash

if [ -z "$1" ]; then
    echo "Error: No environment specified. Use: ./deploy.sh [dev|stg]"
    exit 1
fi

ENV=$1
if [ "$ENV" == "dev" ]; then
    COMPOSE_FILE="docker-compose.dev.yml"
elif [ "$ENV" == "stg" ]; then
    COMPOSE_FILE="docker-compose.stg.yml"
else
    echo "Error: Invalid environment. Use 'dev' or 'stg'."
    exit 1
fi

echo "Compiling the project for $ENV environment..."
./mvnw clean package || { echo "Error: Compilation failed"; exit 1; }

echo "Building Docker image..."
docker build -f src/main/docker/Dockerfile.jvm -t my-quarkus-app-$ENV . || { echo "Error: Docker build failed"; exit 1; }

echo "Starting containers with $COMPOSE_FILE..."
docker-compose -f $COMPOSE_FILE up --build -d || { echo "Error: Docker Compose failed"; exit 1; }