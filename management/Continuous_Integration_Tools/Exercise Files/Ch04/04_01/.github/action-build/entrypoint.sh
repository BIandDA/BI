#!/bin/sh -xe
pip install --quiet --upgrade --requirement requirements.txt
./upload-new-version.sh
