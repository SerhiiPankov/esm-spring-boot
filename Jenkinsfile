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
        environment {
            TOMCAT_HOME = 'C:/apache-tomcat'
            JAR_FILE = 'target/esm-0.0.1-SNAPSHOT.jar'
        }

        steps {
            // Stop Tomcat
            sh "${TOMCAT_HOME}/bin/shutdown.sh"

            // Remove existing war file and deployed application
            sh "rm -rf ${TOMCAT_HOME}/webapps/esm-0.0.1-SNAPSHOT*"

            // Copy the new war file to Tomcat webapps directory
            sh "cp ${JAR_FILE} ${TOMCAT_HOME}/webapps/myapp.jar"

            // Start Tomcat
            sh "${TOMCAT_HOME}/bin/startup.sh"
        }
    }

    stage('Verify') {
        steps {
            // Wait for Tomcat to start
            sh "sleep 30"

            // Verify if the application is deployed successfully
            sh "curl -s http://localhost:8080/"
        }
    }
}

post {
    always {
        // Cleanup any leftover files after deployment
        sh "rm -rf ${JAR_FILE}"
    }
}


// pipeline {
//     agent none
//     stages {
//         stage('Build') {
//             agent any
//             steps {
//                 echo 'Hello, Maven'
//                 bat 'mvn -B -DskipTests clean package'
//             }
//         }
//         stage('Run') {
//             agent any
//             steps {
//                 echo 'Hello, JDK'
//                 bat 'java -jar target/esm-0.0.1-SNAPSHOT.jar'
//             }
//         }
//     }
// }
