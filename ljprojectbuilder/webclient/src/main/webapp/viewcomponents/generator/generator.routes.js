/** 
 * Navigation and routing for module ljprojectbuilderApp.generator.
 */
(function() {
	'use strict';
	
	angular.module('ljprojectbuilderApp.generator').config(['$routeProvider', function($routeProvider) {
	  $routeProvider.when('/viewcomponents/generator/generate/:id', {
		  title : "project",
		  subtitle : "generator.title",
		  templateUrl : "viewcomponents/generator/generator.html",
		  controller : 'generatorCtrl',
		  controllerAs : 'ctrl',
		});
	}]);
})();