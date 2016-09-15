(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.project').controller('projectAllCtrl', projectAllCtrl);
	
	function projectAllCtrl($rootScope, $scope, $location, projectConnectorFactory, $translate, gotoProject) {
	
		$scope.projectAll = [];
		$scope.refresh = function() { projectConnectorFactory.getProjectAll().then(	setProjectAll, null); };
		$scope.gotoUpdateProject = function(id) { gotoProject.update($location, id); };
		$scope.gotoCreateProject = function () { gotoProject.create($location); };
		$scope.deleteProject = function(id) {projectConnectorFactory.deleteProject(id).then(projectConnectorFactory.getProjectAll(), null)};
		$scope.setSelected = function (idSelected) { $scope.idSelected = idSelected; };
		
		init();
		function init() {
			//change title on view change
			$scope.refresh();
		}
		
		function setProjectAll(response) {
			$scope.projectAll = response;		
		}
		
		$scope.doBack = function () {
			gotoProject.back($location);
		};
	};
	
})();