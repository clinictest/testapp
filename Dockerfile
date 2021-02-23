
FROM maven:3.6.1-jdk-8-alpine as builder

MAINTAINER Author AndreiKviatkouski <a.kviatkouski@softteco.com>

WORKDIR /build

ENV PORT 8080
ENV HOST 0.0.0.0

COPY pom.xml .
COPY src src

RUN mvn clean package -Pproduction

FROM openjdk:8-jdk-alpine

RUN apk add --no-cache curl tar bash

WORKDIR /app

FROM adoptopenjdk/openjdk8:alpine-slim

COPY --from=builder /build/target/testapp-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]