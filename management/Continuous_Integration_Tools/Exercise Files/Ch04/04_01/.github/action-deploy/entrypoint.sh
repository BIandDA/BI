#!/bin/sh -xe
pip install --quiet --upgrade --requirement requirements.txt
sh -c "./deploy-new-version.sh $*"
sh -c "./test-environment.sh $*"
# trigger pipeline
