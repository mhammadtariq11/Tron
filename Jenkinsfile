pipeline {
    agent any
    tools {
        jdk 'jdk17'       // Must match your JDK installation name in Jenkins
        maven 'maven-3.9.6'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/YOUR_USERNAME/YOUR_REPO.git'
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
                junit 'target/surefire-reports/**/*.xml'  // JUnit report collection
                archiveArtifacts 'target/surefire-reports/*'  // Save test reports
            }

            post {
                always {
                    // Print test summary to console
                    script {
                        def testResults = junit testResults: 'target/surefire-reports/**/*.xml'
                        echo "Test results: ${testResults.totalCount} tests, " +
                             "${testResults.failCount} failures, " +
                             "${testResults.skipCount} skipped"
                    }
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package'
                archiveArtifacts 'target/*.jar'  // Save the built game
            }
        }
    }

    post {
        always {
            emailext (
                subject: "Tron Build ${currentBuild.result}",
                body: """
                    Build: ${env.BUILD_URL}
                    Tests run: ${currentBuild.testResultAction.totalCount}
                    Failures: ${currentBuild.testResultAction.failCount}
                    Skipped: ${currentBuild.testResultAction.skipCount}
                """,
                to: 'your-email@example.com'
            )
        }
    }
}