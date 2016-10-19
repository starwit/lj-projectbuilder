/** 
 * Navigation and routing for module ljprojectbuilderApp.project.
 */
(function() {
'use strict';

	angular.module('${appName}App.${domain?lower_case}').factory('goto${domain}', goto${domain});

	function goto${domain}($location) {
		var factory = {};
		factory.all = function() {
	    	$location.path('/viewcomponents/${domain?lower_case}-all/');
	    },
	    factory.update = function(id) {
	    	$location.path('/viewcomponents/${domain?lower_case}-maintain/update/' + id);
	    },
	    factory.create = function() {
	    	$location.path('/viewcomponents/${domain?lower_case}-maintain/create/');
	    },
	    factory.loaderror = function() {
	    	$location.path('/');
	    }
		return factory;
    };
    
   	angular.module('${appName}App.${domain?lower_case}') 
   	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/viewcomponents/${domain?lower_case}-all/', {
			controller : '${domain?lower_case}AllCtrl',
			controllerAs : 'ctrl',
			title : "${domain?lower_case}.all.title",
			subtitle : "",
			templateUrl : "viewcomponents/${domain?lower_case}/${domain?lower_case}${templateAll}",
	        resolve: {
	        	${domain?lower_case}ConnectorFactory: ${domain?lower_case}ConnectorFactory
	         }
		}).when('/viewcomponents/${domain?lower_case}-maintain/create/', {
			controller : '${domain?lower_case}SingleCtrl',
			controllerAs : 'ctrl',
			title : "${domain?lower_case}.create.title",
			subtitle : "",
			templateUrl : "viewcomponents/${domain?lower_case}/${domain?lower_case}${templateSingle}",
			resolve: {
	        	${domain?lower_case}ConnectorFactory: ${domain?lower_case}ConnectorFactory
	         }		
		}).when('/viewcomponents/${domain?lower_case}-maintain/update/:id', {
			controller : '${domain?lower_case}SingleCtrl',
			controllerAs : 'ctrl',
			title : "${domain?lower_case}.update.title",
			subtitle : "",
			templateUrl : "viewcomponents/${domain?lower_case}/${domain?lower_case}${templateSingle}",
	        resolve: {
	        	${domain?lower_case}ConnectorFactory: ${domain?lower_case}ConnectorFactory
	         }		
		});
	}]);
})();