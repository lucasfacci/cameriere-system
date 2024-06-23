FROM openjdk:17-jdk-alpine

ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.32.0/opentelemetry-javaagent.jar /app/libs/opentelemetry-javaagent-1.32.0.jar
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "api-gateway-0.0.1-SNAPSHOT.jar"]
