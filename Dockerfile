#DockerFile Setup
FROM adoptopenjdk/openjdk16:alpine-jre
ADD target/aneeque-app.jar aneeque-app.jar
ENTRYPOINT ["java", "-jar", "aneeque-app.jar"]