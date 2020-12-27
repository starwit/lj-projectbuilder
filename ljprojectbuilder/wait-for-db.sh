#!/bin/sh
# wait-for-postgres.sh

set -e
while ! nc -z db 3306;
do
  echo sleeping;
  sleep 1;
done;
echo Connected!;
