
def incr() {
  sh 'mvn build-helper:parse-version versions:set -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} versions:commit'
  def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
  def version = matcher[0][1]
  env.IMAGE_NAME = "$version-$BUILD_NUMBER"
  echo "Generated IMAGE_NAME: ${env.IMAGE_NAME}"
}


def buildjar() {
  sh 'mvn clean package'
}

def buildimage() {
     echo 'bulding the docekr image'
     withCredentials([usernamePassword(credentialsId: 'dockerhub_repo',passwordVariable: 'PASS' ,usernameVariable: 'USER')]){
     sh "docker build -t sahilf5/demoapp:${env.IMAGE_NAME} ."
     sh 'echo $PASS | docker login -u $USER --password-stdin '
     sh "docker push sahilf5/demoapp:${env.IMAGE_NAME}"
     }
}
  
                 

def deploy() {
  echo" deloy"
  
}
def cred() {
  withCredentials([usernamePassword(credentialsId: 'githubid',passwordVariable: 'PASS' ,usernameVariable: 'USER')]){
    sh 'git config --global user.email "jenkins@gmail.com"'
    sh 'git config --global user.name "jenkins"'

    sh 'git status'
    sh 'git branch'

    sh "git remote set-url origin https://${USER}:${PASS}@github.com/SahilBH1427/java-maven-app.git"
    sh 'git add . '
    sh 'git commit -m "ci:verion bump"'
    sh 'git push origin HEAD:main'
  }
}

return this
