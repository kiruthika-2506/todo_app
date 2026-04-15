pipeline {
    agent any

    environment {
        IMAGE_NAME = "todo-app"
        KUBECONFIG = "C:\\ProgramData\\Jenkins\\.jenkins\\.kube\\config"
    }

    stages {

        stage('Build JAR') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %IMAGE_NAME% .'
            }
        }

        stage('Check Kubernetes') {
            steps {
                withEnv(['KUBECONFIG=%KUBECONFIG%']) {
                    bat 'kubectl get nodes'
                }
            }
        }

        stage('Deploy') {
            steps {
                withEnv(['KUBECONFIG=%KUBECONFIG%']) {
                    bat 'kubectl apply -f deployment.yaml --validate=false'
                }
            }
        }
    }
}