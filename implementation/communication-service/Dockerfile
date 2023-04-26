FROM gradle:7.4.2-jdk11-alpine

COPY build/libs/communication-service-0.0.5-SNAPSHOT.jar /app/application.jar

ENTRYPOINT ["java","-jar","/app/application.jar"]
