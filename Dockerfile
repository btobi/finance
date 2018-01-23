FROM openjdk:8-jre-alpine

COPY target/finance-1.jar /application.jar



CMD ["java", "-jar", "/application.jar"]