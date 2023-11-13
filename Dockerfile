FROM openjdk:11-jre-slim
EXPOSE 8089
COPY target/DevOps_Project-2.1.jar DevOps_Project-2.1.jar
ENTRYPOINT ["java", "-jar", "/DevOps_Project-2.1.jar"]