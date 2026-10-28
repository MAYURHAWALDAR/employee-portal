#!/usr/bin/env bash
set -euo pipefail
: "${S3_BUCKET:?}" "${BUILD_NUMBER:?}" "${TOMCAT_URL:?}" "${TC_USER:?}" "${TC_PASS:?}"
aws s3 cp "s3://${S3_BUCKET}/employee-portal/${BUILD_NUMBER}/employee-portal.war" /tmp/employee-portal.war
curl --fail -u "$TC_USER:$TC_PASS" -T /tmp/employee-portal.war "${TOMCAT_URL}/manager/text/deploy?path=/employee-portal&update=true"
curl --fail "${TOMCAT_URL}/employee-portal/health"
