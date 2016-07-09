'use strict';

angular.module('ljprojectbuilderApp.domain', ['ngRoute','pascalprecht.translate']).value('gotoDomain', {
    all: function(location, projectid) {
    	location.path('/viewcomponents/domain-byproject/' + projectid);
    },
    update: function(location, projectid, id) {
    	location.path('/viewcomponents/domain-maintain/update/' + projectid + '/' + id);
    },
    create: function(location, projectid) {
    	location.path('/viewcomponents/domain-maintain/create/' + projectid);
    },
    back: function(location) {
    	location.path('/');
    }    
})
.controller(domainControllers)
.factory('domainConnectorFactory', domainConnectorFactory)

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/viewcomponents/domain-byproject/:projectid', {
		controller : 'loadDomainController',
		title : "domain.all.title",
		subtitle : "",
		templateUrl : "viewcomponents/domain/domain.all.html"
	}).when('/viewcomponents/domain-maintain/create/:projectid', {
		controller : 'maintainDomainController',
		title : "domain.create.title",
		subtitle : "",
		mode:"create",
		templateUrl : "viewcomponents/domain/domain.single.html"
	}).when('/viewcomponents/domain-maintain/update/:projectid/:id', {
		controller : 'maintainDomainController',
		title : "domain.update.title",
		subtitle : "",
		mode:"update",
		templateUrl : "viewcomponents/domain/domain.single.html"
	});
}]);

