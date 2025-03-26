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
                bat 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                script {
                    try {
                        bat 'mvn surefire:test'  // Explicit test execution
                    } finally {
                        junit '**/target/surefire-reports/*.xml'  // Broader pattern
                        archiveArtifacts '**/target/surefire-reports/*.txt,**/target/surefire-reports/*.xml'
                    }
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'  // Double check
                }
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
            emailext (
                subject: "Tron Build ${currentBuild.currentResult}",
                body: "Build ${env.BUILD_URL}\nTest results: ${currentBuild.testResultAction?.totalCount} tests, ${currentBuild.testResultAction?.failCount} failures",
                to: 'your-email@example.com'
            )
        }
    }
}