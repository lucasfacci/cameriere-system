FROM openjdk:17-jdk-alpine

RUN apk --no-cache add curl

COPY target/config-server-0.0.1-SNAPSHOT.jar config-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "config-server-0.0.1-SNAPSHOT.jar"]
