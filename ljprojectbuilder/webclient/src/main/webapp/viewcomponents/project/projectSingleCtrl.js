(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.project').controller('projectSingleCtrl', projectSingleCtrl);
	
	function projectSingleCtrl($rootScope, $scope, $routeParams, $location, projectConnectorFactory, $translate, gotoProject) {
		
		init();
		$scope.project = {};
		$scope.gotoProject = gotoProject;

		$scope.doMaintain = function () {
			$scope.dialog = {};
			if ($scope.project != null && $scope.project.id != null) {
				projectConnectorFactory.updateProject($scope.project).then(
						saveSuccess, 
						saveError
				);
			} else {
				projectConnectorFactory.createProject($scope.project).then(
						saveSuccess, 
						saveError
				);
			}
		};
		
		function init() {
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.id != undefined && $scope.project.id == null) {
					projectConnectorFactory.loadProject($routeParams.id).then(setProject, null);
				}
			});
		};
		
		function setProject(response) {
			$scope.project = response;
		};
		
		function showDialog(dialogtitle, dialogtext, dialogid) {
			$scope.dialog.title = dialogtitle;
			$scope.dialog.text = dialogtext;
			document.getElementById(dialogid).style.display = 'block';
		};
		
		function saveSuccess(response) {
			setProject(response);
			showDialog("project.dialog.success.title", "project.save.success", 'successdialog');
		};
		
		function saveError(response) {
			showDialog("project.dialog.error.title", "project.save.error", 'errordialog');
		};
	};
})();