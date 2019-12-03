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
		ctrl.addCodeTemplate = addCodeTemplate;
		ctrl.removeCodeTemplate = removeCodeTemplate;
		ctrl.templateTypes = [	"GLOBAL","DOMAIN","ADDITIONAL_CONTENT"];
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
			} else if (ctrl.form.$invalid) {
				return;
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
					ctrl.copy = $routeParams.copy;
					projecttemplateConnectorFactory.loadProjectTemplate(ctrl.projecttemplate.id).then(setProjectTemplateInit, loadError);
				}
				if ($routeParams.projecttemplateid == null) {
					ctrl.projecttemplate = {};
				}
				getCategories();
			});
		}
		
		/**
		 * refreshs the view from database.
		 */
		function getCategories() { 
			projecttemplateConnectorFactory.getCategoryAll().then(setCategoryAll, loadError); 
		};
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setCategoryAll(response) {
			ctrl.categoryAll = response;		
		}
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setProjectTemplateInit(response) {
			ctrl.projecttemplate = response;
			ctrl.projecttemplatetitle = response.title;
			if (ctrl.copy == "true") {
				ctrl.projecttemplate.id = null;
				var codetemplates = [];
		        angular.forEach(ctrl.projecttemplate.codeTemplates, function (value, key) {
		        	value.id = null;
		        	codetemplates.push(value);
		        });
		        ctrl.projecttemplate.codeTemplates = codetemplates;
	        	ctrl.form.$dirty = true;
			}
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
				dialogService.showDialog("projecttemplate.dialog.success.title", "projecttemplate.save.success", dialogService.dialog.id.success, gotoDestination);
			}
		}
		
		/**
		 * Error message after saving.
		 */
		function saveError(response) {
			if (response.responseCode == 'NOT_VALID') {
				dialogService.showValidationDialog("projecttemplate.dialog.error.title", response.message, response.validationErrors, dialogService.dialog.id.error, function(){});
			} else {
				dialogService.showDialog("projecttemplate.dialog.error.title", "projecttemplate.save.error", dialogService.dialog.id.error, function(){});
			}
		}
		
		/**
		 * Add an attribute to a domain.
		 */
		function addCodeTemplate() {
			if (ctrl.projecttemplate.codeTemplates == undefined) {
				ctrl.projecttemplate.codeTemplates = [];
			}
			var codetemplate = {};
			codetemplate.category = {};
			codetemplate.category.name = "ENTITY";
			ctrl.projecttemplate.codeTemplates.unshift(codetemplate);
			ctrl.form.$dirty = true;
		};
		
		/**
		 * Remove an attribute to a domain.
		 */
		function removeCodeTemplate($index) {
			if (ctrl.projecttemplate.codeTemplates != undefined && $index > -1) {
				ctrl.projecttemplate.codeTemplates.splice($index, 1);
				ctrl.form.$dirty = true;
			}
		};
		
		/**
		 * Error message after loading the project.
		 */
		function loadError(response) {
			dialogService.showDialog("projecttemplate.dialog.error.title", "projecttemplate.load.error", dialogService.dialog.id.error, gotoProjectTemplate.all);
		}
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		}
	}
})();