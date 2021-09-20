/** 
 * Navigation and routing for module ljappbuilderApp.app.
 */
(function() {
'use strict';

	angular.module('ljappbuilderApp.app').factory('gotoApp', gotoApp);
	
	function gotoApp($location) {
		var factory = {};
		factory.all = function() {
	    	$location.path('/viewcomponents/app-all/');
	    },
	    factory.update = function(id) {
	    	$location.path('/viewcomponents/app-maintain/update/' + id);
	    },
	    factory.create = function() {
	    	$location.path('/viewcomponents/app-maintain/create/');
	    },
	    factory.loaderror = function() {
	    	$location.path('/');
	    },
		factory.domain = function(id) {
			$location.path('/viewcomponents/domain-byapp/' + id);
	    },
		factory.generate = function(id) {
			$location.path('/viewcomponents/generator/generate/' + id);
	    }
		return factory;
    };
	
	angular.module('ljappbuilderApp.app')
	.config(['$routeProvider', function($routeProvider) {
		  $routeProvider.when('/viewcomponents/app-all/', {
				controller : 'appAllCtrl',
				controllerAs : 'ctrl',
				title : "app.all.title",
				subtitle : "",
				templateUrl : "viewcomponents/app/app.all.html",
	            resolve: {
	            	appConnectorFactory: appConnectorFactory
	             }
			}).when('/viewcomponents/app-maintain/create/', {
				controller : 'appSingleCtrl',
				controllerAs : 'ctrl',
				title : "app.create.title",
				subtitle : "app.create.title",
				templateUrl : "viewcomponents/app/app.single.html",
	            resolve: {
	            	appConnectorFactory: appConnectorFactory,
	            	dialogService: dialogService
	             }
			}).when('/viewcomponents/app-maintain/update/:appid', {
				title : "app",
				subtitle : "app.update.title",
				templateUrl : "viewcomponents/app/app.single.html",
				controller : 'appSingleCtrl',
				controllerAs : 'ctrl',
	            resolve: {
	            	appConnectorFactory: appConnectorFactory,
	            	dialogService: dialogService
	             }
			});
	}]);
})();