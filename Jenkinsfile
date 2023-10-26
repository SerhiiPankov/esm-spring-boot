node('main') {
    stage('checkout scm') {
        checkout scm
    }

    stage('build') {
        sh 'mvn clean package'
    }

    stage('archive artifacts') {
        archiveArtifacts artifacts: 'target/*.war'
    }

    stage('deployment') {
        deploy adapters: [tomcat9(credentialsId: '', path: '', url: 'http://127.0.0.1:8080/')],
        contextPath: 'esm',
        war: 'target/*.war'
    }
}

