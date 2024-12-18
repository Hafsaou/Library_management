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
                           def cloneStatus = sh(script: "git clone https://${GITHUB_PAT}@github.com/Hafsaou/Library_management.git", returnStatus: true)
                                                       // Vérification du statut de la commande de clonage
                                                       if (cloneStatus == 0) {
                                                           echo "Cloning succeeded."
                                                       } else {
                                                           echo "Cloning failed."
                                                       }
                             //  sh "git clone https://${GITHUB_PAT}@github.com/Hafsaou/Library_management.git"
                           }
                           }
                       }
                   }
               }
        stage('Build') {
            steps {
                sh '${MAVEN_HOME}/bin/mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh '${MAVEN_HOME}/bin/mvn test'
            }
        }
        stage('Quality Analysis') {
             steps {
               echo 'sonar'
//                 withSonarQubeEnv('SonarQube') {
//                     sh '${MAVEN_HOME}/bin/mvn sonar:sonar'
//                 }
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
//             emailext to: 'hafsaougha17@gmail.com',
//                 subject: 'Build Success',
//                 body: 'Le build a été complété avec succès.'
         echo 'succes'
        }
        failure {
//             emailext to: 'hafsaougha17@gmail.com',
//                 subject: 'Build Failed',
//                 body: 'Le build a échoué.'
echo 'fail'
        }
    }
}
