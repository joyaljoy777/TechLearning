FROM openjdk:18-ea-11-jdk-alpine3.15

EXPOSE 8889

ENTRYPOINT exec java -jar target/learning-0.0.1-SNAPSHOT.jar