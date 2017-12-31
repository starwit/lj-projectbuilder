/**
 * This controller maintains a project object and belongs to the view porject.single.html.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.projecttemplate').controller('projecttemplateSingleCtrl', projecttemplateSingleCtrl);
	
	projecttemplateSingleCtrl.$inject = ['$scope', '$routeParams', 'projecttemplateConnectorFactory', 'dialogService', 'gotoProjectTemplate'];
	function projecttemplateSingleCtrl($scope, $routeParams, projecttemplateConnectorFactory, dialogService, gotoProjectTemplate) {
		var ctrl = this;
		
		ctrl.doMaintain = doMaintain;
		ctrl.gotoProjectTemplate = gotoProjectTemplate;
		ctrl.doGotoProjectTemplateAll = doGotoProjectTemplateAll;
		ctrl.closeDialog = closeDialog;
		ctrl.dialog = dialogService.dialog;
		init();
		
		function doGotoProjectTemplateAll() {
			gotoProjectTemplate.all();
		}

		/**
		 * Standard function to edit the projecttemplate configuration.
		 */
		function doMaintain() {
			if (ctrl.form.$dirty) {
				doMaintainThenGoto(gotoProjectTemplate.all);
			} else {
				gotoProjectTemplate.all();
			}
		}
		
		function doMaintainThenGoto(gotoDestination) {
			var saveFunction = isUpdate() ? projecttemplateConnectorFactory.updateProjectTemplate : projecttemplateConnectorFactory.createProjectTemplate;
			saveFunction(ctrl.projecttemplate).then(saveSuccessCallbackThatGoesTo(gotoDestination), saveError);
		}

		function isUpdate() {
			return ctrl.projecttemplate != null && ctrl.projecttemplate.id != null;
		}
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.projecttemplate = {};
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.projecttemplateid != undefined && $routeParams.projecttemplateid !== ctrl.projecttemplate.id) {
					ctrl.projecttemplate.id = $routeParams.projecttemplateid;
					ctrl.projecttemplateid = ctrl.projecttemplate.id;
					projecttemplateConnectorFactory.loadProjectTemplate(ctrl.projecttemplate.id).then(setProjectTemplate, loadError);
				}
				if ($routeParams.projecttemplateid == null) {
					ctrl.projecttemplate = {};
				}
			});
		}
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setProjectTemplate(response) {
			ctrl.projecttemplate = response;
			ctrl.projecttemplatetitle = response.title;
		}
		
		/**
		 * Success message after saving.
		 */
		function saveSuccessCallbackThatGoesTo(gotoDestination) {
			return function (response) {
				setProjectTemplate(response);
				dialogService.showDialog("project.dialog.success.title", "project.save.success", dialogService.dialog.id.success, gotoDestination);
			}
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
			dialogService.showDialog("project.dialog.error.title", "project.load.error", dialogService.dialog.id.error, gotoProjectTemplate.all);
		}
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		}
	}
})();