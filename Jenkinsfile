pipeline {
    agent { label 'master' }
    stages {
        
        stage ('Step 1') {
            steps{
                sh "pwd"
            }
        }
        
        stage ('Step 2') {
            steps{
                sshagent(credentials : ['6207d970-1903-41f2-9e11-fa96ec19914e']) {
                    sh 'ssh -o StrictHostKeyChecking=no ubuntu@172.26.1.47 rm file01'
                    sh 'ls'
                }
            }
        }
             
    }
}
