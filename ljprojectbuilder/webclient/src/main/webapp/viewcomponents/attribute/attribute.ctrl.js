var attributeControllers = {};
attributeControllers.loadAttributeController = function($rootScope, $scope, $location, attributeConnectorFactory, $translate, gotoAttribute) {

	$scope.attributeAll = [];
	$scope.refresh = function() { attributeConnectorFactory.getAttributeAll($scope); };
	$scope.gotoUpdateAttribute = function(id) { gotoAttribute.update($location, id); };
	$scope.gotoCreateAttribute = function () { gotoAttribute.create($location); };
	$scope.deleteAttribute = function(id) {	attributeConnectorFactory.deleteAttribute($scope, id);};
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
		gotoAttribute.back($location);
	};
};

attributeControllers.maintainAttributeController = function ($scope, $routeParams, $location, attributeConnectorFactory, $translate, gotoAttribute) {
	$scope.attribute = {};
	init();
	
	function init() {
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title = next.title;
			$scope.mode = next.mode;
			if ($routeParams.id != undefined) {
				attributeConnectorFactory.loadAttribute($scope, $routeParams.id);
			}
		});
	};
	
	$scope.doMaintain = function () {
		if ($scope.mode == 'update') {
			attributeConnectorFactory.updateAttribute($scope, function($location) {gotoAttribute.all($location);}, function($location) {gotoAttribute.update($location, $scope.attribute.id);});
		} else {
			attributeConnectorFactory.createAttribute($scope, function($location) {gotoAttribute.all($location);}, function($location) {gotoAttribute.create($location);});
		}
	};
	
	$scope.doBack = function () {
		gotoAttribute.all($location);
	};
};