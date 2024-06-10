FROM openjdk:17-jdk-alpine

RUN apk --no-cache add curl

COPY target/eureka-0.0.1-SNAPSHOT.jar eureka-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "eureka-0.0.1-SNAPSHOT.jar"]
