VERSION   	   := $(shell (mvn help:evaluate -Dexpression=project.version -q -DforceStdout))
HELM_CHART_NAME	:= ljprojectbuilder
HELM_PATH      	:= helm/$(HELM_CHART_NAME)
HELM_VERSION   	:= $(shell (yq read $(HELM_PATH)/Chart.yaml version))
BUILD          = $(shell pwd)

SETTINGS_XML ?= settings.xml

.PHONY: generate package deploy guard-% docker-build docker-push dev-maven dev-docker dev-helm prod-maven prod-docker prod-helm

guard-%:
	@ if [ "${${*}}" = "" ]; then \
		echo "Environment variable $* not set"; \
		exit 1; \
	fi

dev-maven: guard-BUILD_NUMBER
dev-maven: VERSION := $(VERSION)$(BUILD_NUMBER)
dev-maven: set-version
dev-maven: lirejarp-deploy

dev-docker: guard-BUILD_NUMBER
dev-docker: VERSION := $(VERSION)$(BUILD_NUMBER)
dev-docker: docker-build
dev-docker: docker-push

dev-helm: guard-BUILD_NUMBER
dev-helm: HELM_VERSION := $(HELM_VERSION).$(BUILD_NUMBER)
dev-helm: helm-set-version
dev-helm: helm-package
dev-helm: helm-upload

prod-maven: lirejarp-deploy

prod-docker: docker-build
prod-docker: docker-push

prod-helm: helm-package
prod-helm: helm-upload

set-version: 
	mvn versions:set -DnewVersion=$(VERSION)

lirejarp-deploy:
	npm install --prefix webclient/app
	mvn clean deploy -Pfrontend

helm-set-version:
	yq write -i $(HELM_PATH)/Chart.yaml version $(HELM_VERSION)

helm-package:
	helm repo update
	helm dep update $(HELM_PATH)
	helm package $(HELM_PATH)

helm-upload: guard-BUILD_USER guard-BUILD_PASSWORD
  # TODO: Add the HELM-REPO of your organisation
	curl -u$(BUILD_USER):$(BUILD_PASSWORD) -T $(HELM_CHART_NAME)-$(HELM_VERSION).tgz https://<HELM-REPO>/$(HELM_CHART_NAME)-$(HELM_VERSION).tgz 

docker-build: guard-BUILD_USER guard-BUILD_PASSWORD guard-HTTP_PROXY guard-VERSION
	#	download dependencies
  # TODO: use wget to download the previously uploaded jar 
  # TODO: Add your docker repository
	wget --user=$(BUILD_USER) --password=$(BUILD_PASSWORD) <DOWNLOAD-LINK-TO-JAR> -O application.jar
	docker build --no-cache -t <YOUR-DOCKER-REPO>/ljprojectbuilder:$(VERSION) --build-arg http_proxy=$(HTTP_PROXY) --build-arg https_proxy=$(HTTP_PROXY) .

unset-proxy:
	unset http_proxy
	unset https_proxy

docker-push: guard-BUILD_USER guard-BUILD_PASSWORD
  # TODO: Login to your Docker repo
  # TODO: Push the build container with the tag from docker-build
	docker login -u $(BUILD_USER) -p $(BUILD_PASSWORD) <YOUR-DOCKER-REPO>
	docker push <YOUR-DOCKER-REPO>/ljprojectbuilder:$(VERSION)