#!/bin/bash
docker stop kitchen-postgres
docker rm kitchen-postgres
docker run --name kitchen-postgres -e POSTGRES_DB=kitchen_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres

