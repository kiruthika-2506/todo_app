pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "23mis0389/todo-app:latest"
        KUBECONFIG = "C:\\Users\\kirut\\.kube\\config"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/kiruthika-2506/todo-app.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t todo-app .'
            }
        }

        stage('Tag Docker Image') {
            steps {
                bat 'docker tag todo-app %DOCKER_IMAGE%'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-cred',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat '''
                    echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                    docker push %DOCKER_IMAGE%
                    '''
                }
            }
        }

        stage('Verify Kubernetes Connection') {
            steps {
                bat '''
                kubectl config current-context
                kubectl cluster-info
                kubectl get nodes
                '''
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                bat '''
                kubectl apply -f deployment.yaml
                kubectl apply -f service.yaml
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                bat '''
                kubectl get pods
                kubectl get services
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline SUCCESS: App deployed to Kubernetes!'
        }
        failure {
            echo '❌ Pipeline FAILED!'
        }
    }
}