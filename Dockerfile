FROM java:8

EXPOSE 8080

ARG JAR_FILE=target/blog-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} blog.jar

ENTRYPOINT ["java", "-jar", "/blog.jar"]