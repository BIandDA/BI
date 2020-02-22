FROM python:3.6

LABEL "com.github.actions.name"="Deploy"
LABEL "com.github.actions.description"="Deploy the application"

ADD entrypoint.sh /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
