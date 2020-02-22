#!/bin/sh -xe
pip install --quiet --upgrade --requirement requirements.txt
sh -c "./test-environment.sh $*"
