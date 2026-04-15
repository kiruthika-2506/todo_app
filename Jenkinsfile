pipeline {
    agent any

    environment {
        KUBECONFIG = "C:\\kube\\config"
    }

    stages {

        stage('Checkout') {
            steps {
                git url: 'https://github.com/kiruthika-2506/todo_app', branch: 'main'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t todo-app .'
            }
        }

        stage('Tag Image') {
            steps {
                bat 'docker tag todo-app 23mis0389/todo-app:latest'
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
                    docker push 23mis0389/todo-app:latest
                    '''
                }
            }
        }

        stage('Verify Kubernetes') {
            steps {
                bat '''
                kubectl config current-context
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
    }

    post {
        success {
            echo "✅ Pipeline SUCCESS!"
        }
        failure {
            echo "❌ Pipeline FAILED!"
        }
    }
}