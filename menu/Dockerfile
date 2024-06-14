FROM openjdk:17-jdk-alpine

RUN apk --no-cache add curl

COPY target/menu-0.0.1-SNAPSHOT.jar menu-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "menu-0.0.1-SNAPSHOT.jar"]
