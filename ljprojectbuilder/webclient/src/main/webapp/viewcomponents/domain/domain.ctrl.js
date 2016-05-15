var domainControllers = {};
domainControllers.loadDomainController = function($rootScope, $scope, $location, domainConnectorFactory, $translate, gotoDomain) {

	$scope.domainAll = [];
	$scope.refresh = function() { domainConnectorFactory.getDomainAll($scope); };
	$scope.gotoUpdateDomain = function(id) { gotoDomain.update($location, id); };
	$scope.gotoCreateDomain = function () { gotoDomain.create($location); };
	$scope.deleteDomain = function(id) {	domainConnectorFactory.deleteDomain($scope, id);};
	$scope.setSelected = function (idSelected) { $scope.idSelected = idSelected; };
	
	init();
	function init() {
		//change title on view change
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title=next.title;
			$scope.subtitle=next.subtitle;
		});
		$scope.refresh();
	}
	
	$scope.doBack = function () {
		gotoDomain.back($location);
	};
};

domainControllers.maintainDomainController = function ($scope, $routeParams, $location, domainConnectorFactory, $translate, gotoDomain) {
	$scope.domain = {};
	init();
	
	function init() {
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title = next.title;
			$scope.mode = next.mode;
			if ($routeParams.id != undefined) {
				domainConnectorFactory.loadDomain($scope, $routeParams.id);
			}
		});
	};
	
	$scope.doMaintain = function () {
		if ($scope.mode == 'update') {
			domainConnectorFactory.updateDomain($scope, function($location) {gotoDomain.all($location);}, function($location) {gotoDomain.update($location, $scope.domain.id);});
		} else {
			domainConnectorFactory.createDomain($scope, function($location) {gotoDomain.all($location);}, function($location) {gotoDomain.create($location);});
		}
	};
	
	$scope.doBack = function () {
		gotoDomain.all($location);
	};
};