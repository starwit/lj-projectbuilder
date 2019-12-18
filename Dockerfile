#java-oracle:jdk_8 image is used
#FROM isuper/java-oracle:jdk_8 
FROM openjdk:8-jre-alpine 

RUN apk --update add git less openssh && \
    rm -rf /var/lib/apt/lists/* && \
    rm /var/cache/apk/*

#copy content of host directory to docker directory
ADD tomee/target/apache-tomee /usr/local/tomee 

RUN chmod g+w /usr/local/tomee

#copy application of host directory to docker directory
ADD ljprojectbuilder/target/ljprojectbuilder.war /usr/local/tomee/webapps/ljprojectbuilder.war


#add tomee to path variable
ENV PATH /usr/local/tomee/bin:$PATH 

#add variable CATALINA_HOME
ENV CATALINA_HOME /usr/local/tomee 

#add http proxy
ENV HTTP_PROXY

#set proxy for git
RUN git config --global http.proxy $HTTP_PROXY

#set proxy for git
RUN git config --global https.proxy $HTTP_PROXY

#set working directory
WORKDIR $CATALINA_HOME 

#set port which is available for host system - for docker containers only!
EXPOSE 8080 

#execute command
CMD ["catalina.sh", "run"]
