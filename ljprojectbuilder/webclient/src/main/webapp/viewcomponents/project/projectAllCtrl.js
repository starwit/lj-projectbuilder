(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.project').controller('projectAllCtrl', projectAllCtrl);
	
	function projectAllCtrl($rootScope, $scope, $location, projectConnectorFactory, $translate, gotoProject) {
		init();
		
		function init() {
			$scope.projectAll = [];
			projectConnectorFactory.getProjectAll().then(	setProjectAll, null);
			
			$scope.gotoProject = gotoProject;
			$scope.deleteProject = function(id) {projectConnectorFactory.deleteProject(id).then(projectConnectorFactory.getProjectAll(), null)};
			$scope.setSelected = function (idSelected) { $scope.idSelected = idSelected; };
		}
		
		function setProjectAll(response) {
			$scope.projectAll = response;		
		}
	};
	
})();