pipeline {

agent {
     node {
        label 'TestNode'
   }
}

stages {
    stage('Checkout') {
        steps {
            // delete all files from server TestNode
            bat 'del /F /S /Q *.*'
            // delete all dir from server TestNode
            bat 'for /d %%x in (.\\*) do @rd /s /q %%x'
            // Вывод echo в консоль Jenkins
            echo 'step Git Checkout'

            checkout scm
        }
    }

    stage('Build') {
        steps {
            // Build the war file
             bat 'mvn clean package'
        }
    }

    stage('Deploy') {
        steps {
            // Stop Tomcat

            bat 'cd %CATALINA_HOME%'
            bat 'shutdown.bat'

            // Remove existing war file and deployed application
            bat "del C:/apache-tomcat/webapps/esm-0.0.1-SNAPSHOT*"

            // Copy the new war file to Tomcat webapps directory
            bat "copy target/esm-0.0.1-SNAPSHOT.war C:/apache-tomcat/webapps/myapp.war"

            // Start Tomcat
            bat "C:/apache-tomcat/bin/startup.bat"
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
        bat "del -rf target/esm-0.0.1-SNAPSHOT.war"
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
