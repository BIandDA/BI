#!/bin/bash -e
application=${1:-target-app}
bucket=${2:-${application}-bundles}
tag=$(date +%F)-$(git rev-parse --short HEAD)
bundle=${application}-${tag}.zip

check_version=$(aws elasticbeanstalk describe-application-versions --application-name ${application} --version-label ${tag} --query "ApplicationVersions[0]")

if [ "${check_version}" = "null" ];
then
    git archive HEAD -o /tmp/${bundle}

    aws s3 cp /tmp/${bundle} s3://${bucket}

    aws elasticbeanstalk create-application-version \
        --application-name ${application} \
        --version-label ${tag}  \
        --source-bundle S3Bucket="${bucket}",S3Key="${bundle}" \
        --no-auto-create-application
else
    echo "Version ${tag} already uploaded."
fi
