/**
 * This controller maintains a project object and belongs to the view porject.single.html.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.project').controller('projectSingleCtrl', projectSingleCtrl);
	
	projectSingleCtrl.$inject = ['$scope', '$routeParams', 'projectConnectorFactory', 'dialogService', 'gotoProject'];
	function projectSingleCtrl($scope, $routeParams, projectConnectorFactory, dialogService, gotoProject) {
		var ctrl = this;
		
		ctrl.doMaintain = doMaintain;
		ctrl.gotoProject = gotoProject;
		ctrl.closeDialog = closeDialog;
		ctrl.dialog = dialogService.dialog;
		init();

		/**
		 * Standard function to edit the project configuration.
		 */
		function doMaintain() {
			if (ctrl.project != null && ctrl.project.id != null) {
				projectConnectorFactory.updateProject(ctrl.project).then(
						saveSuccess, 
						saveError
				);
			} else {
				projectConnectorFactory.createProject(ctrl.project).then(
						saveSuccess, 
						saveError
				);
			}
		};
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.project = {};
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.projectid != undefined && $routeParams.projectid !== ctrl.project.id) {
					ctrl.project.id = $routeParams.projectid;
					projectConnectorFactory.loadProject(ctrl.project.id).then(setProject, loadError);
				}
			});
		};
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setProject(response) {
			ctrl.project = response;
		};
		
		/**
		 * Success message after saving.
		 */
		function saveSuccess(response) {
			setProject(response);
			dialogService.showDialog("project.dialog.success.title", "project.save.success", dialogService.dialog.id.success, gotoProject.all);
		};
		
		/**
		 * Error message after saving.
		 */
		function saveError(response) {
			dialogService.showDialog("project.dialog.error.title", "project.save.error", dialogService.dialog.id.error, function(){});
		};
		
		/**
		 * Error message after loading the project.
		 */
		function loadError(response) {
			dialogService.showDialog("project.dialog.error.title", "project.load.error", dialogService.dialog.id.error, gotoProject.all);
		};
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		}
	};
})();