FROM python:3.6

LABEL "com.github.actions.name"="Build"
LABEL "com.github.actions.description"="Create new application version"

ADD entrypoint.sh /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
