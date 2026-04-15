pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Build JAR') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t todo-app .'
            }
        }

        stage('Tag Docker Image') {
            steps {
                bat 'docker tag todo-app 23mis0389/todo-app:latest'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {

                    bat '''
                    docker login -u %DOCKER_USER% -p %DOCKER_PASS%
                    docker push 23mis0389/todo-app:latest
                    '''
                }
            }
        }
    }

    post {
        success {
            echo '✅ SUCCESS: Image pushed!'
        }
        failure {
            echo '❌ ERROR: Pipeline failed!'
        }
    }
}