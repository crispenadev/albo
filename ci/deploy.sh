#!/bin/bash
set -o errexit
set -o nounset

deploy(){

    echo "Deploying step 1..."

    DOMAIN=`cat domain`
    echo "Domain: $DOMAIN"

    ssh -i "deploy_rsa" ubuntu@"$DOMAIN" 'bash -s' < ci/deploy_2.sh
    echo "Done..."

}

deploy

exit 0