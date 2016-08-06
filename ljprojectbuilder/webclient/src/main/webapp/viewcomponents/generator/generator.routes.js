'use strict';

angular.module('ljprojectbuilderApp.generator', ['ngRoute','pascalprecht.translate'])
.controller(generatorControllers)
.factory('projectConnectorFactory', projectConnectorFactory)
.factory('projectSetupConnectorFactory', projectSetupConnectorFactory)
.factory('domainConnectorFactory', domainConnectorFactory)
.factory('generatorConnectorFactory', generatorConnectorFactory)

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/viewcomponents/generator/generate/:id', {
		controller : 'generatorController',
		title : "project.generate.title",
		templateUrl : "viewcomponents/generator/generate.html"
	});
}]);