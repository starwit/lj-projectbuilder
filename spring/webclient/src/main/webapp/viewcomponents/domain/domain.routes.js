/** 
 * Navigation and routing for module ljprojectbuilderApp.domain.
 */
(function() {
	'use strict';

	angular.module('ljprojectbuilderApp.domain').factory('gotoDomain', gotoDomain);
	
	/**
	 * Navigation for module reachable in view.
	 * @param $location - AngularJS location variable (injected).
	 * @returns factory with all needed navigation paths.
	 */
	function gotoDomain($location) {
		var factory = {};
		factory.all = function(projectid) {
			$location.path('/viewcomponents/domain-byproject/' + projectid);
	    },
	    factory.update = function(projectid, id) {
	    	$location.path('/viewcomponents/domain-maintain/update/' + projectid + '/' + id);
	    },
	    factory.create = function(projectid) {
	    	$location.path('/viewcomponents/domain-maintain/create/' + projectid);
	    },
	    factory.loaderror = function() {
	    	$location.path('/viewcomponents/project-all/');
	    },
		factory.prev = function(projectid) {
			$location.path('/viewcomponents/project-maintain/update/' + projectid);
	    },
		factory.detail = function(projectid) {
	    	$location.path('/viewcomponents/project-maintain/update/' + projectid);
	    },
		factory.generate = function(projectid) {
			$location.path('/viewcomponents/generator/generate/' + projectid);
	    },
		factory.next = function(projectid) {
			$location.path('/viewcomponents/generator/generate/' + projectid);
	    }
	    return factory;
	};

	/**
	 * Routing for module.
	 */
	angular.module('ljprojectbuilderApp.domain').config(['$routeProvider', function($routeProvider) {
	  $routeProvider.when('/viewcomponents/domain-byproject/:projectid', {
			controller : 'domainAllCtrl',
			controllerAs : 'ctrl',
			title : "project",
			subtitle : "domain.all.title",
			templateUrl : "viewcomponents/domain/domain.all.html"
		}).when('/viewcomponents/domain-maintain/create/:projectid', {
			controller : 'domainSingleCtrl',
			controllerAs : 'ctrl',
			title : "project",
			subtitle : "domain.create.title",
			templateUrl : "viewcomponents/domain/domain.single.html"
		}).when('/viewcomponents/domain-maintain/update/:projectid/:id', {
			controller : 'domainSingleCtrl',
			controllerAs : 'ctrl',
			title : "project",
			subtitle : "domain.update.title",
			templateUrl : "viewcomponents/domain/domain.single.html"
		});
	}]);
})();