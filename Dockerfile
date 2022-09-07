FROM openjdk:8-jdk-alpine
EXPOSE 8080
WORKDIR .
COPY ./target/ride-share-backend-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "ride-share-backend-0.0.1-SNAPSHOT.jar"]
