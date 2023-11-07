pipeline {
    environment {
        dockerImageName = "ski"
        DOCKER_IMAGE_TAG = "v${BUILD_NUMBER}" // Using Jenkins BUILD_NUMBER as the tag
        PATH = "$PATH:/usr/local/bin"
        SONAR_CREDENTIALS = credentials('f42d7219-5bba-4e05-82ef-ee2115b07063')


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

        stage('JUnit / Mockito Tests') {
                    steps {
                        // Run JUnit and Mockito tests using Maven
                        sh 'mvn test'
                    }
                        }


        stage('SonarQube Analysis') {
                    steps {
                        echo 'sonar test';
                        sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=skander'
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
         stage('Docker Hub') {
              steps {
                     sh "docker login -u zouaouiskander -p Skandeer1"
                     sh "docker tag $dockerImageName:$DOCKER_IMAGE_TAG zouaouiskander/ski:$DOCKER_IMAGE_TAG"
                     sh "docker push  zouaouiskander/ski:$DOCKER_IMAGE_TAG"
              }
        }

        stage("Deploy to private registry") {
                    steps {
                        script {

                            def nexusRegistryUrl = '172.17.0.2:8081/repository/maven-releases/'
                            def dockerUsername = 'zouaouiskander'
                            def dockerPassword = 'Skandeer1'

                            sh "docker build -t $dockerImageName:$DOCKER_IMAGE_TAG ."
                            sh "docker tag $dockerImageName:$DOCKER_IMAGE_TAG ${nexusRegistryUrl}$dockerImageName:$DOCKER_IMAGE_TAG"
                            sh "echo ${dockerPassword} | docker login --username ${dockerUsername} --password ${dockerPassword} ${nexusRegistryUrl}"
                            sh "docker push ${nexusRegistryUrl}$dockerImageName:$DOCKER_IMAGE_TAG"
                        }

                    }
        }

        stage('Nexus Deployment') {
                    steps {

                            sh "mvn deploy -DskipTests=true "

                    }
        }*/

        stage("Start app and db") {
            steps {
                sh "docker-compose up -d"
            }
        }



      /* stage('Deploy') {
                    steps {
                           sh 'mvn deploy -DskipTests=true'
                                }
                            }

        stage("Deploy Dokcer Image to private registry") {
            steps {
                sh "..............."
            }
        }
    }
     deploymentRepo
    post {
        always {
            cleanWs()
        }
*/
    }
}
