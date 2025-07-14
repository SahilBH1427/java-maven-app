pipeline {
    agent any
    tools {
        
        maven 'maven-3.9' 
    } 
    stages {
        stage('Build jar') { 
            steps {
                script{
                    echo "bulding app "
                    sh 'mvn package'
                }
            }
        }
        stage('build iamge') { 
            steps {
           
                echo 'bulding the docekr image'
                withCredentials([usernamePassword(credentialsId: 'dockerhub_repo',passwordVariable: 'PASS' ,usernameVariable: 'USER')]){
                   sh 'docker build -t sahilf5/demoapp:jma-2.0 .'
                   sh 'echo $PASS | docker login -u $USER --password-stdin '
                   sh 'docker push sahilf5/app:jma-2.0'
                }
            }
        }
        stage('Deploy') { 
            steps {
                 echo 'de pppppppp'
            }
        }
    }
}
