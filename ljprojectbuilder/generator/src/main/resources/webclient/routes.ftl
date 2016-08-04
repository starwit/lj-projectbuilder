'use strict';

angular.module('${appName}App.${domain?lower_case}', ['ngRoute','pascalprecht.translate']).value('goto${domain}', {
    all: function(location) {
    	location.path('/viewcomponents/${domain?lower_case}-all/');
    },
    update: function(location, id) {
    	location.path('/viewcomponents/${domain?lower_case}-maintain/update/' + id);
    },
    create: function(location) {
    	location.path('/viewcomponents/${domain?lower_case}-maintain/create/');
    },
    back: function(location) {
    	location.path('/');
    }    
})
.controller(${domain?uncap_first}Controllers)
.factory('${domain?uncap_first}ConnectorFactory', ${domain?uncap_first}ConnectorFactory)

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/viewcomponents/${domain?lower_case}-all/', {
		controller : 'load${domain}Controller',
		title : "${domain?lower_case}.all.title",
		subtitle : "",
		templateUrl : "viewcomponents/${domain?lower_case}/${domain?lower_case}${templateAll}"
	}).when('/viewcomponents/${domain?lower_case}-maintain/create/', {
		controller : 'maintain${domain}Controller',
		title : "${domain?lower_case}.create.title",
		subtitle : "",
		mode:"create",
		templateUrl : "viewcomponents/${domain?lower_case}/${domain?lower_case}${templateSingle}"
	}).when('/viewcomponents/${domain?lower_case}-maintain/update/:id', {
		controller : 'maintain${domain}Controller',
		title : "${domain?lower_case}.update.title",
		subtitle : "",
		mode:"update",
		templateUrl : "viewcomponents/${domain?lower_case}/${domain?lower_case}${templateSingle}"
	});
}]);

