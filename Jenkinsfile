pipeline {
    agent any

    tools {
        maven 'M3'  // Preconfigured Maven in Jenkins
        jdk 'jdk17' // Java 17 (configure in Global Tools)
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/your-account/your-game.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
                archiveArtifacts 'target/*.jar'  // Saves game JAR
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
                junit 'target/surefire-reports/**/*.xml'  // Publishes test results
                jacoco execPattern: 'target/jacoco.exec'  // Code coverage
            }
        }

        stage('Quality Gate') {
            steps {
                // Fail if test coverage < 80%
                sh 'mvn verify -Djacoco.min.line.percentage=80'
            }
        }
    }

    post {
        always {
            mail to: 'team@email.com',
                 subject: "Game Build ${currentBuild.result}",
                 body: "Build ${currentBuild.result}: ${env.BUILD_URL}"
        }
    }
}