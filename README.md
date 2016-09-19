# Project Builder

## ATTENTION ##
To add new Tomcat Server 8.5.3 in your ecplise, you need to add following bugfix to the plugins folder of eclipse
	https://bugs.eclipse.org/bugs/attachment.cgi?id=262418&action=edit

## Description ##

This is a tool that helps you create a new project based on the LireJarp architecture pattern (https://github.com/witchpou/lirejarp). Setup of a new project consists of two things: renaming all elements according to your project needs (files, packages, war-file) and creating base functionality for new domain objects. 
So project builder is a tool that collects in a GUI some data for your new project. Once you provided a new project name and your desired domain objects, your check out copy of LireJarp is modified and enhanced accordingly. 

## Usage ##
Build project with ```mvn package``` and if successful run ```java -jar target/projectbuilder.jar```
