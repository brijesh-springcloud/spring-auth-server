FROM openjdk:8

VOLUME /tmp

ADD maven/spring-auth-server-0.0.1-SNAPSHOT.jar spring-auth-server.jar

RUN sh -c 'touch /spring-auth-server.jar'

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/spring-auth-server.jar"]

# "-Dspring.profiles.active=container"
