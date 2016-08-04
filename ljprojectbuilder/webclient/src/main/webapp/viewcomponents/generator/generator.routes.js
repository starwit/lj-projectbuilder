'use strict';

angular.module('ljprojectbuilderApp.project', ['ngRoute','pascalprecht.translate']).value('gotoProject', {
    all: function(location) {
    	location.path('/viewcomponents/project-all/');
    },
    update: function(location, id) {
    	location.path('/viewcomponents/project-maintain/update/' + id);
    },
    create: function(location) {
    	location.path('/viewcomponents/project-maintain/create/');
    },
    back: function(location) {
    	location.path('/');
    }    
})
.controller(generatorControllers)
.factory('projectConnectorFactory', projectConnectorFactory)
.factory('projectSetupConnectorFactory', projectSetupConnectorFactory)
.factory('domainConnectorFactory', domainConnectorFactory)
.factory('generatorConnectorFactory', domainConnectorFactory)

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/viewcomponents/generate/:id', {
		controller : 'generatorController',
		title : "project.generate.title",
		subtitle : "",
		mode:"generate",
		templateUrl : "viewcomponents/generator/generate.html"
	});
}]);