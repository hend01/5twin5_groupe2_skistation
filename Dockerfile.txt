FROM openjdk:17
ADD target/gestion-station-ski-1.0.jar ski.jar
ENTRYPOINT ["java", "-jar","ski.jar"]
EXPOSE 8090
