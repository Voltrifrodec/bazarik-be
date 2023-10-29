FROM openjdk:17

ADD ./target/bazarik-0.0.1-SNAPSHOT.jar bazarik-fe.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "bazarik-fe.jar" ]