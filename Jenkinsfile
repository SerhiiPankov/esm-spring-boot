pipeline {
    agent any

    stages {
        stage('checkout') {
            steps {
                checkout scm
            }
        }

        stage('build') {
            steps {
                 sh 'mvn clean install'
            }
        }

        stage('sonarQube') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.token=squ_20c46a789668ddb2ea360baa4237c0278a70a7a5'
            }
        }

        stage('deploy') {

            steps {
                sh 'java -jar target/esm-esm.jar'
            }
        }
    }
}
