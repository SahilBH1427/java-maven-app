def gv
pipeline {
    agent any
    tools {
        maven 'maven-3.9'
    }

    stages {
        stage('Init') {
            when {
                expression {
                    env.BRANCH_NAME == 'jenkins-jobs'
                }
            }
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
