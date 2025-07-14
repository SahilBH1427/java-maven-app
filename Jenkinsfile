pipeline {
    agent any
    tools {
        maven 'maven-3.9'
    }

    stages {
        stage('Init') {
            steps {
                script {
                    gv = load 'script.groovy'  /
                }
            }
        }

        stage('Build Jar') {
            steps {
                script {
                    gv.buildjar()
                }
            }
        }

        stage('Build Image') {
            steps {
                script {
                    gv.buildimage()
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    gv.deploy()
                }
            }
        }
    }
}
