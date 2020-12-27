/** 
 * Navigation and routing for module ljprojectbuilderApp.project.
 */
(function() {
'use strict';

	angular.module('ljprojectbuilderApp.project').factory('gotoProject', gotoProject);
	
	function gotoProject($location) {
		var factory = {};
		factory.all = function() {
	    	$location.path('/viewcomponents/project-all/');
	    },
	    factory.update = function(id) {
	    	$location.path('/viewcomponents/project-maintain/update/' + id);
	    },
	    factory.create = function() {
	    	$location.path('/viewcomponents/project-maintain/create/');
	    },
	    factory.loaderror = function() {
	    	$location.path('/');
	    },
		factory.domain = function(id) {
			$location.path('/viewcomponents/domain-byproject/' + id);
	    },
		factory.generate = function(id) {
			$location.path('/viewcomponents/generator/generate/' + id);
	    }
		return factory;
    };
	
	angular.module('ljprojectbuilderApp.project')
	.config(['$routeProvider', function($routeProvider) {
		  $routeProvider.when('/viewcomponents/project-all/', {
				controller : 'projectAllCtrl',
				controllerAs : 'ctrl',
				title : "project.all.title",
				subtitle : "",
				templateUrl : "viewcomponents/project/project.all.html",
	            resolve: {
	            	projectConnectorFactory: projectConnectorFactory
	             }
			}).when('/viewcomponents/project-maintain/create/', {
				controller : 'projectSingleCtrl',
				controllerAs : 'ctrl',
				title : "project.create.title",
				subtitle : "project.create.title",
				templateUrl : "viewcomponents/project/project.single.html",
	            resolve: {
	            	projectConnectorFactory: projectConnectorFactory,
	            	dialogService: dialogService
	             }
			}).when('/viewcomponents/project-maintain/update/:projectid', {
				title : "project",
				subtitle : "project.update.title",
				templateUrl : "viewcomponents/project/project.single.html",
				controller : 'projectSingleCtrl',
				controllerAs : 'ctrl',
	            resolve: {
	            	projectConnectorFactory: projectConnectorFactory,
	            	dialogService: dialogService
	             }
			});
	}]);
})();