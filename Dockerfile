
#FROM alpine:3.10  as build-stage
#ENV NODE_VERSION 14.16.0
#RUN apk add --no-cache --virtual tar curl bash gnupg \
#    && rm -rf /var/cache/apk/*
#WORKDIR /app
#COPY . .
#RUN npm install
#RUN npm run build
#FROM node:12.18.1
#
#WORKDIR /app
#
#COPY package.json package.json
#COPY package-lock.json package-lock.json
#
#RUN npm install
#
#COPY . .

FROM maven:3.6.1-jdk-8-alpine as builder

MAINTAINER Author AndreiKviatkouski <a.kviatkouski@softteco.com>

WORKDIR /build

ENV PORT 8080
ENV HOST 0.0.0.0
COPY pom.xml .
COPY src src

RUN mvn clean package

FROM adoptopenjdk/openjdk8:alpine-slim

RUN apk add --no-cache curl tar bash

WORKDIR /app

FROM adoptopenjdk/openjdk8:alpine-slim

COPY --from=builder /build/target/testapp-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar"]