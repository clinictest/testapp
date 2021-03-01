
#COPY ~/.m2/repository/target/testapp-0.0.1-SNAPSHOT.jar app.jar



#https://docs.github.com/en/actions/guides/building-and-testing-java-with-maven
FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=staging/*.jar
COPY ${JAR_FILE} app.jar
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
