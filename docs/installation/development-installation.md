## Development Installation

### Prerequisites

* Java JDK 14 or later
* Maven 3
* NodeJs (16.9.1) and NPM (8.3.2) - [NodeJS Install](https://nodejs.org/en/download/package-manager/)
* mariaDB 10.6 (available for development via docker-compose scripts)
* using Keycloak is optional

If you need more information about installing prerequisites (ubuntu / linux mint), please check [here](prerequisites-installation.md)

### Installation Steps

:exclamation: Each step is executed from project home directory.

1) go to deployment folder and start environment (database and keycloak) via docker-compose:

    ```bash
    cd deployment
    docker-compose -f localenv-docker-compose.yml up
    ```

2) go to webclient/app and install ui

    ```bash
    cd webclient/app
    npm install --legacy-peer-deps
    ```

3) build project

    ```bash
    mvn clean install -P frontend
    ```

4) start project

    ```bash
    java -jar application/target/application-0.0.1-SNAPSHOT.jar
    ```
    You can also run the main-class via Visual Studio Code.


* **Project Builder can be reached under http://localhost:8081/ljprojectbuilder/**
* **If you are using keycloak:**
    * **default user/password is admin/admin**
    * **keycloak can be reached under http://localost:8081/auth**

### Debugging

#### Frontend Debugging

For debugging, you can start the frontend separately.

```shell
cd webclient/app
npm start
```
NPM server starts under localhost:3000/ljprojectbuider/ by default

! If you are using the installation with keycloak, make sure you are logged in before using - just go to localhost:8081/ljprojectbuilder in your browser.

#### Backend Debugging

You can start the spring boot application in debug mode. See Spring Boot documentation for futher details. The easiest way is, to use debug functionality integrated in your IDE like VS code.

### MariaDB Client

The database is available under localhost:3006

```
Username:ljprojectbuilder
Database:ljprojectbuilder
Password:ljprojectbuilder
```
MySQLWorkbench is recommended to access database for development purpose.

### Starting without keycloak

If you want to start your application without keycloak connection, you need to change spring boot profile to dev in application\src\main\resources\application.properties.

```properties
spring.profiles.active=dev
```

or define env-variable
```bash
SPRING_PROFILES_ACTIVE=dev
```
