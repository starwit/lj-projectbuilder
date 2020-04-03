/** 
 * Navigation and routing for module ljprojectbuilderApp.generator.
 */
(function() {
	'use strict';
	
	angular.module('ljprojectbuilderApp.generator').factory('gotoGenProjectTemplate', gotoGenProjectTemplate);
	
	function gotoGenProjectTemplate($location) {
		var factory = {};
	    factory.update = function(id) {
	    	$location.path('/viewcomponents/projecttemplate-maintain/update/' + id + '/' + false);
	    }
	    return factory;
	};
	
	angular.module('ljprojectbuilderApp.generator').config(['$routeProvider', function($routeProvider) {
	  $routeProvider.when('/viewcomponents/generator/generate/:id', {
		  title : "project",
		  subtitle : "generator.title",
		  templateUrl : "viewcomponents/generator/generator.html",
		  controller : 'generatorCtrl',
		  controllerAs : 'ctrl',
		})
	}]);
})();