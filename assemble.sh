#!/bin/bash
set -o errexit
set -o nounset

RAMA=`git branch -a| grep \* | cut -d ' ' -f2`;

echo "Rama->${RAMA}";

build_api(){
    echo "Fetching changes..."
    git pull origin develop;
    mvn clean package -DskipTests;
    echo "Finish SCPs..."
}

build_api

exit $?


