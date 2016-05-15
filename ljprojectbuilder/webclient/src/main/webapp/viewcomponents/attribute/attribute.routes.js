'use strict';

angular.module('ljprojectbuilderApp.attribute', ['ngRoute','pascalprecht.translate']).value('gotoAttribute', {
    all: function(location) {
    	location.path('/viewcomponents/attribute-all/');
    },
    update: function(location, id) {
    	location.path('/viewcomponents/attribute-maintain/update/' + id);
    },
    create: function(location) {
    	location.path('/viewcomponents/attribute-maintain/create/');
    },
    back: function(location) {
    	location.path('/');
    }    
})
.controller(attributeControllers)
.factory('attributeConnectorFactory', attributeConnectorFactory)

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/viewcomponents/attribute-all/', {
		controller : 'loadAttributeController',
		title : "attribute.all.title",
		subtitle : "",
		templateUrl : "viewcomponents/attribute/attribute.all.html"
	}).when('/viewcomponents/attribute-maintain/create/', {
		controller : 'maintainAttributeController',
		title : "attribute.create.title",
		subtitle : "",
		mode:"create",
		templateUrl : "viewcomponents/attribute/attribute.single.html"
	}).when('/viewcomponents/attribute-maintain/update/:id', {
		controller : 'maintainAttributeController',
		title : "attribute.update.title",
		subtitle : "",
		mode:"update",
		templateUrl : "viewcomponents/attribute/attribute.single.html"
	});
}]);

