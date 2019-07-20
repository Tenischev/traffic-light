pipeline {
    parameters {
        string(name: 'port', defaultValue: '22222', description: 'Port where traffic light will be run')
    }
    options {
        timeout(time: 1, unit: 'HOURS')
    }
    agent {
        node {
            label 'master'
            customWorkspace "${workspace}/${env.BRANCH_NAME}"
        }
    }
    stages {
        stage("Build") {
            steps {
                dir('TrafficLight') {
                    sh 'docker build -t traffic-light .'
                }
            }
        }
        stage("Deploy") {
            steps {
                script {
                    try {
                        sh "docker stop traffic-light"
                        sh "docker rm traffic-light"
                    } catch (exc) {
                        echo "Container doesn't exist!"
                    }
                }
                sh "docker volume create traffic-light-cfg"
                sh "docker run --name traffic-light -v traffic-light-cfg:/app/config -p ${port}:8080 -d traffic-light"
            }
        }
    }
}