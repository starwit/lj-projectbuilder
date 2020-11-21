/** 
 * Navigation and routing for module ljprojectbuilderApp.projecttemplate.
 */
(function() {
'use strict';

	angular.module('ljprojectbuilderApp.projecttemplate').factory('gotoProjectTemplate', gotoProjectTemplate);
	
	function gotoProjectTemplate($location) {
		var factory = {};
		factory.all = function() {
	    	$location.path('/viewcomponents/projecttemplate-all/');
	    },
	    factory.update = function(id) {
	    	$location.path('/viewcomponents/projecttemplate-maintain/update/' + id + '/' + false);
	    },
	    factory.copy = function(id) {
	    	$location.path('/viewcomponents/projecttemplate-maintain/update/' + id + '/' + true);
	    },
	    factory.create = function() {
	    	$location.path('/viewcomponents/projecttemplate-maintain/create/');
	    },
	    factory.loaderror = function() {
	    	$location.path('/');
	    }
		return factory;
    };
    
	angular.module('ljprojectbuilderApp.projecttemplate')
	.config(['$routeProvider', function($routeProvider) {
		  $routeProvider.when('/viewcomponents/projecttemplate-all/', {
				controller : 'projecttemplateAllCtrl',
				controllerAs : 'ctrl',
				title : "projecttemplate.all.title",
				subtitle : "",
				templateUrl : "viewcomponents/projecttemplate/projecttemplate.all.html",
	            resolve: {
	            	projecttemplateConnectorFactory: projecttemplateConnectorFactory
	             }
			}).when('/viewcomponents/projecttemplate-maintain/create/', {
				controller : 'projecttemplateSingleCtrl',
				controllerAs : 'ctrl',
				title : "projecttemplate.create.title",
				subtitle : "projecttemplate.create.title",
				templateUrl : "viewcomponents/projecttemplate/projecttemplate.single.html",
	            resolve: {
	            	projecttemplateConnectorFactory: projecttemplateConnectorFactory,
	            	dialogService: dialogService
	             }
			}).when('/viewcomponents/projecttemplate-maintain/update/:projecttemplateid/:copy', {
				title : "projecttemplate.update.title",
				subtitle : "projecttemplate.update.title",
				templateUrl : "viewcomponents/projecttemplate/projecttemplate.single.html",
				controller : 'projecttemplateSingleCtrl',
				controllerAs : 'ctrl',
	            resolve: {
	            	projecttemplateConnectorFactory: projecttemplateConnectorFactory,
	            	dialogService: dialogService
	             }
			})
	}]);
})();