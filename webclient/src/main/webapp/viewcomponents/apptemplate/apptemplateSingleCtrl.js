/**
 * This controller maintains a app object and belongs to the view porject.single.html.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.apptemplate').controller('apptemplateSingleCtrl', apptemplateSingleCtrl);
	
	apptemplateSingleCtrl.$inject = ['$scope', '$routeParams', 'apptemplateConnectorFactory', 'dialogService', 'gotoAppTemplate'];
	function apptemplateSingleCtrl($scope, $routeParams, apptemplateConnectorFactory, dialogService, gotoAppTemplate) {
		var ctrl = this;
		
		ctrl.doMaintain = doMaintain;
		ctrl.gotoAppTemplate = gotoAppTemplate;
		ctrl.doGotoAppTemplateAll = doGotoAppTemplateAll;
		ctrl.closeDialog = closeDialog;
		ctrl.dialog = dialogService.dialog;
		ctrl.addTemplateFile = addTemplateFile;
		ctrl.removeTemplateFile = removeTemplateFile;
		ctrl.closeDialogWithErrors = dialogService.closeDialogWithErrors;
		ctrl.resetAndContinue = dialogService.resetAndContinue;
		ctrl.templateTypes = [	"GLOBAL","DOMAIN","ADDITIONAL_CONTENT"];
		init();
		
		function doGotoAppTemplateAll() {
			gotoAppTemplate.all();
		}

		/**
		 * Standard function to edit the apptemplate configuration.
		 */
		function doMaintain() {
			if (ctrl.form.$dirty || !isUpdate()) {
				doMaintainThenGoto(gotoAppTemplate.all);
			} else if (ctrl.form.$invalid) {
				return;
			} else {
				gotoAppTemplate.all();
			}
		}
		
		function doMaintainThenGoto(gotoDestination) {
			var saveFunction = isUpdate() ? apptemplateConnectorFactory.updateAppTemplate : apptemplateConnectorFactory.createAppTemplate;
			saveFunction(ctrl.apptemplate).then(saveSuccessCallbackThatGoesTo(gotoDestination), saveError(gotoDestination));
		}

		function isUpdate() {
			return ctrl.apptemplate != null && ctrl.apptemplate.id != null;
		}
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.apptemplate = {};
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.apptemplateid != undefined && $routeParams.apptemplateid !== ctrl.apptemplate.id) {
					ctrl.apptemplate.id = $routeParams.apptemplateid;
					ctrl.apptemplateid = ctrl.apptemplate.id;
					ctrl.copy = $routeParams.copy;
					apptemplateConnectorFactory.loadAppTemplate(ctrl.apptemplate.id).then(setAppTemplateInit, loadError);
				}
				if ($routeParams.apptemplateid == null) {
					ctrl.apptemplate = {};
					ctrl.apptemplate.location = "https://gitlab.com/witchpou/lirejarp";
					ctrl.apptemplate.title = "lirejarp";
					ctrl.apptemplate.packagePrefix = "starwit";
				}
				getCategories();
			});
		}
		
		/**
		 * refreshs the view from database.
		 */
		function getCategories() { 
			apptemplateConnectorFactory.getCategoryAll().then(setCategoryAll, loadError); 
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
		function setAppTemplateInit(response) {
			ctrl.apptemplate = response;
			ctrl.apptemplatetitle = response.title;
			if (ctrl.copy == "true") {
				ctrl.apptemplate.id = null;
				var templatefiles = [];
		        angular.forEach(ctrl.apptemplate.templateFiles, function (value, key) {
		        	value.id = null;
		        	templatefiles.push(value);
		        });
		        ctrl.apptemplate.templateFiles = templatefiles;
	        	ctrl.form.$dirty = true;
			}
		}
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setAppTemplate(response) {
			ctrl.apptemplate = response;
			ctrl.apptemplatetitle = response.title;
		}
		
		/**
		 * Success message after saving.
		 */
		function saveSuccessCallbackThatGoesTo(gotoDestination) {
			return function (response) {
				setAppTemplate(response);
				dialogService.showDialog("apptemplate.dialog.success.title", "apptemplate.save.success", dialogService.dialog.id.success, gotoDestination);
			}
		}

		/**
		 * Error message after saving.
		 */
		function saveError(gotoDestination) {
			return function (response) {
				if (response.responseCode == 'NOT_VALID') {
					dialogService.showValidationDialog("apptemplate.dialog.error.title", response.message, response.validationErrors, dialogService.dialog.id.error, gotoDestination);
				} else {
					dialogService.showDialog("apptemplate.dialog.error.title", "apptemplate.save.error", dialogService.dialog.id.error, function(){});
				}
			}
		}
		
		/**
		 * Add an templatefile to a apptemplate.
		 */
		function addTemplateFile() {
			if (ctrl.apptemplate.templateFiles == undefined) {
				ctrl.apptemplate.templateFiles = [];
			}
			var templatefile = {};
			templatefile.category = ctrl.categoryAll[0];
			templatefile.type = ctrl.templateTypes[1];
			templatefile.fileNameSuffix = "Entity.java"
			templatefile.templatePath = "${apphome}/${app.targetPath}/generator-templates/entity/entity.ftl";
			templatefile.targetPath = "${apphome}/${app.targetPath}/${app.title}/persistence/src/main/java/de/${app.packagePrefix?lower_case}/${app.title?lower_case}/entity/";	
			ctrl.apptemplate.templateFiles.unshift(templatefile);
			ctrl.form.$dirty = true;
		};
		
		/**
		 * Remove an templatefile from a apptemplate.
		 */
		function removeTemplateFile($index) {
			if (ctrl.apptemplate.templateFiles != undefined && $index > -1) {
				ctrl.apptemplate.templateFiles.splice($index, 1);
				ctrl.form.$dirty = true;
			}
		};
		
		/**
		 * Error message after loading the apptemplate.
		 */
		function loadError(response) {
			dialogService.showDialog("apptemplate.dialog.error.title", "apptemplate.load.error", dialogService.dialog.id.error, gotoAppTemplate.all);
		}
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		}
	}
})();