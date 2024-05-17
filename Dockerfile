FROM gradle:7.4.2-jdk17 AS builder
WORKDIR /home/gradle/project

COPY build.gradle.kts settings.gradle.kts /home/gradle/project/
RUN gradle build -x test --no-daemon

COPY . /home/gradle/project/
RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]