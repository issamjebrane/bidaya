# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container at /app
COPY target/bidaya-0.0.1-SNAPSHOT.jar /app/bidaya-0.0.1-SNAPSHOT.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "bidaya-0.0.1-SNAPSHOT.jar"]
