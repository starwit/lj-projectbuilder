FROM openjdk:17-slim
RUN apt-get -y update
RUN apt-get -y install git

RUN useradd -ms /bin/bash ljprojectbuilder
USER ljprojectbuilder
WORKDIR /home/ljprojectbuilder
RUN whoami
RUN pwd
RUN mkdir tmp
ENV TMP=/home/${USER}/tmp
ENV TEMP=/home/${USER}/tmp
RUN cd /home/${USER}
RUN ls -al
# copy application JAR (with libraries inside)
COPY application/target/application-*.jar /home/ljprojectbuilder/application.jar
#COPY /home/ljprojectbuilder/application/target/application-*.jar /home/ljprojectbuilder/application.jar
# specify default command
CMD ["/usr/local/openjdk-17/bin/java", "-jar", "/home/ljprojectbuilder/application.jar", "-Dlog4j2.formatMsgNoLookups=true"]
