pipeline {
  environment {
    dockerimagename = "dreamweb:latest"
    dockerImage = ""
  }
  agent any
  stages {
    stage('Checkout Source') {
      steps {
        git branch: 'main', url: 'https://github.com/ardaa/dreamwebserver.git'
      }
    }
    stage('Build image') {
      steps {
        script {
        dockerImage = sh(script: "docker build -t dreamweb .", returnStatus: true) == 0 ? dockerimagename : null

        }
      }
    }
    stage('Pushing Image') {
      environment {
        registryCredential = 'dockerhub-credentials'
        DOCKER_REGISTRY = 'https://registry.hub.docker.com'
      }
      steps {
        script {
          // Use Docker CLI to push the image
        withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
            sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword} && docker tag dreamweb arda12/dreamweb && docker push arda12/dreamweb"
          

        }
      }
        }
    }
    stage('Deploying container to Kubernetes') {
      steps {
         
                  sh '/var/lib/jenkins/.local/bin/ansible-playbook /home/admin/deploy.yaml -i /home/admin/inventory.ini'


        
      }
    }
  }
}