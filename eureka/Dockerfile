FROM openjdk:17-jdk-alpine

RUN apk --no-cache add curl

ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.32.0/opentelemetry-javaagent.jar /app/libs/opentelemetry-javaagent-1.32.0.jar
COPY target/eureka-0.0.1-SNAPSHOT.jar eureka-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "eureka-0.0.1-SNAPSHOT.jar"]
