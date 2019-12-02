#!/bin/bash
set -o errexit
set -o nounset

stop(){

    echo "Stoping spring boot app..."
    sudo service inteligas stop
    sleep 5;

}

stop

exit 0