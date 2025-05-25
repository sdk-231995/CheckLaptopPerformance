FROM eclipse-temurin:17

LABEL mentainer="dkg@gmail.com"

WORKDIR /app

COPY target/UserService-0.0.1-SNAPSHOT.jar UserService-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "UserService-0.0.1-SNAPSHOT.jar"]