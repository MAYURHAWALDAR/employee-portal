pipeline {
 agent any
 tools { jdk 'JDK17'; maven 'Maven3' }
 environment {
  APP_NAME='employee-portal'; AWS_REGION='ap-south-1'; S3_BUCKET='REPLACE_WITH_YOUR_BUCKET'
  SONARQUBE_ENV='SonarQube'; TOMCAT_URL='http://REPLACE_TOMCAT_HOST:8080'; TOMCAT_CONTEXT='/employee-portal'
 }
 options { timestamps(); disableConcurrentBuilds(); buildDiscarder(logRotator(numToKeepStr: '10')) }
 stages {
  stage('Checkout') { steps { checkout scm } }
  stage('Validate') { steps { sh 'mvn -B validate' } }
  stage('Unit Test and Coverage') { steps { sh 'mvn -B clean test' } post { always { junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true; archiveArtifacts artifacts: 'target/site/jacoco/**', allowEmptyArchive: true } } }
  stage('SonarQube Analysis') { steps { withSonarQubeEnv("${SONARQUBE_ENV}") { sh 'mvn -B sonar:sonar' } } }
  stage('Quality Gate') { steps { timeout(time: 10, unit: 'MINUTES') { waitForQualityGate abortPipeline: true } } }
  stage('Package') { steps { sh 'mvn -B package -DskipTests'; sh 'sha256sum target/employee-portal.war > target/employee-portal.war.sha256' } }
  stage('Publish to S3') { steps { sh '''aws s3 cp target/employee-portal.war s3://$S3_BUCKET/$APP_NAME/$BUILD_NUMBER/employee-portal.war --region $AWS_REGION
aws s3 cp target/employee-portal.war.sha256 s3://$S3_BUCKET/$APP_NAME/$BUILD_NUMBER/employee-portal.war.sha256 --region $AWS_REGION
aws s3 cp target/employee-portal.war s3://$S3_BUCKET/$APP_NAME/latest/employee-portal.war --region $AWS_REGION''' } }
  stage('Deploy to Tomcat') { steps { withCredentials([usernamePassword(credentialsId: 'tomcat-manager-creds', usernameVariable: 'TC_USER', passwordVariable: 'TC_PASS')]) { sh '''curl --fail --silent --show-error -u "$TC_USER:$TC_PASS" -T target/employee-portal.war "$TOMCAT_URL/manager/text/deploy?path=$TOMCAT_CONTEXT&update=true"''' } } }
  stage('Smoke Test') { steps { retry(5) { sleep 5; sh 'curl --fail --silent --show-error "$TOMCAT_URL$TOMCAT_CONTEXT/health" | grep UP' } } }
 }
 post { success { archiveArtifacts artifacts: 'target/*.war,target/*.sha256', fingerprint: true } always { deleteDir() } }
}
