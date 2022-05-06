[Back](../README.md)

## Prerequisites Installation

In the description below, the following prerequisites are installed:

* github account to login to projectbuilder
* Java 14 or later
* Maven 3
* NodeJs with NPM (version 8.3.2) - [NodeJS Install](https://nodejs.org/en/download/package-manager/)
* docker (Version 20.10.8 or higher) and docker compose (version 1.29.2)
* hint: if not using docker-compose scripts, mariaDB 10.6 is needed

**Recommended:**

* linux operating system
* Visual Studio Code

The installation steps are only described for Linux Mint or Ubuntu. If you are using another operation system, you have to adjust your installation by your own.

## Workplace Installation on Ubuntu 20.4 / Linux Mint

Run the following commands on your shell:

```bash

sudo bash

# Install java
apt install openjdk-16-jdk

# Install docker
apt-get remove docker docker-engine docker.io containerd runc
apt-get update
apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
apt-get update

# Install correct version of docker-compose
curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose

apt-get install docker-ce docker-ce-cli containerd.io docker-compose

# Install Maven
apt install maven

exit
```
Enable docker for your user:

```bash
sudo groupadd docker
sudo usermod -aG docker $USER
sudo chown root:docker /var/run/docker.sock
sudo chown -R root:docker /var/run/docker
sudo chmod g=x /var/run/docker
```
--> restart
## VS-Code Configuration

The following extensions are installed:

```bash
code --list-extensions | xargs -L 1 echo code --install-extension

    code --install-extension cweijan.vscode-mysql-client2
    code --install-extension dhruv.maven-dependency-explorer
    code --install-extension eamodio.gitlens
    code --install-extension ms-azuretools.vscode-docker
    code --install-extension redhat.java
    code --install-extension VisualStudioExptTeam.vscodeintellicode
    code --install-extension vscjava.vscode-java-debug
    code --install-extension vscjava.vscode-java-dependency
    code --install-extension vscjava.vscode-java-pack
    code --install-extension vscjava.vscode-java-test
    code --install-extension vscjava.vscode-maven
```

To run projectBuilder via run-configuration directly, add launch configuration (launch.config) to .vscode folder:

```json
{
    "configurations": [
        {
            "type": "java",
            "name": "Launch Application",
            "request": "launch",
            "mainClass": "de.starwit.application.Application",
            "projectName": "application",
            "args": "",
            "env": {
                "APP_NAME": "FOO_APP",
                "SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GITHUB_CLIENTID": "edba8e9e17f1c045633e",
                "SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GITHUB_CLIENTSECRET": "<<yourClientSecret>>"
            }
        }
    ]
}
```
