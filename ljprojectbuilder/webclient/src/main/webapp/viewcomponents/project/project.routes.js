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
	    factory.back = function() {
	    	$location.path('/');
	    }
		return factory;
    };
	
	angular.module('ljprojectbuilderApp.project')
	.config(['$routeProvider', function($routeProvider) {
		  $routeProvider.when('/viewcomponents/project-all/', {
				controller : 'projectAllCtrl',
				title : "project.all.title",
				subtitle : "",
				templateUrl : "viewcomponents/project/project.all.html"
			}).when('/viewcomponents/project-maintain/create/', {
				controller : 'projectSingleCtrl',
				title : "project.create.title",
				subtitle : "",
				templateUrl : "viewcomponents/project/project.single.html"
			}).when('/viewcomponents/project-maintain/update/:id', {
				controller : 'projectSingleCtrl',
				title : "project.update.title",
				subtitle : "",
				templateUrl : "viewcomponents/project/project.single.html"
			});
	}]);
})();