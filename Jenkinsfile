pipeline {
    agent { label 'master' }
    stages {
               
        stage ('Step 1') {
            steps{
                sshagent(credentials : ['6207d970-1903-41f2-9e11-fa96ec19914e']) {
                    sh 'ssh -o StrictHostKeyChecking=no ubuntu@172.26.1.47 touch file01'
                }
            }
        }
        
        stage ('Step 2') {
            steps{
                sh 'ls /home/ubuntu'
                sh 'scp /home/ubuntu/filesend ubuntu@172.26.1.47:filesend-received -v'
            }
        }
             
    }
}
