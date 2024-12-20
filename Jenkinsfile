pipeline {
    agent any
    environment {
        MAVEN_HOME = tool 'Maven'
        SONAR_PROJECT_KEY = 'Library-management'
        SONAR_SCANNER_HOME = tool 'SonarQubeScanner'
    }
    stages {
        stage('Checkout') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'git_token', variable: 'GITHUB_PAT')]) {
                        if (fileExists('Library-management')) {
                            dir('Library-management') {
                                sh "git reset --hard"
                                sh "git clean -fd"
                                sh "git pull origin main"
                            }
                        } else {
                            def cloneStatus = sh(script: "git clone https://${GITHUB_PAT}@github.com/Hafsaou/Library_management.git", returnStatus: true)
                            if (cloneStatus == 0) {
                                echo "Cloning succeeded."
                            } else {
                                echo "Cloning failed."
                            }
                        }
                    }
                }
            }
        }
        stage('Build') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn clean install"
            }
        }
        stage('Test') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn test"
            }
        }
        stage('Quality Analysis') {
            steps {
                withCredentials([string(credentialsId: 'Library-management-token', variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv('SonarQube') {
                        sh """
                            mvn sonar:sonar \
                            -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                            -Dsonar.login="${SONAR_TOKEN}"
                        """
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Déploiement simulé réussi'
            }
        }
    }
    post {
        success {
            echo 'Build successful'
             mail to: 'hafsaougha17@gmail.com', subject: 'Build Success', body: 'The build has been completed successfully.'
        }
        failure {
            echo 'Build failed'
            mail to: 'hafsaougha17@gmail.com', subject: 'Build Failed', body: 'The build has failed.'
        }
    }
}
