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
		factory.all = function(appid) {
			$location.path('/viewcomponents/domain-byapp/' + appid);
	    },
	    factory.update = function(appid, id) {
	    	$location.path('/viewcomponents/domain-maintain/update/' + appid + '/' + id);
	    },
	    factory.create = function(appid) {
	    	$location.path('/viewcomponents/domain-maintain/create/' + appid);
	    },
	    factory.loaderror = function() {
	    	$location.path('/viewcomponents/app-all/');
	    },
		factory.prev = function(appid) {
			$location.path('/viewcomponents/app-maintain/update/' + appid);
	    },
		factory.detail = function(appid) {
	    	$location.path('/viewcomponents/app-maintain/update/' + appid);
	    },
		factory.generate = function(appid) {
			$location.path('/viewcomponents/generator/generate/' + appid);
	    },
		factory.next = function(appid) {
			$location.path('/viewcomponents/generator/generate/' + appid);
	    }
	    return factory;
	};

	/**
	 * Routing for module.
	 */
	angular.module('ljprojectbuilderApp.domain').config(['$routeProvider', function($routeProvider) {
	  $routeProvider.when('/viewcomponents/domain-byapp/:appid', {
			controller : 'domainAllCtrl',
			controllerAs : 'ctrl',
			title : "app",
			subtitle : "domain.all.title",
			templateUrl : "viewcomponents/domain/domain.all.html"
		}).when('/viewcomponents/domain-maintain/create/:appid', {
			controller : 'domainSingleCtrl',
			controllerAs : 'ctrl',
			title : "app",
			subtitle : "domain.create.title",
			templateUrl : "viewcomponents/domain/domain.single.html"
		}).when('/viewcomponents/domain-maintain/update/:appid/:id', {
			controller : 'domainSingleCtrl',
			controllerAs : 'ctrl',
			title : "app",
			subtitle : "domain.update.title",
			templateUrl : "viewcomponents/domain/domain.single.html"
		});
	}]);
})();