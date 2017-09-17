#java-oracle:jdk_8 image is used
FROM isuper/java-oracle:jdk_8 

RUN locale-gen de_DE.UTF-8  
ENV LANG de_DE.UTF-8  

#copy content of host directory to docker directory
ADD apache-tomee /usr/local/tomee 
ADD db /usr/local/db 

#copy application of host directory to docker directory
ADD ljprojectbuilder.war /usr/local/tomee/webapps/ljprojectbuilder.war

#add tomee to path variable
ENV PATH /usr/local/tomee/bin:$PATH 

#add variable CATALINA_HOME
ENV CATALINA_HOME /usr/local/tomee 


#set working directory
WORKDIR $CATALINA_HOME 

#set port which is available for host system
EXPOSE 8081 

#execute command
CMD ["catalina.sh", "run"]
