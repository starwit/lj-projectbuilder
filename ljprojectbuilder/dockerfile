FROM openjdk:14.0.1-slim
# copy application JAR (with libraries inside)
COPY application/target/application-*.jar /application.jar
# specify default command
CMD ["/usr/java/openjdk-14/bin/java", "-jar", "/application.jar"]
