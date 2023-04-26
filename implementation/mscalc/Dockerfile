FROM gradle:7.4.2-jdk11-alpine

COPY --chown=gradle:gradle . /app
WORKDIR /app

RUN ./gradlew bootJar

ENTRYPOINT sleep 10 && java -jar /app/build/libs/MSCalc-1.0-SNAPSHOT.jar primary 8104