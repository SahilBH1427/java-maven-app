# Use a slim OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory in the container
WORKDIR /app

# Copy the JAR file built by Maven
COPY target/java-maven-app-*.jar app.jar

# Run the JAR
CMD java -jar java-maven-app-*.jar

