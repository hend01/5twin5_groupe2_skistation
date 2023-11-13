FROM maven:3.8.4-openjdk-11
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN mvn dependency:resolve
COPY src ./src
EXPOSE 8089
ADD /target/Hend-station-ski.jar Hend-station-ski.jar
CMD ["mvn", "spring-boot:run"]
