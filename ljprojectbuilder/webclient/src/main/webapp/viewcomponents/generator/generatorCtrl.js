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
			ctrl.generatorDto.domains = ctrl.domainAll;
			projectSetupConnectorFactory.projectSetup(ctrl.generatorDto).then(function(){
				document.getElementById('downloadlink').click();
				ctrl.closeDialog('loadingdialog');
			}, setupError);
		};
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.domainAll = [];
			ctrl.generatorDto = {};
			
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.id != undefined) {
					ctrl.projectid = $routeParams.id;
					domainConnectorFactory.getDomainsByProject($routeParams.id)
						.then(setDomainAll, null);
					
					projectConnectorFactory.loadProject($routeParams.id)
						.then(	setGeneratorDto, null);
				}
			});
			ctrl.refresh();
		}
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setGeneratorDto(response) {
			ctrl.generatorDto.project = response;
			ctrl.generatorDto.generateEntity = true;
			ctrl.generatorDto.generateService = true;
			ctrl.generatorDto.generateRest = true;
			ctrl.generatorDto.generateFrontend = true;
			
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
		function setupError(response) {
			dialogService.showDialog("projectsetup.dialog.error.title", response, dialogService.dialog.id.error, function(){});
		};
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		};
	};
})();