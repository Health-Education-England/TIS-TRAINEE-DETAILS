pipeline {
    agent { label 'master' }
    stages {
               
        stage ('Run Tests') {
            steps{
                sshagent(credentials : ['key']) {
                    sh 'ssh -o StrictHostKeyChecking=no ubuntu@172.26.1.47 touch file200'
                    sh 'scp /home/ubuntu/file20 ubuntu@172.26.1.47:received20'
                }
            }
        }
        
        stage ('Install Docker') {
            steps{
                sshagent(credentials : ['key']) {
                    sh 'ansible-playbook -v /home/ubuntu/TIS-DEVOPS/ansible/tasks/docker-upgrade.yml -i /home/ubuntu/TIS-DEVOPS/ansible/inventory/simple-inventory.ini'
                }
            }
        }
        
    }
}
