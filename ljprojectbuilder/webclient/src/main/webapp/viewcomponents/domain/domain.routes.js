'use strict';

angular.module('ljprojectbuilderApp.domain', ['ngRoute','pascalprecht.translate']).value('gotoDomain', {
    all: function(location) {
    	location.path('/viewcomponents/domain-all/');
    },
    update: function(location, id) {
    	location.path('/viewcomponents/domain-maintain/update/' + id);
    },
    create: function(location) {
    	location.path('/viewcomponents/domain-maintain/create/');
    },
    back: function(location) {
    	location.path('/');
    }    
})
.controller(domainControllers)
.factory('domainConnectorFactory', domainConnectorFactory)

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/viewcomponents/domain-all/', {
		controller : 'loadDomainController',
		title : "domain.all.title",
		subtitle : "",
		templateUrl : "viewcomponents/domain/domain.all.html"
	}).when('/viewcomponents/domain-maintain/create/', {
		controller : 'maintainDomainController',
		title : "domain.create.title",
		subtitle : "",
		mode:"create",
		templateUrl : "viewcomponents/domain/domain.single.html"
	}).when('/viewcomponents/domain-maintain/update/:id', {
		controller : 'maintainDomainController',
		title : "domain.update.title",
		subtitle : "",
		mode:"update",
		templateUrl : "viewcomponents/domain/domain.single.html"
	});
}]);

