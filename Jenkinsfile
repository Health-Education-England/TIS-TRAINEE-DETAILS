pipeline {
    agent { label 'master' }
    stages {
               
        stage ('Step 1') {
            steps{
                sshagent(credentials : ['key']) {
                    sh 'ssh -o StrictHostKeyChecking=no ubuntu@172.26.1.47 touch file100'
                }
            }
        }
        
        stage ('Step 2') {
            steps{
                sshagent(credentials : ['key']) {
                    sh 'scp /home/ubuntu/file20 ubuntu@172.26.1.47:received'
                }                
            }
        }

             
    }
}
