/**
 * This controller maintains a project object and belongs to the view porject.single.html.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.project').controller('projectSingleCtrl', projectSingleCtrl);
	
	projectSingleCtrl.$inject = ['$scope', '$routeParams', 'projectConnectorFactory', 'gotoProject'];
	function projectSingleCtrl($scope, $routeParams, projectConnectorFactory, gotoProject) {
		var ctrl = this;
		
		ctrl.doMaintain = doMaintain;
		ctrl.gotoProject = gotoProject;
		init();

		/**
		 * Standard function to edit the project configuration.
		 */
		function doMaintain() {
			ctrl.dialog = {};
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
				if ($routeParams.id != undefined && ctrl.project.id == null) {
					projectConnectorFactory.loadProject($routeParams.id).then(setProject, null);
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
		 * Display dialog after saving project configuration.
		 */
		function showDialog(dialogtitle, dialogtext, dialogid) {
			ctrl.dialog.title = dialogtitle;
			ctrl.dialog.text = dialogtext;
			document.getElementById(dialogid).style.display = 'block';
		};
		
		/**
		 * Success message after saving.
		 */
		function saveSuccess(response) {
			setProject(response);
			showDialog("project.dialog.success.title", "project.save.success", 'successdialog');
		};
		
		/**
		 * Error message after saving.
		 */
		function saveError(response) {
			showDialog("project.dialog.error.title", "project.save.error", 'errordialog');
		};
	};
})();