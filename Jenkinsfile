pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'todo-app'
        KUBECONFIG = 'C:\\ProgramData\\Jenkins\\.jenkins\\.kube\\config'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git 'https://github.com/kiruthika-2506/todo_app.git'
            }
        }

        stage('Build Application') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %DOCKER_IMAGE% .'
            }
        }

        stage('Verify Docker Image') {
            steps {
                bat 'docker images'
            }
        }

        stage('Check Kubernetes Connection') {
            steps {
                bat 'kubectl get nodes'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                bat 'kubectl apply -f deployment.yaml'
            }
        }

        stage('Verify Deployment') {
            steps {
                bat 'kubectl get pods'
                bat 'kubectl get services'
            }
        }
    }

    post {
        success {
            echo '✅ Deployment Successful!'
        }
        failure {
            echo '❌ Pipeline Failed!'
        }
    }
}