(function() {
	'use strict';

	angular.module('ljprojectbuilderApp.generator').config(['$routeProvider', function($routeProvider) {
	  $routeProvider.when('/viewcomponents/generator/generate/:id', {
			controller : 'generatorCtrl',
			controllerAs : 'ctrl',
			title : "project.generate.title",
			templateUrl : "viewcomponents/generator/generator.html"
		});
	}]);
})();