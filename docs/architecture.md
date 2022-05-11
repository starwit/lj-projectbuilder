# Architecture

[Back](../README.md)

## ProjectBuilder Components

The ProjectBuilder itself is based on the same project structure like the [starwit/reacthook-spring-template](https://github.com/starwit/reacthook-spring-template). The following description is valid for both. The project, based on Spring Boot is build via maven and is parted in subprojects shown in the diagram below.

![Components projectBuilder](diagrams/projectbuilder-architecture-intern.drawio.svg)

The following subprojects exist in every project built with projectBuilder:
### application
This subproject defines the over-all spring boot application. It contains spring boot application configuration and is needed to build the application.
### persistence
This subproject contains classes for database access. In this project, all database scripts are placed. Through flyway, these scripts are executed automatically during project start. Two databases has to be supported with the scripts: h2 (for tests) and mysql.
### rest
This subproject enables the access from UI via RESTful webservices.
### webclient
The UI logic is placed in the webclient project. React is used. The Frontend is utilized with [Material-UI](https://www.mui.com) and its design structure (mostly based on [JSS](https://cssinjs.org/?v=v10.8.1)).

## ProjectBuilder specific
In ProjectBuilder, one additional project is implemented:
### generator
The generator project contains all logic to generate projects configured with the ProjectBuilder.

Code generation via freemarker enables publishing of an entire running application via ProjectBuilder. The intent is to help to do the first step and using a standardized way to implement standard functionality. So, you will just generate once and change the code in the way you want. This pragmatic approach avoids a complex code generation.

## Complete Components

In folder deployments, different docker-compose scripts exists in order to deploy ProjectBuilder with additional components:

![Components](diagrams/projectbuilder-architecture-env.drawio.svg)

The components are used as followed:

* certbot + nginx: provides ssl-certificate-handling in oder to do https
* keycloak: authentication and authorisation. For development, keycloak is using integrated database. In production an additional MariaDB is used
* mariaDB: Database for projectBuilder
* mariaDB for keycloak