var ${domain?uncap_first}Controllers = {};
${domain?uncap_first}Controllers.load${domain}Controller = function($rootScope, $scope, $location, ${domain?uncap_first}ConnectorFactory, $translate, goto${domain}) {

	$scope.${domain?lower_case}All = [];
	$scope.refresh = function() { ${domain?uncap_first}ConnectorFactory.get${domain}All($scope); };
	$scope.gotoUpdate${domain} = function(id) { goto${domain}.update($location, id); };
	$scope.gotoCreate${domain} = function () { goto${domain}.create($location); };
	$scope.delete${domain} = function(id) {	${domain?uncap_first}ConnectorFactory.delete${domain}($scope, id);};
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
		goto${domain}.back($location);
	};
};

${domain?uncap_first}Controllers.maintain${domain}Controller = function ($scope, $routeParams, $location, ${domain?uncap_first}ConnectorFactory, $translate, goto${domain}) {
	$scope.${domain?lower_case} = {};
	init();
	
	function init() {
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title = next.title;
			$scope.mode = next.mode;
			if ($routeParams.id != undefined) {
				${domain?uncap_first}ConnectorFactory.load${domain}($scope, $routeParams.id);
			}
		});
	};
	
	$scope.doMaintain = function () {
		if ($scope.mode == 'update') {
			${domain?uncap_first}ConnectorFactory.update${domain}($scope, function($location) {goto${domain}.all($location);}, function($location) {goto${domain}.update($location, $scope.${domain?lower_case}.id);});
		} else {
			${domain?uncap_first}ConnectorFactory.create${domain}($scope, function($location) {goto${domain}.all($location);}, function($location) {goto${domain}.create($location);});
		}
	};
	
	$scope.doBack = function () {
		goto${domain}.all($location);
	};
};