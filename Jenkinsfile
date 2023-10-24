pipeline {
    agent none
    stages {
        stage('Build') {
            agent any
            steps {
                echo 'Hello, Maven'
                pwsh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Run') {
            agent any
            steps {
                echo 'Hello, JDK'
                pwsh 'java -jar target/esm-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}
