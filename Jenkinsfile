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
                    env.BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                    gv = load 'script.groovy'
                }
            }
        }
        stage('increment version'){
            steps {
                script{
                    gv.incr()
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
        stage('commit verion update'){
            steps{
                 script{
                    gv.cred()
                 }
            }
        }
    }
}
