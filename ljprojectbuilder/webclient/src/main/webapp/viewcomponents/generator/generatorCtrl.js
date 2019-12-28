/**
 * Contains all functionality to create new projects and generate the CRUD operations for the domain objects.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.generator').controller('generatorCtrl', generatorCtrl);

	generatorCtrl.$inject = ['$scope', '$routeParams', 'dialogService', 'domainConnectorFactory', 'projectConnectorFactory', 'projectSetupConnectorFactory', ];
	function generatorCtrl($scope, $routeParams, dialogService, domainConnectorFactory, projectConnectorFactory, projectSetupConnectorFactory) {
		var ctrl = this;

		ctrl.projectid = 0;
		ctrl.refresh = refresh;
		ctrl.dialog = dialogService.dialog;
		ctrl.closeDialog = closeDialog;
		ctrl.projectDownload = projectDownload;
		ctrl.checkTargetRepo = checkTargetRepo;
		ctrl.createTargetRepo = createTargetRepo;
		ctrl.checkAuthentication = checkAuthentication;
		ctrl.resetAuth = resetAuth;
		init();
		
		/**
		 * Refreshs the view from database.
		 */
		function refresh() { 
			domainConnectorFactory.getDomainsByProject($routeParams.id); 
		};
		
		/**
		 * Creates a new project with the given setup from a template-project.
		 */
		function projectDownload() {
			dialogService.showDialog(null, null, "loadingdialog", function(){});
			getSelectedDomainIds();
			projectSetupConnectorFactory.projectSetup(ctrl.generatorDto).then(function(){
				document.getElementById('downloadlink').click();
				dialogService.closeDialog('loadingdialog');
			}, setupDownloadError);
			resetAuth();
		};
		
		function resetAuth() {
			ctrl.generatorDto.username="";
			ctrl.generatorDto.password="";
		}
		
		function checkAuthentication() {
			if (ctrl.generatorDto.project.template.credentialsRequired) {
				dialogService.showDialog("generator.login", null, "authenticationdialog", function(){});
			} else {
				projectDownload();
			}
		};
		
		function checkTargetRepo() {
			var targetRepoData = ctrl.targetRepoData;
			if(targetRepoData.username && targetRepoData.password && targetRepoData.baseURL) {
				projectSetupConnectorFactory.checkIfRepoServerWorks(ctrl.targetRepoData).then(function(data) {
					ctrl.repos = data;
					ctrl.targetRepoLoaded = true;
				});				
			} else {
				//TODO error message
			}

		}
		
		function createTargetRepo() {
			var targetRepoData = ctrl.targetRepoData;
			if(targetRepoData.username && targetRepoData.password && targetRepoData.baseURL) {
				projectSetupConnectorFactory.createTargetRepo(ctrl.targetRepoData).then(function(data) {
					ctrl.repos = data;

				});				
			} else {
				//TODO error message
			}
		}
		
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
					ctrl.projectid = $routeParams.id;
					domainConnectorFactory.getDomainsByProject($routeParams.id)
						.then(setDomainAll, null);
					
					projectConnectorFactory.loadProject($routeParams.id)
						.then(setGeneratorDto, null);
				}
			});
			ctrl.refresh();
		}
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setGeneratorDto(response) {
			ctrl.generatorDto.project = response;
			ctrl.projecttitle = response.title;
			ctrl.downloadlink="downloadproject?projectid=" + response.id;
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
			dialogService.showDialog("projectsetup.dialog.success.title", "projectsetup.execute.success", dialogService.dialog.id.success, function(){});
		};
		
		
		/**
		 * Error message after loading the project.
		 */
		function setupDownloadError(response) {
			dialogService.closeDialog('loadingdialog');
			dialogService.showDialog("projectsetup.dialog.error.title", response, dialogService.dialog.id.error, function(){});
			if (response == 'NOT_AUTHORIZED') {
				//is this needed?
			}
		};
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		};
	};
})();