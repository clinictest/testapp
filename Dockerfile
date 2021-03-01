FROM adoptopenjdk/openjdk11:alpine-jre
#COPY ~/.m2/repository/target/testapp-0.0.1-SNAPSHOT.jar app.jar
ARG JAR_FILE=staging/*.jar
COPY ${JAR_FILE} app.jar
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
