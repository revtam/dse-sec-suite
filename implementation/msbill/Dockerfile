FROM gradle:7.4.2-jdk11-alpine

COPY --chown=gradle:gradle . /app
WORKDIR /app

RUN gradle build --no-daemon

ENTRYPOINT sleep 10 && java -jar /app/build/libs/msbill-0.0.1-SNAPSHOT.jar