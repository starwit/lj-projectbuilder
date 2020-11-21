/** 
 * Navigation and routing for module ljprojectbuilderApp.domain.
 */
(function() {
	'use strict';

	angular.module('ljprojectbuilderApp.default');
	
	/**
	 * Routing for module.
	 */
	angular.module('ljprojectbuilderApp.default').config(['$routeProvider', function($routeProvider) {
	  $routeProvider.when('/viewcomponents/welcome', {
			templateUrl : "viewcomponents/default/welcome.html"
		}).when('/viewcomponents/notallowed', {
			templateUrl : "viewcomponents/default/403.html"
		}).when('/viewcomponents/internalerror', {
			templateUrl : "viewcomponents/default/500.html"
		});
	}]);
})();