pipeline {
    agent any
    
    tools {
        jdk 'jdk17'
        maven 'maven-3.9.6'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
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
                junit 'target/test-results/**/*.xml'
                archiveArtifacts 'target/test-results/*'
            }
        }
        
        stage('Package') {
            steps {
                bat 'mvn package'
                archiveArtifacts 'target/*.jar'
            }
        }
    }
    
    post {
        always {
            junit 'target/test-results/**/*.xml'
        }
    }
}