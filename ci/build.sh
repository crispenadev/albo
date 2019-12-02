#!/bin/bash
set -o errexit
set -o nounset

RAMA=`git branch -a| grep \* | cut -d ' ' -f2`;

echo "Rama->${RAMA}";

if [[ "${RAMA}" == *"HEAD"* ]] || [[ "${RAMA}" == *"detached"* ]]; then
  RAMA=${TRAVIS_BRANCH}
fi

echo "Current branch is: ${RAMA}";

if [ "${RAMA}" = "master" ]; then
  BRANCH=master
  DOMAIN=api.inteligas.mx
elif [[ "${RAMA}" == "uat" ]]; then
  BRANCH=uat
  DOMAIN=api.uat.inteligas.mx
else
  BRANCH=develop
  DOMAIN=api.dev.inteligas.mx
fi

echo "$BRANCH env detected: $DOMAIN";
echo $BRANCH > branch
echo $DOMAIN > domain

build_api(){
    echo "Fetching changes..."
	git pull;
    mvn clean package -DskipTests;
    scp -i "deploy_rsa" target/inteligas_api-0.0.1-SNAPSHOT.jar ubuntu@"$DOMAIN":/home/ubuntu/deploy/upload
    scp -i "deploy_rsa" branch ubuntu@"$DOMAIN":/home/ubuntu/deploy
}

build_api

exit $?
