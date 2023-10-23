pipeline {
    agent none
    stages {
        stage('Build') {
            agent {
                docker {
                    image 'maven:3.6.3-jdk-11'
                    }
            }
            steps {
                echo 'Hello, Maven'
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Run') {
            agent {
                docker {
                    image 'openjdk:17-slim'
                }
            }
            steps {
                echo 'Hello, JDK'
                sh 'java -jar target/esm-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}
