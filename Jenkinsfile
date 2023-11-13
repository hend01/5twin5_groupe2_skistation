pipeline {
    environment {
        dockerImageName = "ski"
        DOCKER_IMAGE_TAG = "v${BUILD_NUMBER}" // Using Jenkins BUILD_NUMBER as the tag
        PATH = "$PATH:/usr/local/bin"
        GRAFANA_CONTAINER = "871435b97d51"
        PROMETHEUS_CONTAINER= "350eeb5ae795"
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
/*
        stage('JUNIT / Mockito') {
            steps {
                // Run JUnit and Mockito tests using Maven
                sh 'mvn test'
            }
        }

        stage('MVN SONARQUBE') {
            steps {
                script {
                    echo 'sonar test';
                    withCredentials([usernamePassword(credentialsId: 'f42d7219-5bba-4e05-82ef-ee2115b07063', passwordVariable: 'SONAR_PASSWORD', usernameVariable: 'SONAR_LOGIN')]) {
                        sh "mvn sonar:sonar -Dsonar.login=${SONAR_LOGIN} -Dsonar.password=${SONAR_PASSWORD}"
                    }
                }
            }
        }
        */

        stage('MVN NEXUS') {
                    steps {
                        sh "mvn deploy -DskipTests=true "
                    }
        }

        stage('PROMETHEUS ET GRAFANA') {
                    steps {
                        sh "docker restart ${PROMETHEUS_CONTAINER}"
                        sh "docker restart ${GRAFANA_CONTAINER}"
                    }
        }


        stage("Build Docker image") {
            steps {
                script {
                    sh 'docker build -t $dockerImageName:$DOCKER_IMAGE_TAG -f Dockerfile ./'
                }
            }
        }
    /*
        stage('PUSH TO DOCKERHUB') {
            steps {
                script{
                    withCredentials([usernamePassword(credentialsId: '9632221c-3c9c-4b6a-8e46-6274c153d735', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_LOGIN')]) {
                        sh "docker login -u ${DOCKER_LOGIN} -p ${DOCKER_PASSWORD}"
                        sh "docker tag $dockerImageName:$DOCKER_IMAGE_TAG zouaouiskander/ski:$DOCKER_IMAGE_TAG"
                        sh "docker push  zouaouiskander/ski:$DOCKER_IMAGE_TAG"
                            }
                    }
            }
        }
*/
        stage("DOCKER COMPOSE") {
            steps {
                sh "docker-compose up -d"
            }
        }
    }
    post {
            success {
                emailext(
                    subject: "PROJECT : ${PROJECT_NAME}  , Build Successful: Build #${currentBuild.number}",
                    body: "The build was successful. Build number: ${currentBuild.number}",
                    to: 'mohamedskander.zouaoui@esprit.tn'
                )
            }
            failure {
                emailext(
                    subject: "PROJECT : ${PROJECT_NAME} , Build Failed: Build #${currentBuild.number}",
                    body: "The build has failed. Build number: ${currentBuild.number}, FAILED TESTS: ${FAILED_TESTS} ",
                    to: 'mohamedskander.zouaoui@esprit.tn'
                )
            }
        }
}
