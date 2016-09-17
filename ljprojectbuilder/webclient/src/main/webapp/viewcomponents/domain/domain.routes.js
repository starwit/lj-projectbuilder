(function() {
	'use strict';

	angular.module('ljprojectbuilderApp.domain').factory('gotoDomain', gotoDomain);
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
	    factory.back = function() {
	    	$location.path('/');
	    }
	    return factory;
	};

	angular.module('ljprojectbuilderApp.domain').config(['$routeProvider', function($routeProvider) {
	  $routeProvider.when('/viewcomponents/domain-byproject/:projectid', {
			controller : 'domainAllCtrl',
			controllerAs : 'ctrl',
			title : "domain.all.title",
			subtitle : "",
			templateUrl : "viewcomponents/domain/domain.all.html"
		}).when('/viewcomponents/domain-maintain/create/:projectid', {
			controller : 'domainSingleCtrl',
			controllerAs : 'ctrl',
			title : "domain.create.title",
			subtitle : "",
			templateUrl : "viewcomponents/domain/domain.single.html"
		}).when('/viewcomponents/domain-maintain/update/:projectid/:id', {
			controller : 'domainSingleCtrl',
			controllerAs : 'ctrl',
			title : "domain.update.title",
			subtitle : "",
			templateUrl : "viewcomponents/domain/domain.single.html"
		});
	}]);
})();