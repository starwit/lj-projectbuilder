/** 
 * Navigation and routing for module ljprojectbuilderApp.generator.
 */
(function() {
	'use strict';
	
	angular.module('ljprojectbuilderApp.generator').factory('gotoGenAppTemplate', gotoGenAppTemplate);
	
	function gotoGenAppTemplate($location) {
		var factory = {};
	    factory.update = function(id) {
	    	$location.path('/viewcomponents/apptemplate-maintain/update/' + id + '/' + false);
	    }
	    return factory;
	};
	
	angular.module('ljprojectbuilderApp.generator').config(['$routeProvider', function($routeProvider) {
	  $routeProvider.when('/viewcomponents/generator/generate/:id', {
		  title : "app",
		  subtitle : "generator.title",
		  templateUrl : "viewcomponents/generator/generator.html",
		  controller : 'generatorCtrl',
		  controllerAs : 'ctrl',
		})
	}]);
})();