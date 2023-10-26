pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code repository
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Build the war file
                 sh 'mvn clean package'
            }
        }

        stage('Deploy') {

            steps {
                sh 'java -jar esm-esm.jar'
            }
        }
    }
}
