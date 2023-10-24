pipeline {
    agent none
    stages {
        stage('Build') {
            agent any
            steps {
                echo 'Hello, Maven'
                bat 'mvn -B -DskipTests clean package'
            }
        }
        stage('Run') {
            agent any
            steps {
                echo 'Hello, JDK'
                bat 'java -jar target/esm-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}
