#!/bin/bash
set -o errexit
set -o nounset

deploy(){

    echo "Deploying step 2..."

    cd deploy;
    BRANCH=`cat branch`
    echo "Branch: $BRANCH"

    echo "Stoping service..."
    sudo service inteligas stop

    echo "Replace jar..."
    mv upload/inteligas_api-0.0.1-SNAPSHOT.jar inteligas_api-0.0.1-SNAPSHOT.jar

    echo "Reload daemon..."
    sudo systemctl daemon-reload

    [ -e inteligas.log ] && rm inteligas.log;
    echo "Starting spring boot app..."
    sudo service inteligas start
    sleep 5;

    tail -f inteligas.log | while read LOGLINE
    do
        echo "${LOGLINE}"

        if [[ "${LOGLINE}" == *"Tomcat started"* ]]
        then
            echo "App started succesfully!"
            pkill -P $$ tail
        elif [[ "${LOGLINE}" == *"APPLICATION FAILED TO START"* ]] ||
            [[ "${LOGLINE}" == *"Application startup failed"* ]]
        then
            echo "ERROR starting app"
            pkill -P $$ tail
            exit 1;
        fi

    done

}

deploy

exit 0