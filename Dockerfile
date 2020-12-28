FROM openjdk:14.0.1-slim
RUN apt-get -y update
RUN apt-get -y install git
ENV TMP=/home/${USER}/tmp
ENV TEMP=/home/${USER}/tmp
RUN cd /home/${USER}
RUN ls -al
# copy application JAR (with libraries inside)
COPY application/target/application-*.jar /application.jar
# specify default command
CMD ["/usr/java/openjdk-14/bin/java", "-jar", "/application.jar"]
