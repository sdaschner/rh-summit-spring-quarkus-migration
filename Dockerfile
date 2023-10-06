FROM adoptopenjdk/openjdk16-openj9:x86_64-alpine-jre-16.0.1_9_openj9-0.26.0

COPY target/*.jar /app.jar

CMD ["java", "-jar", "/app.jar"]
