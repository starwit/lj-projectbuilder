/** 
 * Navigation and routing for module ljprojectbuilderApp.projecttemplate.
 */
(function() {
'use strict';
   
	angular.module('ljprojectbuilderApp.adminview')
		.config(['$routeProvider', function($routeProvider) {
		  $routeProvider.when('/viewcomponents/adminview/', {
				controller : 'adminviewCtrl',
				controllerAs : 'ctrl',
				title : "adminview.title",
				subtitle : "",
				templateUrl : "viewcomponents/adminview/adminview.html"
			})
	}]);
})();