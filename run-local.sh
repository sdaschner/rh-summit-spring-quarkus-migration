#!/bin/bash
set -euo pipefail

# requires docker network to allow local communication via container names
#docker network create --subnet=192.168.42.0/24 dkrnet

trap cleanup EXIT

function cleanup() {
  docker stop kaffeehaus kaffeehaus-db &> /dev/null || true
}



cleanup

docker run -d --rm \
  --name kaffeehaus-db \
  --network dkrnet \
  -p 5432:5432 \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  postgres:15.2

sleep 3
docker run --rm \
  --name kaffeehaus \
  --network dkrnet \
  -p 8080:8080 \
  kaffeehaus
