(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.project').controller('projectSingleCtrl', projectSingleCtrl);
	
	function projectSingleCtrl($rootScope, $scope, $routeParams, $location, projectConnectorFactory, $translate, gotoProject) {
		
		init();
		$scope.project = {};
		$scope.gotoAll = function() { gotoProject.all($location); };
		$scope.gotoUpdateProject = function() { gotoUpdateProject.update($location, $scope.project.id); }
		$scope.gotoCreateProject = function () { gotoProject.create($location); };

		function init() {
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.id != undefined) {
					projectConnectorFactory.loadProject($routeParams.id).then(setProject, null);
				}
			});
		};
		
		function showDialog(dialogtitle, dialogtext, dialogid) {
			$rootScope.dialog.title = dialogtitle;
			$rootScope.dialog.text = dialogtext;
			document.getElementById(dialogid).style.display = 'block';
		};
		
		function showGenerationResultDialog(response) {
			if (response.responseCode == 'OK' || response.responseCode == 'EMPTY') {
				showDialog("project.dialog.success.title", response.message, 'successdialog');
			} else {
				showDialog("project.dialog.error.title", response.message, 'errordialog');
			}
		};
		
		function setProject(response) {
			$scope.project = response;
		};
		
		function saveSuccess(response) {
			$scope.project = response;
			showDialog("project.dialog.success.title", "project.save.success", 'successdialog');
		};
		
		function saveError(response) {
			$rootScope.dialog.title = "project.dialog.error.title";
			showDialog("project.dialog.error.title", "project.save.error", 'errordialog');
		};
		
		$scope.doMaintain = function () {
			$rootScope.dialog = {};
			if ($scope.mode == 'update') {
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
		
		$scope.doProjectSetup = function () {
			$rootScope.dialog = {};
			projectSetupConnectorFactory.projectSetup($scope.project)
			.then(showGenerationResultDialog);
		};
		
		$scope.doRename = function () {
			$rootScope.dialog = {};
			projectSetupConnectorFactory.rename($scope.project)
			.then(showGenerationResultDialog);
		};
		
		$scope.doRenamePackage = function () {
			$rootScope.dialog = {};
			projectSetupConnectorFactory.renamePackage($scope.project)
			.then(showGenerationResultDialog);
		};
		
		$scope.doBack = function () {
			gotoProject.all($location);
		};
	};
})();