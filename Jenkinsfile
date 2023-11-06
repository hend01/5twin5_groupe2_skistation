pipeline {
    environment {
        dockerImageName = "ski"
       
    }
    agent any
        tools {
          maven 'M2_HOME'
          jdk 'JAVA_HOME'
        }

    stages {
        stage('GIT') {
            steps {
                echo "Getting Project from Git"
                git branch: 'SkanderZouaoui',
                    url: 'https://github.com/hend01/5twin5_groupe2_skistation'
            }
        }

        stage('Build') {
            steps {
                script {
                    sh "mvn --version" // Use the specified Maven installation
                    sh "mvn clean package -DskipTests" // Build your Maven project
                }
            }
        }
       /*stage('Sonarqube') {
            steps {
                echo 'sonar test';
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=admin/sonar'
            }
        }*/

        stage("Build Docker image") {
            steps {
                script {
                    dockerImage = docker.build(dockerImageName)
                }
            }
        }

        stage("Start app and db") {
            steps {
                sh "docker-compose up -d"
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
