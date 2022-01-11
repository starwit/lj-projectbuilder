# info keycloak

run local keycloak with docker command:

```bash
docker run -d --name keycloak -p 8080:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin jboss/keycloak
```
Click add realm and import mapcollector-realm.json

start/stop container with
```bash
docker stop keycloak
docker start keycloak
```
In keycloak:

* add user 
* set password under tab credentials
* assign roles tu user (Role Mappings)