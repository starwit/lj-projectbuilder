(function() {
'use strict';

	angular.module('ljprojectbuilderApp.project').value('gotoProject', {
	    all: function(location) {
	    	location.path('/viewcomponents/project-all/');
	    },
	    update: function(location, id) {
	    	location.path('/viewcomponents/project-maintain/update/' + id);
	    },
	    create: function(location) {
	    	location.path('/viewcomponents/project-maintain/create/');
	    },
	    back: function(location) {
	    	location.path('/');
	    }    
	});
	
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
				mode:"create",
				templateUrl : "viewcomponents/project/project.single.html"
			}).when('/viewcomponents/project-maintain/update/:id', {
				controller : 'projectSingleCtrl',
				title : "project.update.title",
				subtitle : "",
				mode:"update",
				templateUrl : "viewcomponents/project/project.single.html"
			});
	}]);
})();