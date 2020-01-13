pipeline {
    agent { label 'master' }
    stages {
               
        stage ('Step 1') {
            steps{
                sshagent(credentials : ['6207d970-1903-41f2-9e11-fa96ec19914e']) {
                    sh 'ssh -o StrictHostKeyChecking=no ubuntu@172.26.1.47 touch file01'
                    sh 'scp -v -o StrictHostKeyChecking=no home/ubuntu/filesend ubuntu@172.26.1.47:received'
                }
            }
        }

             
    }
}
