/**
 * Contains all functionality to create new projects and generate the CRUD operations for the domain objects.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.generator').controller('generatorCtrl', generatorCtrl);

	generatorCtrl.$inject = ['$scope', '$routeParams', 'dialogService', 'domainConnectorFactory', 'projectConnectorFactory', 'projectSetupConnectorFactory', 'generatorConnectorFactory'];
	function generatorCtrl($scope, $routeParams, dialogService, domainConnectorFactory, projectConnectorFactory, projectSetupConnectorFactory, generatorConnectorFactory) {
		var ctrl = this;

		ctrl.refresh = refresh;
		ctrl.generate = generate;
		ctrl.doProjectSetupAll = doProjectSetupAll;
		ctrl.dialog = dialogService.dialog;
		ctrl.closeDialog = closeDialog;
		init();
		
		/**
		 * Refreshs the view from database.
		 */
		function refresh() { 
			domainConnectorFactory.getDomainsByProject($routeParams.id); 
		};
		
		/**
		 * Generates CRUD-access for domain objects.
		 */
		function generate() {
			ctrl.generatorDto.domains = [];
			if (ctrl.domainAll != null) {
				ctrl.domainAll.forEach(function(domain) {
					if (domain.selected) {
						ctrl.generatorDto.domains.push(domain);
					}
				});
				generatorConnectorFactory.generate(ctrl.generatorDto);
			}
		};
		
		/**
		 * Creates a new project with the given setup from a template-project.
		 */
		function doProjectSetupAll() {
			projectSetupConnectorFactory.projectSetupAll(ctrl.generatorDto.project).then(setupSuccess, setupError);
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
			dialogService.showDialog("domain.dialog.success.title", "domain.save.success", dialogService.dialog.id.success, function(){});
		};
		
		
		/**
		 * Error message after loading the project.
		 */
		function setupError(response) {
			dialogService.showDialog("domain.dialog.error.title", response, dialogService.dialog.id.error, function(){});
		};
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		};
	};
})();