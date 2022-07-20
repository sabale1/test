pipeline {
  agent any
  tools {
    maven "Maven_HOME-3.6.3"
  }
  
    //each branch has 1 job running at a time
  options {
    disableConcurrentBuilds()  
  }

  stages {
    stage('Environment Variables') {
      steps {
        script {
          load "$JENKINS_HOME/workspace/$Job_Name/envar.groovy"        
        }
      }
    }
    stage('Initialization') {
      steps {
        sh '''
            echo "PATH = ${PATH}"
        '''
      }
    }
    stage("Build") {
      steps {
        sh "mvn  compile -Dspring.profiles.active=dev"      
        
      }
    }
    
    stage('Unit Testing') {
      steps {
        script {
          sh "mvn clean test -Dspring.profiles.active=dev"
          echo 'TestNG Report'
        }
        step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
      }
    }
    
    stage('Code Coverage') {
        steps{
          	script {
                 	echo 'Code Coverage'
                 	jacoco()
                    }
                     	
            }
    }

    stage('Code Analysis') {
      steps {
        script {
          scannerHome = tool 'SonarQubeScanner'
        }
        withSonarQubeEnv('sonarqube') {
          sh  "mvn -Dspring.profiles.acitve=dev -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml clean verify sonar:sonar"
        
        }
      }
    }
    
 stage('SonarQube Security') {
      steps {
        script {
          scannerHome = tool 'SonarQubeScanner'
        }
        withSonarQubeEnv('sonarqube9005') {
          sh  "mvn clean verify sonar:sonar -Dspring.profiles.active=dev"
        }
      }
    }
    stage('Build Images') {
      steps {
        sh 'mvn clean package'
        script {
          docker.withRegistry('docker-registry.cdacmumbai.in:443') {
            def image = docker.build("${beimage}")
          }
        }

        
      }
    }
    
    stage('Upload Images to SIT/QA') {
      steps {
        withCredentials([string(credentialsId: 'DockerRegistryID', variable: 'DockerRegistryID')]) {}
        
        sh "docker push $beimage"
      }
    }
    
stage('k8s deploying in sit'){
        steps {
            sshagent(['sit-cluster']) {
                script{
                        sh "ssh root@10.210.0.140  kubectl rollout restart deployment erp-accounts-accounts-be -n ecgcbackend"                        
    
                    }
           
         }
       }

}

stage('k8s deploying in qa'){
        steps {
            sshagent(['qa-cluster']) {            
                script{                      
                        sh "ssh root@10.210.0.160  kubectl rollout restart deployment erp-accounts-accounts-be -n ecgcbackend"                       
                   
    
                    }
            
         }
       }

}


  }
  post {
  success{
   emailext attachLog: true,
   compressLog: true,
   body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} Deployed ${currentBuild.getBuildCauses()[0].shortDescription} \n More info at: ${env.BUILD_URL}",
   from: "${fromemail}",
   to: "${toemails}",
   subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME} Deployed "
       } 
 unstable{
  emailext attachLog: true,
   compressLog: true,
   body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} ${currentBuild.getBuildCauses()[0].shortDescription} \n More info at: ${env.BUILD_URL}\n Please find the log file attached for your reference",
   from: "${fromemail}",
   to: "${toemails}",
   subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
       } 
 aborted{
  emailext attachLog: true,
   compressLog: true,
   body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} ${currentBuild.getBuildCauses()[0].shortDescription} \n More info at: ${env.BUILD_URL}\n Please find the log file attached for your reference",
   from: "${fromemail}",
   to: "${toemails}",
   subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
       }
 failure{
  emailext attachLog: true,
   compressLog: true,
   body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} ${currentBuild.getBuildCauses()[0].shortDescription} \n More info at: ${env.BUILD_URL}\n Please find the log file attached for your reference",
   from: "${fromemail}",
   to: "${toemails}",
   subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
       }               
    always {
      cleanWs()
    }
  }
}
