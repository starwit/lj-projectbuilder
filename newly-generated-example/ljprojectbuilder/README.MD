# LireJarp Java-Spring backend
This repository contains a template for a java spring boot backend and a React frontend

## Contact

denjoo@oss.volkswagen.com (backend)
ipdramon@oss.volkswagen.com (frontend)

## Structure
The project is structured into five submodules:
* **application**
* **rest**
* **service**
* **persistence**
* **webclient**

Preface
```
1. Search your project for TODOs (e.g. 'grep -rni "TODO" .' in this projects root folder ). There will be some locations you have to change

2. Install NodeJS (if not already done)

3. Run npm install in webclient/app
This will download the dependencies listed in package.json
```

Build spring backend
```
cd ljprojectbuilder-spring
mvn clean package
```

Build spring backend + React frontend
```
cd ljprojectbuilder-spring
mvn clean package -Pfrontend
```

Run example application.
```
cd application
java -jar target/application-<version>.dev.jar 
```

Test simple rest call.
```
http://localhost:8080/ljprojectbuilder/api/health
http://localhost:8080/ljprojectbuilder/api/health/log
http://localhost:8080/ljprojectbuilder/api/devices
```
```
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/ljprojectbuilder/api/health
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/ljprojectbuilder/api/health/log
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/ljprojectbuilder/api/devices
```

Test embedded H2 DB.
```
http://localhost:8080/ljprojectbuilder/h2
```
