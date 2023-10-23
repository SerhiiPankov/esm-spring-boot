pipeline {
    agent none
    stages {
        stage('Build') {
            agent { docker 'maven:3.8.5-jdk-1111' }
            steps {
                echo 'Hello, Maven'
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Run') {
            agent { docker 'openjdk:18.0.1.1-jdk-slim' }
            steps {
                echo 'Hello, JDK'
                sh 'java -jar target/esm-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}
