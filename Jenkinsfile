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
                withSonarQubeEnv('sonar') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('deploy') {

            steps {
                sh 'java -jar target/esm-esm.jar'
            }
        }
    }
}
