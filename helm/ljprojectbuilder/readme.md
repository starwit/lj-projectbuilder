# Deployment with helm

This is a guide on how to deploy this application with helm from the local folder.

## Preparation

To deploy this application in kubernetes with helm, make sure that:

1. kubectl is installed on your machine
2. kubectl is configured for your kubernetes cluster
3. helm is installed on your machine
4. A docker container of the application is available with the name:  
starwit/lj-projectbuilder/ljprojectbuilder

## Deploying the app

Please use a terminal for the following steps.

1. From the projects root folder (ljprojectbuilder), go to helm/ljprojectbuilder

```bash
cd helm/ljprojectbuilder
```

2. Update your dependencies

```bash
helm dep update
```

3. Install your application with a deployment name of your choosing and the following code

```bash
helm install <DEPLOYMENT-NAME> .
```

4. Wait for the application to deploy completely (kwatch is your friend ;) )

5. Enjoy your deployment

**HAPPY HELMING!**

## Constacts

- Rolf Simon (Author)
