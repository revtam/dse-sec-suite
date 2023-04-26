FROM gradle:7.4.2-jdk11-alpine

COPY communication-service-broker-0.1.2-SNAPSHOT.jar /app/application.jar

ENTRYPOINT ["java","-jar","/app/application.jar"]
CMD ["UDP", "HTTP"]
