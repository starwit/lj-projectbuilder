(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.generator').controller('generatorCtrl', generatorCtrl);

	function generatorCtrl($scope, $routeParams, domainConnectorFactory, projectConnectorFactory, projectSetupConnectorFactory, generatorConnectorFactory) {
		var ctrl = this;
		ctrl.domainAll = [];
		ctrl.generatorDto = {};
		ctrl.refresh = function() { domainConnectorFactory.getDomainsByProject($routeParams.id); };
	
		init();
		function init() {
			//change title on view change
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.id != undefined) {
					ctrl.projectid = $routeParams.id;
					domainConnectorFactory.getDomainsByProject($routeParams.id)
						.then(function(response) {
							ctrl.domainAll = response;
						}, null);
					
					projectConnectorFactory.loadProject($routeParams.id)
					.then(	
						function(response) { 
							ctrl.generatorDto.project = response; 
						}, null
					);
				}
			});
			ctrl.refresh();
		}
		
		ctrl.generate = function () {
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
		
		ctrl.doProjectSetupAll = function () {
			projectSetupConnectorFactory.projectSetupAll(ctrl.generatorDto.project);
		};
	};
})();