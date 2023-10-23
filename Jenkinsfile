pipeline {
    agent none
    stages {
        stage('Build') {
            agent {
                dockerContainer {
                    image 'maven:3.6.3-jdk-11'
                    }
            }
            steps {
                echo 'Hello, Maven'
                bat 'mvn -B -DskipTests clean package'
            }
        }
        stage('Run') {
            agent {
                dockerContainer {
                    image 'openjdk:17-slim'
                }
            }
            steps {
                echo 'Hello, JDK'
                bat 'java -jar target/esm-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}
