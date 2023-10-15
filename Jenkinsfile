pipeline {
    agent any

    stages {
        stage('Git') {
            steps {
                echo 'Getting Project from Git'
                git branch: 'main', url: 'https://github.com/hend01/5twin5_groupe2_skistation.git'
            }
        }


        stage('Build') {
            steps {
                sh 'mvn -version'
                sh 'mvn clean package -DskipTests'
                sh 'mvn test'
            }
        }
    }
}