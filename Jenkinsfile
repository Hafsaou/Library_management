pipeline {
    agent any
    environment {
        MAVEN_HOME = tool 'Maven'
    }
    stages {
       stage('checkout') {
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
                               sh "git clone https://${GITHUB_PAT}@github.com/Hafsaou/Library_management.git"
                           }
                           }
                       }
                   }
               }
        stage('Build') {
            steps {
                sh '${MAVEN_HOME}/bin/mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh '${MAVEN_HOME}/bin/mvn test'
            }
        }
        stage('Quality Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '${MAVEN_HOME}/bin/mvn sonar:sonar'
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
            emailext to: 'hafsaougha17@gmail.com',
                subject: 'Build Success',
                body: 'Le build a été complété avec succès.'
        }
        failure {
            emailext to: 'hafsaougha17@gmail.com',
                subject: 'Build Failed',
                body: 'Le build a échoué.'
        }
    }
}
