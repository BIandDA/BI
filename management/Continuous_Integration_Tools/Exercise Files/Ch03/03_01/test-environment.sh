#!/bin/bash -e
environment=${1:-staging}
site="http://${environment}.aawrp5rccf.us-west-2.elasticbeanstalk.com/"

code=$(curl -sL --max-time 3 -o /dev/null -w "%{http_code}" $site)

echo "Site: $site"
echo "Expected '200'; Received: $code"

if [ "$code" != "200" ];
then
    exit 1
fi
