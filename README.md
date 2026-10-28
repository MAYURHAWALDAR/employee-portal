# Employee Portal CI/CD Project

Flow: GitHub -> Jenkins -> Maven/JUnit/JaCoCo -> SonarQube Quality Gate -> WAR -> Amazon S3 -> Tomcat -> Smoke Test.

## Requirements
JDK 17, Maven 3.9+, Tomcat 11, Jenkins, SonarQube, AWS CLI. This uses Jakarta Servlet 6.1. For Tomcat 9, change `jakarta.servlet` imports/dependency to `javax.servlet`.

## Build
`mvn clean test package`

## Before running Jenkins
1. In Jenkins Global Tools add JDK as `JDK17` and Maven as `Maven3`.
2. Configure SonarQube server as `SonarQube`, add its token, and create the SonarQube webhook `http://JENKINS_URL/sonarqube-webhook/`.
3. Add Jenkins username/password credential `tomcat-manager-creds`.
4. Edit `S3_BUCKET` and `TOMCAT_URL` in Jenkinsfile.
5. Install AWS CLI on the Jenkins agent and attach an EC2 IAM role allowing `s3:PutObject`, `s3:GetObject`, and `s3:ListBucket` only for your bucket/prefix.
6. In Tomcat `conf/tomcat-users.xml`, create a dedicated user with role `manager-script`. Restrict Tomcat Manager access to the Jenkins security group/IP.
7. Create a Jenkins Pipeline from SCM using your GitHub URL and script path `Jenkinsfile`.

## Upload to GitHub
`git init`
`git add .`
`git commit -m "Initial CI/CD project"`
`git branch -M main`
`git remote add origin https://github.com/YOUR_USER/employee-portal.git`
`git push -u origin main`

## Outputs
Versioned S3 artifact: `s3://BUCKET/employee-portal/BUILD_NUMBER/employee-portal.war`
Application: `http://TOMCAT:8080/employee-portal/`
Health: `http://TOMCAT:8080/employee-portal/health`
Rollback: set a previous `BUILD_NUMBER` and run `scripts/rollback.sh`.
