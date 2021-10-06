/** 
 * Navigation and routing for module ljprojectbuilderApp.apptemplate.
 */
(function() {
'use strict';

	angular.module('ljprojectbuilderApp.apptemplate').factory('gotoAppTemplate', gotoAppTemplate);
	
	function gotoAppTemplate($location) {
		var factory = {};
		factory.all = function() {
	    	$location.path('/viewcomponents/apptemplate-all/');
	    },
	    factory.update = function(id) {
	    	$location.path('/viewcomponents/apptemplate-maintain/update/' + id + '/' + false);
	    },
	    factory.copy = function(id) {
	    	$location.path('/viewcomponents/apptemplate-maintain/update/' + id + '/' + true);
	    },
	    factory.create = function() {
	    	$location.path('/viewcomponents/apptemplate-maintain/create/');
	    },
	    factory.loaderror = function() {
	    	$location.path('/');
	    }
		return factory;
    };
    
	angular.module('ljprojectbuilderApp.apptemplate')
	.config(['$routeProvider', function($routeProvider) {
		  $routeProvider.when('/viewcomponents/apptemplate-all/', {
				controller : 'apptemplateAllCtrl',
				controllerAs : 'ctrl',
				title : "apptemplate.all.title",
				subtitle : "",
				templateUrl : "viewcomponents/apptemplate/apptemplate.all.html",
	            resolve: {
	            	apptemplateConnectorFactory: apptemplateConnectorFactory
	             }
			}).when('/viewcomponents/apptemplate-maintain/create/', {
				controller : 'apptemplateSingleCtrl',
				controllerAs : 'ctrl',
				title : "apptemplate.create.title",
				subtitle : "apptemplate.create.title",
				templateUrl : "viewcomponents/apptemplate/apptemplate.single.html",
	            resolve: {
	            	apptemplateConnectorFactory: apptemplateConnectorFactory,
	            	dialogService: dialogService
	             }
			}).when('/viewcomponents/apptemplate-maintain/update/:apptemplateid/:copy', {
				title : "apptemplate.update.title",
				subtitle : "apptemplate.update.title",
				templateUrl : "viewcomponents/apptemplate/apptemplate.single.html",
				controller : 'apptemplateSingleCtrl',
				controllerAs : 'ctrl',
	            resolve: {
	            	apptemplateConnectorFactory: apptemplateConnectorFactory,
	            	dialogService: dialogService
	             }
			})
	}]);
})();