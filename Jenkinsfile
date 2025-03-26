pipeline {
    agent any
    tools {
        jdk 'jdk17'
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
                bat '''
                    mkdir target
                    javac -d target/classes src/*.java
                    jar cvfe target/Tron.jar Main -C target/classes .
                '''
                archiveArtifacts 'target/Tron.jar'
            }
        }
        stage('Test') {
            steps {
                bat '''
                    javac -d target/test-classes -cp target/classes src/Test*.java
                    java -cp target/classes;target/test-classes org.junit.runner.JUnitCore UnitTesting
                '''
            }
        }
    }
}