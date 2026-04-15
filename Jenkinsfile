pipeline {
    agent any

    tools {
        maven 'Maven'   // Make sure Maven is configured in Jenkins
    }

    environment {
        IMAGE_NAME = "todo-app"
        DOCKERHUB_USER = "your-dockerhub-username"   // 🔴 change this
        KUBECONFIG = "C:\\ProgramData\\Jenkins\\.jenkins\\.kube\\config"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/kiruthika-2506/todo_app.git'
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

        stage('Tag Docker Image') {
            steps {
                bat 'docker tag %IMAGE_NAME% %DOCKERHUB_USER%/%IMAGE_NAME%:latest'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    bat 'docker login -u %USER% -p %PASS%'
                    bat 'docker push %DOCKERHUB_USER%/%IMAGE_NAME%:latest'
                }
            }
        }

        stage('Check Kubernetes') {
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
            echo '✅ SUCCESS: Application deployed!'
        }
        failure {
            echo '❌ ERROR: Pipeline failed!'
        }
    }
}