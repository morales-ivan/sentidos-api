FROM gradle:7.2-jdk11 AS build_image

COPY . /home/sentidos-api/src/
WORKDIR /home/sentidos-api/src
RUN gradle assemble

FROM openjdk:11-jre-slim AS exec_image

EXPOSE 8080
RUN mkdir /home/sentidos-api

COPY --from=build_image /home/sentidos-api/src/build/libs/sentidos-api-0.0.1-SNAPSHOT.jar /home/sentidos-api/sentidos-api.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/home/sentidos-api/sentidos-api.jar"]