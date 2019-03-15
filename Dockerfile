#java-oracle:jdk_8 image is used
#FROM isuper/java-oracle:jdk_8 
FROM openjdk:8-jre-alpine 


#copy content of host directory to docker directory
ADD tomee/target/apache-tomee /usr/local/tomee 

RUN chmod g+w /usr/local/tomee

#copy application of host directory to docker directory
ADD ljprojectbuilder/target/ljprojectbuilder.war /usr/local/tomee/webapps/ljprojectbuilder.war


#add tomee to path variable
ENV PATH /usr/local/tomee/bin:$PATH 

#add variable CATALINA_HOME
ENV CATALINA_HOME /usr/local/tomee 


#set working directory
WORKDIR $CATALINA_HOME 

#set port which is available for host system - for docker containers only!
EXPOSE 8080 

#execute command
CMD ["catalina.sh", "run"]

