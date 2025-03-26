pipeline {
    agent any
    tools {
        jdk 'Java 17'      // Must match EXACTLY what you configured
        maven 'Maven'      // Case-sensitive name
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/mhammadtariq11/Tron.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
                junit 'target/surefire-reports/**/*.xml'
            }
        }
    }
}