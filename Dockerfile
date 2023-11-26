FROM gradle:4.10.2-jdk8-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
ENV GRADLE_OPTS=-Dorg.gradle.daemon=false
RUN gradle build --no-daemon

FROM openjdk:8-jre-alpine
EXPOSE 8080
ENV PORT 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/DreamWebServer-1.0.jar

ENTRYPOINT ["java","-jar","/app/DreamWebServer-1.0.jar"]
