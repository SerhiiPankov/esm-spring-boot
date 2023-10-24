pipeline {
    agent {docker 'oraclelinux:8-slim'}
    stages {
        stage('Build') {
            agent { docker 'maven:3.8.3-openjdk-17' }
            steps {
                echo 'Hello, Maven'
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Run') {
            agent any
            steps {
                echo 'Hello, JDK'
                sh 'java -jar target/esm-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}
