#!/bin/sh -xe
# Requirements (Stage 1)
pip install --quiet --upgrade pip
pip install --quiet --requirement requirements.txt

# Checks (Stage 2)
flake8 --ignore=E501,E231 *.py tests/*.py
pylint --disable=C0301 --disable=C0326 *.py tests/*.py
python -m unittest --verbose --failfast
