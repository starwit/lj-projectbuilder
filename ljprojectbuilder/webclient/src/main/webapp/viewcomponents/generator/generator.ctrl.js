var generatorControllers = {};

generatorControllers.generatorController = function($scope, $routeParams, $location, domainConnectorFactory, projectConnectorFactory, projectSetupConnectorFactory, generatorConnectorFactory, gotoProject) {

	$scope.domainAll = [];
	$scope.generatorDto = {};
	$scope.refresh = function() { domainConnectorFactory.getDomainsByProject($routeParams.id); };

	
	init();
	function init() {
		//change title on view change
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title=next.title;
			$scope.subtitle=next.subtitle;
			if ($routeParams.id != undefined) {
				$scope.projectid = $routeParams.id;
				domainConnectorFactory.getDomainsByProject($routeParams.id)
					.then(function(response) {
						$scope.domainAll = response;
					}, null);
				
				projectConnectorFactory.loadProject($scope, $routeParams.id)
				.then(	
					function(response) { $scope.generatorDto.project = response; }
				);
			}
		});
		$scope.refresh();
	}
	
	$scope.generate = function () {
		$scope.generatorDto.domains = [];
		if ($scope.domainAll != null) {
			$scope.domainAll.forEach(function(domain) {
				$scope.generatorDto.domains.push(domain);
			});
			generatorConnectorFactory.generate($scope.generatorDto);
		}
	};
	
	
	$scope.doProjectSetupAll = function () {
		$rootScope.dialog = {};
		projectSetupConnectorFactory.projectSetupAll($scope.generatorDto.project);
	};
};