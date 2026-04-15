FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/todo-app.jar app.jar
CMD ["java", "-jar", "app.jar"]