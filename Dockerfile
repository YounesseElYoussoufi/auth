FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files to the container
COPY pom.xml .
COPY src ./src
EXPOSE 8080

# Install Maven
RUN apk add --no-cache maven

# Build the Maven project
RUN mvn clean install

# Copy the generated JAR file to the container's root directory
ARG JAR_FILE=target/*.jar
RUN cp ${JAR_FILE} gestion-scolarite.jar

# Define the entry point for the application
ENTRYPOINT ["java", "-jar", "/app/gestion-scolarite.jar"]