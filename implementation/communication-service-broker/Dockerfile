FROM gradle:7.4.2-jdk11-alpine

COPY --chown=gradle:gradle . /app
WORKDIR /app

RUN ./gradlew bootJar

ENTRYPOINT ["java","-jar","/app/build/libs/communication-service-broker-0.1.2-SNAPSHOT.jar"]
CMD ["UDP", "HTTP"]
