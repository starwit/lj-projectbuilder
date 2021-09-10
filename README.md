# Project Builder

## Motivation

Creating modern software always requires a number of things to do. One of these things is to persist data and to access persisted data. Finally the data should be presented in a userfriendly manner. Yet still a lot of boiler plate code needs to be written and creating application quickly remains a challenge. Lirejarp Project Builder is an application to kickstart new project via using templates. The Project Builder provides a configuration interfaces.

A fully working example application of this pattern can be found is the [lirejarp template](https://github.com/starwit/lirejarp). Besides using described pattern it also contains an Javascript based client application that serves a UI to the Java based backend. This rigorous separation of UI is also intended as it helps to demonstrate architecture approach without mixing with UI issues.

This is a tool that helps you create a new project based on the [lirejarp template](https://github.com/starwit/lirejarp). Setup of a new project consists of two things: renaming all elements according to your project needs (files, packages, war-file) and creating base functionality for new domain objects. So project builder is a tool that collects in a GUI some data for your new project. Once you provided a new project name and your desired domain objects, your check out copy of LireJarp or other templates is modified and enhanced accordingly.

## Prerequisites

* Kubernetes Cluster
* Helm

## Installation

Go to helm/ljprojectbuilder
- copy values-template4private.yaml to values-private.yaml and add secret values
- execute e.g.:
  - for local cluster: `helm install ljprojectbuilder . -n ljprojectbuilder -f values.yaml -f values-private.yaml`
  - for hetzner cluster: `helm install ljprojectbuilder . -n ljprojectbuilder -f values.yaml -f values-hetzner.yaml`

## Further Information
* [build and release project](docs/build-release-mgmt.md)
* [installation on local PC](docs/local-installation.md)
* [how to create templates](docs/templates-howto.md)
* [architecture description for developer](docs/architecture.md)

## Screenshot
![ljProjectBuilder](docs/imgs/lJProjectBuilder.png)