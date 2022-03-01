FROM openjdk:latest

VOLUME /tmp

ADD target/iuh-education-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}","-jar", "app.jar", "/iuh-education-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080