pipeline {
    agent { label 'master' }
    stages {
        
        stage ('Step-1') {
            steps{
                sh "ls ~/workspace/TIS-OPS/ansible"
            }
        }
        
        stage ('Step-2') {
            steps{
                sh 'scp test21 -o StrictHostKeyChecking=no ubuntu@172.26.1.140:test21-received'
                sh 'ssh -o StrictHostKeyChecking=no ubuntu@172.26.1.140 ls'
            }
        }
        
        stage ('Step-4') {
            steps{
                sh "ansible-playbook -i ~/workspace/TIS-OPS/ansible/inventory/simple-inventory.ini ~/workspace/TIS-OPS/ansible/install-docker.yml -vvv"
            }
        }
             
    }
}
