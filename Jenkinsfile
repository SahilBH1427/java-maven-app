def gv
pipeline {
    agent any

    stages {
        stage('Init') {
            steps {
                script {
                    gv = load 'script.groovy'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    gv.buildjar()
                }
            }
        }

        stage('Image') {
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
