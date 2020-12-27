/** 
 * Navigation and routing for module '${project.title?lower_case}App.${domain.name?lower_case}.
 */
(function() {
'use strict';

	angular.module('${project.title?lower_case}App.${domain.name?lower_case}').factory('goto${domain.name}', goto${domain.name});

	function goto${domain.name}($location) {
		var factory = {};
		factory.all = function() {
	    	$location.path('/viewcomponents/${domain.name?lower_case}-all/');
	    },
	    factory.update = function(id) {
	    	$location.path('/viewcomponents/${domain.name?lower_case}-maintain/update/' + id);
	    },
	    factory.create = function() {
	    	$location.path('/viewcomponents/${domain.name?lower_case}-maintain/create/');
	    },
	    factory.loaderror = function() {
	    	$location.path('/');
	    }
		return factory;
    };
    
   	angular.module('${project.title?lower_case}App.${domain.name?lower_case}') 
   	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/viewcomponents/${domain.name?lower_case}-all/', {
			controller : '${domain.name?lower_case}AllCtrl',
			controllerAs : 'ctrl',
			title : "${domain.name?lower_case}.all.title",
			subtitle : "",
			templateUrl : "viewcomponents/${domain.name?lower_case}/${domain.name?lower_case}.all.html",
	        resolve: {
	        	${domain.name?lower_case}ConnectorFactory: ${domain.name?lower_case}ConnectorFactory
	         }
		}).when('/viewcomponents/${domain.name?lower_case}-maintain/create/', {
			controller : '${domain.name?lower_case}SingleCtrl',
			controllerAs : 'ctrl',
			title : "${domain.name?lower_case}.create.title",
			subtitle : "",
			templateUrl : "viewcomponents/${domain.name?lower_case}/${domain.name?lower_case}.single.html",
			resolve: {
	        	${domain.name?lower_case}ConnectorFactory: ${domain.name?lower_case}ConnectorFactory
	         }		
		}).when('/viewcomponents/${domain.name?lower_case}-maintain/update/:id', {
			controller : '${domain.name?lower_case}SingleCtrl',
			controllerAs : 'ctrl',
			title : "${domain.name?lower_case}.update.title",
			subtitle : "",
			templateUrl : "viewcomponents/${domain.name?lower_case}/${domain.name?lower_case}.single.html",
	        resolve: {
	        	${domain.name?lower_case}ConnectorFactory: ${domain.name?lower_case}ConnectorFactory
	         }		
		});
	}]);
})();