# Project Builder

## Links: 
* ![architecture description for developer](https://github.com/witchpou/lj-projectbuilder/blob/master/architecture.md)
* ![introduction for webdesigner](https://github.com/witchpou/lj-projectbuilder/blob/master/webdesign.md)
* [article in german](https://wp.starwit.de/wp-content/uploads/2017/02/03-2016-Java-aktuell-Anett-Hübner_Java-Enterprise-Anwendungen-effizient-...-4.pdf)

## Motivation

![screenshot project builder](https://wp.starwit.de/wp-content/uploads/2016/10/projectbuilder.png)

Creating modern software always requires a number of things to do. One of these things is to persist data and to access persisted data. Finally the data should be presented in a userfriendly manner.

Yet still a lot of boiler plate code needs to be written and creating JEE application quickly remains a challenge. Therefore approach described in this article shall provide a guide in how to setup an architecture implementing persistence and a RESTful interface layer. To do that properly a (reusable) pattern of inheritance + generic data types is proposed. This (yet unnamed) pattern is applied in persistence, Enterprise Java Beans and in RESTful web service implementation and therefore proved to be quite flexible.

A fully working example application of this pattern can be found is the [lirejarp template](https://github.com/witchpou/lirejarp). Besides using described pattern it also contains an Javascript based client application that serves a UI to the Java based back end. This rigorous separation of UI is also intended as it helps to demonstrate architecture approach without mixing with UI issues. 

This is a tool that helps you create a new project based on the [lirejarp template](https://github.com/witchpou/lirejarp). Setup of a new project consists of two things: renaming all elements according to your project needs (files, packages, war-file) and creating base functionality for new domain objects. So project builder is a tool that collects in a GUI some data for your new project. Once you provided a new project name and your desired domain objects, your check out copy of LireJarp is modified and enhanced accordingly.

## Prequisites

To start the software, the following stuff have to be installed:

* Java 8 JDK or higher
* Maven 3.0.5 or higher
* ant 1.9.3 or higher

## Start Project Builder

You can build and start the Project Builder in only three steps:

1. Clone [Lirejarp Project Builder](https://github.com/witchpou/lj-projectbuilder) with git clone https://github.com/witchpou/lj-projectbuilder
2. go to directory lj-projectbuilder and open command line
3. execute `ant setup_project` on commandline

Hence, Project Builder can be reached under http://localhost:8081/ljprojectbuilder/. 

