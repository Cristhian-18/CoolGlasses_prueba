FROM amazoncorretto:17.0.11-alpine

COPY target/CoolGlasses-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]