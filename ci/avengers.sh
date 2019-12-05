#!/bin/bash

#!/bin/bash
nohup java -jar target/test-0.0.1-SNAPSHOT.jar > ${\logs}\log.txt 2>&1 &
echo $! > ${\logs}\pid.file



