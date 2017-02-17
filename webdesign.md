
# Webdesign

## Abstract
Lots of Java-based webapplications lack to involve webdesigners in the usual development workflow. Usually, a webdesigner creates html-sites as templates and a web-developer has to rewrite and add them into the sourcecode management system – you spend twice the time you needed. It would be great, if the webdesigner can add the design changes directly in the sourcecode repository. Because of the strict seperation of business logic, interaction via AngularJS and presentation via HTML, it is easy to involve webdesigners. Also the webdesigner can build the project in only one step and the changes he made are displayed immediately.

## Prequisites
You only need to install JDK 1.8, maven and ant to build your web-application. With an ant-spript (build.xml) located in <>-directory, everything a webdesigner need is build.

## Ant setup_project
Configures tomee-server, builds the project and starts the tomee-server with the webapplication included.
In the folder webclient/src/main/webapp you can find all frontend code and include your changes.
Ant copy_frontend_local

Displays all changes in webclient project. All changes you did in webclient/src/main/webapp are transfered to the deployment-directory of the tomee-server.
