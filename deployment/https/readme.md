This is configured for running on server for domain pb.starwit.de.

* 1. if you change domain configure / check domain in all files of this folder
* 2. create server on hetzner
* 3. create volume on named lj-mariadb
* 4. login to server via ssh
* 5. setup machine and install docker compose:

```bash
    apt update
    apt install \
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
    apt update

    # Install correct version of docker-compose
    curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    chmod +x /usr/local/bin/docker-compose
    ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose

    apt install docker-ce docker-ce-cli containerd.io docker-compose
```
* 6. restart machine and relogin
* 7. checkout project and go to `/lj-projectbuilder/deployment/https`
* 8. set passwords in env.sh and execute `source env.sh` to set environment variables dor docker compose scripts
* 9. execute `bash init-letsencrypt.sh` to get initial credentials. It uses docker-compose.yml (nginx + certbot) in order to get initial letsencrypt certificates.
* 10. execute `projectbuilder-docker-compose up -d` and `env-docker-compose up -d`
   * !!! if you change database passwords after initial starting of docker compose scripts, it wont't work
* 11. chech network and open ports `netstat -tulpn`
* go to https://pb.startwit.de/auth, login and create user for projectbuilder
* check prohectbuilder on https://pb.startwit.de/
