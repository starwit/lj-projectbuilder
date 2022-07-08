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
ENV SENTRY_TRACES_SAMPLE_RATE_FRONTEND=__SENTRY_TRACES_SAMPLE_RATE_FRONTEND_HERE__
ENV SENTRY_DSN_FRONTEND=__SENTRY_DSN_FRONTEND_HERE__
ENV SENTRY_ENVIRONMENT=__SENTRY_ENVIRONMENT_HERE__

# Replace env var placeholders before running
ADD entrypoint.sh ./
ENTRYPOINT ["/home/ljprojectbuilder/entrypoint.sh"]

RUN cd /home/${USER}
RUN ls -al
# copy application JAR (with libraries inside)
COPY application/target/application-*.jar /home/ljprojectbuilder/application.jar
#COPY /home/ljprojectbuilder/application/target/application-*.jar /home/ljprojectbuilder/application.jar

ENV SENTRY_TRACES_SAMPLE_RATE_FRONTEND=.75
ENV SENTRY_DSN_FRONTEND=
ENV SENTRY_ENVIRONMENT=local-development

# specify default command
CMD ["/usr/local/openjdk-17/bin/java", "-jar", "/home/ljprojectbuilder/application.jar", "-Dlog4j2.formatMsgNoLookups=true"]
