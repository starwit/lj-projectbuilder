# Installation on local PC

[Back](../README.md)

## Prerequisites for Development Workplace

* github account to login to projectbuilder
* Java 14 or later
* Maven 3

**Recommended:**

* linux operating system
* docker and docker compose
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

# Add environment variables
echo 'export CLIENTSECRET=<<yourClientSecret>>' >> ~/.bashrc 
echo 'export CLIENTID=<<yourClientID>>' >> ~/.bashrc 
echo 'export JAVA_HOME=/usr/lib/jvm/default-java' >> ~/.bashrc 

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
                "PROJECT_NAME": "FOO_PROJECT",
                "SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GITHUB_CLIENTID": "edba8e9e17f1c045633e",
                "SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GITHUB_CLIENTSECRET": "<<yourClientSecret>>"
            }            
        }
    ]
}
```

## MariaDB Installation and Configuration

Go to workplace folder and run the following command:

```bash
docker-compose -f mysql-docker-compose up
```

Configure the database Connection in VS Code Client as followed:

```
Username:ljprojectbuilder
Database:ljprojectbuilder
Password:ljprojectbuilder
```

To be able to login to projectBuilder by your Github Account, you have to add your GitHub-User to your database:

```sql
INSERT INTO `ALLOWEDUSER` (`USERALIAS`, `USERROLE`) VALUES
('your-github-user', 'PBUSER'),
```
## Install ljprojectbuilder via Maven

- go to directory ljprojectbuilder and open command line
- Build app running all tests:
  ```bash
  mvn clean package -P frontend
  ```
### Run ljprojectbuilder

You can run the main-class via Visual Studio Code, or start the build artifact (located in `application/target`) via command line with `java -jar application-0.0.1-SNAPSHOT.jar`. Hence, Project Builder can be reached under http://localhost:8081/ljprojectbuilder/.

### Install ljprojectbuilder (pre-built docker-image) via docker-compose

Go to workplace folder and run the following command:

```bash
sudo docker-compose -f mysql-docker-compose up
```