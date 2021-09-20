FROM openjdk:11-jre-slim-buster
# copy application JAR (with libraries inside)

ADD application.jar /opt/application.jar
RUN chmod +x /opt/application.jar
# specify default command
CMD ["/usr/local/openjdk-11/bin/java", "-jar", "/opt/application.jar"]
