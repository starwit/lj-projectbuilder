'use strict';

var ${domain?uncap_first}Module = angular.module('${appName}App.${domain?lower_case}', ['ngRoute','pascalprecht.translate']).value('goto${domain}', {
    all: function(location) {
    	location.path('/views/${domain?lower_case}-all/');
    },
    update: function(location, id) {
    	location.path('/views/${domain?lower_case}-maintain/update/' + id);
    },
    create: function(location) {
    	location.path('/views/${domain?lower_case}-maintain/create/');
    },
    back: function(location) {
    	location.path('/');
    }    
});

${domain?uncap_first}Module.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/views/${domain?lower_case}-all/', {
		controller : 'load${domain}Controller',
		title : "${domain?lower_case}.all.title",
		subtitle : "",
		templateUrl : "views/${domain?lower_case}/${domain?lower_case}${templateAll}"
	}).when('/views/${domain?lower_case}-maintain/create/', {
		controller : 'maintain${domain}Controller',
		title : "${domain?lower_case}.create.title",
		subtitle : "",
		mode:"create",
		templateUrl : "views/${domain?lower_case}/${domain?lower_case}${templateSingle}"
	}).when('/views/${domain?lower_case}-maintain/update/:id', {
		controller : 'maintain${domain}Controller',
		title : "${domain?lower_case}.update.title",
		subtitle : "",
		mode:"update",
		templateUrl : "views/${domain?lower_case}/${domain?lower_case}${templateSingle}"
	});
}]);

${domain?uncap_first}Module.controller(${domain?uncap_first}Controllers);
${domain?uncap_first}Module.factory('${domain?uncap_first}ConnectorFactory', ${domain?uncap_first}ConnectorFactory);
