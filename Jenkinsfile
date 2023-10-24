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
             bash 'mvn clean package'
        }
    }

    stage('Deploy') {
        environment {
            TOMCAT_HOME = 'C:/apache-tomcat'
            JAR_FILE = 'target/esm-0.0.1-SNAPSHOT.jar'
        }

        steps {
            // Stop Tomcat
            bat "${TOMCAT_HOME}/bin/shutdown.sh"

            // Remove existing war file and deployed application
            bat "rm -rf ${TOMCAT_HOME}/webapps/esm-0.0.1-SNAPSHOT*"

            // Copy the new war file to Tomcat webapps directory
            bat "cp ${JAR_FILE} ${TOMCAT_HOME}/webapps/myapp.jar"

            // Start Tomcat
            bat "${TOMCAT_HOME}/bin/startup.sh"
        }
    }

    stage('Verify') {
        steps {
            // Wait for Tomcat to start
            bat "sleep 30"

            // Verify if the application is deployed successfully
            bat "curl -s http://localhost:8080/"
        }
    }
}

post {
    always {
        // Cleanup any leftover files after deployment
        bat "rm -rf ${JAR_FILE}"
    }
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
