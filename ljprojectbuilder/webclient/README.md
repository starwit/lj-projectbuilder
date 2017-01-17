# Introduction Webclient
## app.module.js

### module definition

In *app.module.js*, a module named *ljprojectbuilderApp* is created: 

```
angular.module('ljprojectbuilderApp', [
```
This module is used as app as we will see later.

A module contains the different components of an AngularJS app. In our case, all defined domains are components. We created the domains *project*, *domain* and *generator*. to prevent confusion if you want to add more than one app in future, the prefix *ljprojectbuilderApp.* is added to each module contained in *ljprojectbuilderApp*. We used also the module *pascalprecht.translate* for language translation and *ngRoute* for navigation:

```
angular.module('ljprojectbuilderApp', [
	    'pascalprecht.translate',
	  
	    //###BEGIN### include generated files
		'ljprojectbuilderApp.project',
		'ljprojectbuilderApp.domain',
		'ljprojectbuilderApp.generator',
		//###END### include generated files
		'ngRoute'
	]);
```

### global controller definition

To handle global stuff in the app, a controller is defined:


```
	/**
	 * Controller for global behavior when changing the view (routeChange).
	 * @param $scope
	 * @returns
	 */
	function appController($scope, $window) {
		$scope.locale = $window.navigator.language || $window.navigator.userLanguage; 
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title=next.title;
			$scope.subtitle=next.subtitle;
		});
	}
```

This controller is added to the module *ljprojectbuilderApp*:


```
	angular.module('innovationlabApp').controller('appController', appController);
```
## index.html

In index.html, you will find <html lang="<%= locale %>" ng-app="ljprojectbuilderApp"  ng-controller="appController"> as first line. That means:

1. ng-app="ljprojectbuilderApp" is added. 'ng-app' is a *directive*. The module 'ljprojectbuilderApp' will be available in the <html> tag and defines the application scope.
2. The directive ng-controller="appController" adds the controller *appController* (defined in app.module.js) to the page. The controller has an own scope. Hence, all properties attached to $scope in appController are avaiable in the whole page. E.g. to access $scope.title, we use *{{title}}*. {{title}} is an *expression*. Expressions are used to display values on the page.

