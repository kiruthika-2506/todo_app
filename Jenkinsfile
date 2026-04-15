pipeline {
    agent any

    environment {
        IMAGE_NAME = "todo-app"
        KUBECONFIG = "C:\\ProgramData\\Jenkins\\.jenkins\\.kube\\config"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git 'https://github.com/kiruthika-2506/todo_app.git'
            }
        }

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

        stage('Verify Docker Image') {
            steps {
                bat 'docker images'
            }
        }

        stage('Check Kubernetes Connection') {
            steps {
                withEnv(['KUBECONFIG=%KUBECONFIG%']) {
                    bat 'kubectl get nodes'
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withEnv(['KUBECONFIG=%KUBECONFIG%']) {
                    bat 'kubectl apply -f deployment.yaml --validate=false'
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                withEnv(['KUBECONFIG=%KUBECONFIG%']) {
                    bat 'kubectl get pods'
                    bat 'kubectl get services'
                }
            }
        }
    }

    post {
        success {
            echo '✅ SUCCESS: App deployed to Kubernetes!'
        }
        failure {
            echo '❌ ERROR: Pipeline failed. Check logs.'
        }
    }
}