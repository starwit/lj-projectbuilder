/**
 * Contains all functionality to create new apps and generate the CRUD operations for the domain objects.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.generator').controller('generatorCtrl', generatorCtrl);

	generatorCtrl.$inject = ['$scope', '$routeParams', 'dialogService', 'domainConnectorFactory', 'appConnectorFactory', 'appSetupConnectorFactory', 'gotoGenAppTemplate',];
	function generatorCtrl($scope, $routeParams, dialogService, domainConnectorFactory, appConnectorFactory, appSetupConnectorFactory, gotoGenAppTemplate) {
		var ctrl = this;

		ctrl.appid = 0;
		ctrl.refresh = refresh;
		ctrl.gotoGenAppTemplate = gotoGenAppTemplate;
		ctrl.dialog = dialogService.dialog;
		ctrl.closeDialog = closeDialog;
		ctrl.appDownload = appDownload;
		ctrl.checkAuthentication = checkAuthentication;
		ctrl.resetAuth = resetAuth;
		ctrl.closeDialogWithErrors = dialogService.closeDialogWithErrors;
		ctrl.resetAndContinue = dialogService.resetAndContinue;
		init();
		
		/**
		 * Refreshs the view from database.
		 */
		function refresh() { 
			domainConnectorFactory.getDomainsByApp($routeParams.id); 
		};
		
		/**
		 * Creates a new app with the given setup from a template-app.
		 */
		function appDownload() {
			ctrl.generatorDto.user = ctrl.generatorDto.username; 
			ctrl.generatorDto.pass = ctrl.generatorDto.password;
			dialogService.showDialog(null, null, "loadingdialog", function(){});
			getSelectedDomainIds();
			
			appSetupConnectorFactory.appSetup(ctrl.generatorDto).then(function(){
				document.getElementById('downloadlink').click();
				dialogService.closeDialog('loadingdialog');
			}, setupDownloadError(gotoTemplate));
			resetAuth();
		};
		
		function resetAuth() {
			ctrl.generatorDto.username="";
			ctrl.generatorDto.password="";
		};
		
		function gotoTemplate() {
			gotoGenAppTemplate.update(ctrl.generatorDto.app.template.id);
		}
		
		function checkAuthentication() {
			if (ctrl.generatorDto.app.template.credentialsRequired) {
				dialogService.showDialog("generator.login", null, "authenticationdialog", function(){});
			} else {
				appDownload();
			}
		};
		
		function getSelectedDomainIds() {
			ctrl.generatorDto.selectedDomains = ctrl.domainAll;
		}
		
		function init() {
			ctrl.domainAll = [];
			ctrl.generatorDto = {};
			ctrl.targetRepoData = {};
			ctrl.repos = {};
			ctrl.targetRepoLoaded = false;
			ctrl.resetAuth();
			
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.id != undefined) {
					ctrl.appid = $routeParams.id;
					domainConnectorFactory.getDomainsByApp($routeParams.id)
						.then(setDomainAll, null);
					
					appConnectorFactory.loadApp($routeParams.id)
						.then(setGeneratorDto, null);
				}
			});
			ctrl.refresh();
		}
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setGeneratorDto(response) {
			ctrl.generatorDto.app = response;
			ctrl.apptitle = response.title;
			ctrl.downloadlink="downloadapp/" + response.id;
		}
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setDomainAll(response) {
			ctrl.domainAll = response;
		}
		
		/**
		 * Success message after saving.
		 */
		function setupSuccess(response) {
			dialogService.showDialog("appsetup.dialog.success.title", "appsetup.execute.success", dialogService.dialog.id.success, function(){});
		};
		
		
		/**
		 * Error message after loading the app.
		 */
		function setupDownloadError(gotoDestination) {
			return function (response) {
				dialogService.closeDialog('loadingdialog');
				if (response == 'NOT_AUTHORIZED') {
					dialogService.showDialog("appsetup.dialog.error.title", response, dialogService.dialog.id.error, function(){});
				} else if (response == 'NOT_VALID') {
					dialogService.showGeneratorValidationDialog("apptemplate.dialog.error.title", response.message, response.validationErrors, dialogService.dialog.id.error, gotoDestination);
				} else {
					dialogService.showGeneratorValidationDialog("apptemplate.dialog.error.title", response, null, dialogService.dialog.id.error, gotoDestination);
				}
			}
		};
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		};
	};
})();