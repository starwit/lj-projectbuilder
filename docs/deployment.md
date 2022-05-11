# ProjectBuilder Deployment

There are mainly three different deployments via docker-compose scripts:
* development deployment
* remote deployment
* deployment on Kubernetes cluster

The following picture depicts the deployed software part and the interaction with build & deployment automation.

![Overall picture](diagrams/deployment-current.drawio.svg)

## Development Deployment (private, with docker-compose)

For development, ProjectBuilder is running natively with java command to enable debugging and fast changes. In the deployment folder are two docker-compose scripts:
* localenv-docker-compose.yml: combines keycloak and MariaDB
* mysqllocal-docker-compose.yml: deploys only MariaDB, if it is not needed to run ProjectBuilder with Keycloak (dev-mod)

## Server Deployment (public, with docker-compose)

:heavy_exclamation_mark: This deployment is not recommended for high user counts. Downtime is needed for doing updates. Use e.g. Kubernetes deployment to ensure a failsafe and stable environment.

### Usage of different scripts

The scripts for server deployment are located under `deployment\https`. The following software is deployed:

* certbot + Nginx: provides SSL-certificate-handling in order to do HTTPS
* keycloak: authentication and authorization. For development, keycloak is using an integrated database. In production, an additional MariaDB is used
* MariaDB: Database for projectBuilder
* MariaDB for keycloak

In order to provide different release cycles for the different software products, three compose scripts are used:

* docker-compose.yml: used during the installation of from init-letsencrypt.sh in order to integrate certbot SSL-secret-handling into Nginx
* env-docker-compose.yml: includes Nginx, keycloak, which are only updated when new versions are provided by producers
* projectbuilder-docker-compose.yml: includes projectBuilder with database and is updated on new projectBuilder release - which should be very often

### Installation Steps

#### Initial Installation

1) Install prerequisites. See [documentation for installation of prerequisites](installation/prerequisites-installation.md) for details.
2) checkout ProjectBuilder deployment folder
3) go to https folder
4) set variables in env.sh and add it as source:
    ```bash
    source env.sh
    ```
5) establish certbot on nginx in order to be able to use letsencrypt:
    ```bash
    bash init-letsencrypt.sh
    ```
6) start env-docker-compose.yml
    ```bash
    docker-compose -f env-docker-compose.yml up -d
    ```
7) start projectbuilder-docker-compose.yml
    ```bash
    docker-compose -f projectbuilder-docker-compose.yml up -d
    ```
8) reset env.sh

#### Update ProjectBuilder

Via github actions, a new release is built and pushed to docker hub. the docker-compose script downloads the new images and deploys it to the server. Hence, to update ProjectBuilder, put new release to docker-compose script and restart the script.

1) set variables in env.sh and add it as source:
    ```bash
    source env.sh
    ```
2) stop projectbuilder-docker-compose.yml
    ```bash
    docker-compose -f projectbuilder-docker-compose.yml down
    ```
3) update projectbuilder-docker-compose.yml
4) restart projectbuilder-docker-compose.yml
    ```bash
    docker-compose -f projectbuilder-docker-compose.yml up -d
    ```
5) reset env.sh

## Deployment on Kubernetes

There are helm charts available for deployment on Kubernetes.

Go to helm/ljprojectbuilder
- copy values-template4private.yaml to values-private.yaml and add secret values
- execute e.g.:
  - for local cluster: `helm install ljprojectbuilder . -n ljprojectbuilder -f values.yaml -f values-private.yaml`
  - for hetzner cluster: `helm install ljprojectbuilder . -n ljprojectbuilder -f values.yaml -f values-hetzner.yaml`
