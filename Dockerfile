FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/bidaya-0.0.1-SNAPSHOT.jar /app/bidaya-0.0.1-SNAPSHOT.jar
RUN mkdir -p /app/uploads
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/bidaya-0.0.1-SNAPSHOT.jar"]
