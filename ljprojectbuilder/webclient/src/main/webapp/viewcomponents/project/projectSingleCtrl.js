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
		ctrl.doMaintainDomain = doMaintainDomain;
		ctrl.doMaintainGenerate = doMaintainGenerate;
		ctrl.gotoProject = gotoProject;
		ctrl.closeDialog = closeDialog;
		ctrl.dialog = dialogService.dialog;
		init();

		/**
		 * Standard function to edit the project configuration.
		 */
		function doMaintain() {
			if (ctrl.form.$dirty) {
				doMaintainThenGoto(gotoProject.all);
			} else {
				gotoProject.all();
			}
		}
		
		/**
		 * Standard function to edit the project configuration.
		 */
		function doMaintainDomain() {
			if (ctrl.form.$dirty || $routeParams.projectid == null) {
				doMaintainThenGoto(gotoProjectDomain);
			} else {
				gotoProjectDomain();
			}
		}
		
		/**
		 * Standard function to edit the project configuration.
		 */
		function doMaintainGenerate() {
			if (ctrl.form.$dirty) {
				doMaintainThenGoto(gotoProjectGenerate);
			} else {
				gotoProjectGenerate();
			}
		}

		function doMaintainThenGoto(gotoDestination) {
			var saveFunction = isUpdate() ? projectConnectorFactory.updateProject : projectConnectorFactory.createProject;
			saveFunction(ctrl.project).then(saveSuccessCallbackThatGoesTo(gotoDestination), saveError);
		}

		function isUpdate() {
			return ctrl.project != null && ctrl.project.id != null;
		}
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.project = {};
			ctrl.templateAll = [];
			projectConnectorFactory.getTemplateAll().then(setTemplateAll, null);
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.projectid != undefined && $routeParams.projectid !== ctrl.project.id) {
					ctrl.project.id = $routeParams.projectid;
					ctrl.projectid = ctrl.project.id;
					projectConnectorFactory.loadProject(ctrl.project.id).then(setProject, loadError);
				}
			});
		}
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setProject(response) {
			ctrl.project = response;
			ctrl.projecttitle = response.title;
		}
		
		function setTemplateAll(response) {
			ctrl.templateAll = response;
			if ($routeParams.projectid == null) {
				ctrl.project.template = response[0];
			}
		}
		
		/**
		 * Success message after saving.
		 */
		function saveSuccessCallbackThatGoesTo(gotoDestination) {
			return function (response) {
				setProject(response);
				dialogService.showDialog("project.dialog.success.title", "project.save.success", dialogService.dialog.id.success, gotoDestination);
			}
		}
		function gotoProjectDomain() {
			gotoProject.domain(ctrl.project.id);
		}
		
		function gotoProjectGenerate() {
			gotoProject.generate(ctrl.project.id);
		}
		
		/**
		 * Error message after saving.
		 */
		function saveError(response) {
			dialogService.showDialog("project.dialog.error.title", "project.save.error", dialogService.dialog.id.error, function(){});
		}
		
		/**
		 * Error message after loading the project.
		 */
		function loadError(response) {
			dialogService.showDialog("project.dialog.error.title", "project.load.error", dialogService.dialog.id.error, gotoProject.all);
		}
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		}
	}
})();