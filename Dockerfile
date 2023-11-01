FROM openjdk:17

ADD /target/bazarik-0.0.1-SNAPSHOT.jar bazarik-be.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "bazarik-be.jar" ]