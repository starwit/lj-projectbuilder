/**
 * This controller maintains a app object and belongs to the view porject.single.html.
 */
(function() {
	'use strict';
	angular.module('ljappbuilderApp.app').controller('appSingleCtrl', appSingleCtrl);
	
	appSingleCtrl.$inject = ['$scope', '$routeParams', 'appConnectorFactory', 'dialogService', 'gotoApp'];
	function appSingleCtrl($scope, $routeParams, appConnectorFactory, dialogService, gotoApp) {
		var ctrl = this;
		
		ctrl.doMaintain = doMaintain;
		ctrl.doMaintainDomain = doMaintainDomain;
		ctrl.doMaintainGenerate = doMaintainGenerate;
		ctrl.gotoApp = gotoApp;
		ctrl.closeDialog = closeDialog;
		ctrl.closeDialogWithErrors = dialogService.closeDialogWithErrors;
		ctrl.resetAndContinue = dialogService.resetAndContinue;		
		ctrl.dialog = dialogService.dialog;
		init();

		/**
		 * Standard function to edit the app configuration.
		 */
		function doMaintain() {
			if (ctrl.form.$dirty) {
				doMaintainThenGoto(gotoApp.all);
			} else {
				gotoApp.all();
			}
		}
		
		/**
		 * Standard function to edit the app configuration.
		 */
		function doMaintainDomain() {
			if (ctrl.form.$dirty || $routeParams.appid == null) {
				doMaintainThenGoto(gotoAppDomain);
			} else {
				gotoAppDomain();
			}
		}
		
		/**
		 * Standard function to edit the app configuration.
		 */
		function doMaintainGenerate() {
			if (ctrl.form.$dirty) {
				doMaintainThenGoto(gotoAppGenerate);
			} else {
				gotoAppGenerate();
			}
		}

		function doMaintainThenGoto(gotoDestination) {
			var saveFunction = isUpdate() ? appConnectorFactory.updateApp : appConnectorFactory.createApp;
			saveFunction(ctrl.app).then(saveSuccessCallbackThatGoesTo(gotoDestination), saveError(gotoDestination));
		}

		function isUpdate() {
			return ctrl.app != null && ctrl.app.id != null;
		}
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.app = {};
			ctrl.templateAll = [];
			appConnectorFactory.getTemplateAll().then(setTemplateAll, null);
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.appid != undefined && $routeParams.appid !== ctrl.app.id) {
					ctrl.app.id = $routeParams.appid;
					ctrl.appid = ctrl.app.id;
					appConnectorFactory.loadApp(ctrl.app.id).then(setApp, loadError);
				}
			});
		}
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setApp(response) {
			ctrl.app = response;
			ctrl.apptitle = response.title;
		}
		
		function setTemplateAll(response) {
			ctrl.templateAll = response;
			if ($routeParams.appid == null) {
				ctrl.app.template = response[0];
			}
		}
		
		/**
		 * Success message after saving.
		 */
		function saveSuccessCallbackThatGoesTo(gotoDestination) {
			return function (response) {
				setApp(response);
				dialogService.showDialog("app.dialog.success.title", "app.save.success", dialogService.dialog.id.success, gotoDestination);
			}
		}
		function gotoAppDomain() {
			gotoApp.domain(ctrl.app.id);
		}
		
		function gotoAppGenerate() {
			gotoApp.generate(ctrl.app.id);
		}
		
		/**
		 * Error message after saving.
		 */
		function saveError(gotoDestination) {
			return function (response) {
				if (response.responseCode == 'NOT_VALID') {
					dialogService.showValidationDialog("app.dialog.error.title", response.message, response.validationErrors, dialogService.dialog.id.error, gotoDestination);
				} else {
					dialogService.showDialog("app.dialog.error.title", "apptemplate.save.error", dialogService.dialog.id.error, function(){});
				}
			}
		}
		
		/**
		 * Error message after loading the app.
		 */
		function loadError(response) {
			dialogService.showDialog("app.dialog.error.title", "app.load.error", dialogService.dialog.id.error, gotoApp.all);
		}
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		}
	}
})();