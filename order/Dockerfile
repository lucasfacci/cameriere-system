FROM openjdk:17-jdk-alpine

RUN apk --no-cache add curl

COPY target/order-0.0.1-SNAPSHOT.jar order-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "order-0.0.1-SNAPSHOT.jar"]
