pipeline {
    agent none
    stages {
        stage('Build') {
            agent { dockerContainer 'maven:3.8.5-jdk-11'}
            steps {
                echo 'Hello, Maven'
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Run') {
            agent { dockerContainer 'openjdk:17-slim'}
            steps {
                echo 'Hello, JDK'
                sh 'java -jar target/esm-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}
