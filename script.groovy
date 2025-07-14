def buildjar() {
  sh 'mvn package'
}
def buildimage() {
     echo 'bulding the docekr image'
     withCredentials([usernamePassword(credentialsId: 'dockerhub_repo',passwordVariable: 'PASS' ,usernameVariable: 'USER')]){
     sh 'docker build -t sahilf5/demoapp:jma-2.0 .'
     sh 'echo $PASS | docker login -u $USER --password-stdin '
     sh 'docker push sahilf5/demoapp:jma-2.0'
     }
}
  
                 

def deploy() {
  echo" deloy"
  
}

return this
