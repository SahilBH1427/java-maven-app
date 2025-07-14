deg gv

pipeline {
    agent any
    tools {
        
        maven 'maven-3.9' 
    } 
   stages {
        stage('init') { 
            steps {
                script{
                    scrpit{
                        gv = load "script.groovy"
                    }
                    
                }
            }
        }
    stages {
        stage('Build jar') { 
            steps {
                script{
                    echo "bulding app "
                    gv.buildjar()
                   
                }
            }
        }
        stage('build iamge') { 
            steps {  
                script{
                    echo "bulding image "
                    gv.buildimage()
                   
                      
                    }
                }
            }
        }
        stage('Deploy') { 
            steps {
                 scrpit{
                     gv.deploy
                 }
            }
        }
    }
}
