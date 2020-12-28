# Project Builder

## Installation on kubernetes with helm

Set environment variables:

```bash
export CLIENTID="your ClientID"
export CLIENTSECRET="your ClientSecret"
export PAT="your personal access token"
export GITHUBUSERNAME="your github username"
```

Go to helm/ljprojectbuilder and execute:

```bash
helm install ljprojectbuilder . --set github.registry.username=$GITHUBUSERNAME,github.registry.pat=$PAT,oauth.clientid=$CLIENTID,oauth.clientsecret=$CLIENTSECRET
```

## [DEPRECATED]

The following description is deprecated because we switched to Spring Boot.

### Links:
* [How to create templates](https://gitlab.com/witchpou/lj-projectbuilder/blob/master/documentation/howto.md)
* [architecture description for developer](https://gitlab.com/witchpou/lj-projectbuilder/blob/master/documentation/architecture.md)
* [introduction for webdesigner](https://github.com/witchpou/lj-projectbuilder/blob/master/documentation/webdesign.md)
* [article in german](https://wp.starwit.de/wp-content/uploads/2017/02/03-2016-Java-aktuell-Anett-Hübner_Java-Enterprise-Anwendungen-effizient-...-4.pdf)

### Motivation

![screenshot project builder](https://wp.starwit.de/wp-content/uploads/2016/10/projectbuilder.png)

Creating modern software always requires a number of things to do. One of these things is to persist data and to access persisted data. Finally the data should be presented in a userfriendly manner. Yet still a lot of boiler plate code needs to be written and creating application quickly remains a challenge. Lirejarp Project Builder is an application to kickstart new project via using templates. The Project Builder provides a configuration interfaces.

A fully working example application of this pattern can be found is the [lirejarp template](https://github.com/starwit/lirejarp). Besides using described pattern it also contains an Javascript based client application that serves a UI to the Java based backend. This rigorous separation of UI is also intended as it helps to demonstrate architecture approach without mixing with UI issues.

This is a tool that helps you create a new project based on the [lirejarp template](https://github.com/starwit/lirejarp). Setup of a new project consists of two things: renaming all elements according to your project needs (files, packages, war-file) and creating base functionality for new domain objects. So project builder is a tool that collects in a GUI some data for your new project. Once you provided a new project name and your desired domain objects, your check out copy of LireJarp or other templates is modified and enhanced accordingly.

### Prequisites

To start the software, the following stuff have to be installed:

* Java 8 JDK or higher
* Maven 3.0.5 or higher
* ant 1.9.3 or higher

### Ant Build

You can build and start the Project Builder in only three steps:

1. Clone [Lirejarp Project Builder](https://github.com/starwit/lj-projectbuilder) with git clone https://github.com/starwit/lj-projectbuilder
2. go to directory ljprojectbuilder and open command line
3. execute `ant build_application` on commandline

### Maven Build

- go to directory ljprojectbuilder and open command line
- Build app running all tests:
  ```bash
  mvn clean verify
  ```
 You will find the build artifact in `application/target`
- start JAR-fie with `java -jar application-0.0.1-SNAPSHOT.jar`

Hence, Project Builder can be reached under http://localhost:8081/ljprojectbuilder/.

### How-To Build Application in Developer Mode

* go to `ljprojectbuilder/application/Application.java` and run as Java application in your IDE
* go back to directory ljprojectbuilder and execute `ant copy_frontend` on commandline to copy frontend changes
