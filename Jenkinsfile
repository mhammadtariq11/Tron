pipeline {
    agent any
    tools {
        jdk 'Java 17'
        maven 'Maven'
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
                bat 'mvn clean compile -Djava.awt.headless=true'
            }
        }
        
        stage('Test') {
            steps {
                // Skip GUI tests
                bat 'mvn test -Djava.awt.headless=true -Dtest=!test.java.com.tron.UnitTestingTest#testGameControllerHandleInput'
            }
        }
        
        stage('Package') {
            steps {
                // Force headless mode and skip tests during packaging
                bat 'mvn package -Djava.awt.headless=true -DskipTests'
                archiveArtifacts 'target/*.jar'
            }
        }
    }
    
    post {
        always {
            echo 'Build completed with temporary GUI workaround'
        }
    }
}