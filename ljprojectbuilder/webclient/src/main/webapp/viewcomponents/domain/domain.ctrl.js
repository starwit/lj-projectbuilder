var domainControllers = {};
domainControllers.loadDomainController = function($rootScope, $routeParams, $scope, $location, domainConnectorFactory, $translate, gotoDomain) {

	$scope.domainAll = [];
	$scope.refresh = function() { domainConnectorFactory.getDomainsByProject($routeParams.projectid); };
	$scope.gotoUpdateDomain = function(projectid, id) { gotoDomain.update($location, projectid, id); };
	$scope.gotoCreateDomain = function (projectid) { gotoDomain.create($location, projectid); };
	$scope.deleteDomain = function(id) {	domainConnectorFactory.deleteDomain($scope, id);};
	$scope.setSelected = function (idSelected) { $scope.idSelected = idSelected; };
	
	init();
	function init() {
		//change title on view change
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title=next.title;
			$scope.subtitle=next.subtitle;
			if ($routeParams.projectid != undefined) {
				$scope.projectid = $routeParams.projectid;
				domainConnectorFactory.getDomainsByProject($routeParams.projectid)
				.then(function(response) {
					$scope.domainAll = response;
				}, null);
			}
		});
	}
	
	$scope.doBack = function () {
		gotoDomain.back($location);
	};
	
	$scope.showDetails = function(domainid) {
	    var x = document.getElementById(domainid);
	    if (x.className.indexOf("w3-show") == -1) {
	        x.className += " w3-show";
	    } else {
	        x.className = x.className.replace(" w3-show", "");
	    }
	};
};

domainControllers.maintainDomainController = function ($scope, $routeParams, $location, domainConnectorFactory, $translate, gotoDomain) {
	$scope.domain = {};
	$scope.projectid = {};
	init();
	
	$scope.deleteDomain = function(id) {	domainConnectorFactory.deleteDomain($scope, id);	};
	
	function init() {
		$scope.$on('$routeChangeSuccess', function (scope, next, current) {
			$scope.title = next.title;
			$scope.mode = next.mode;
			$scope.projectid = $routeParams.projectid;
			$scope.domain.project = {};
			$scope.domain.project.id = $scope.projectid;
			
			domainConnectorFactory.getTypes().then(function(response) {
				$scope.dataTypes = response;
			})

			if ($routeParams.id != undefined) {
				domainConnectorFactory.loadDomain($scope, $routeParams.id);
			}
		});
	};
	
	$scope.doMaintain = function () {
		if ($scope.mode == 'update') {
			domainConnectorFactory.updateDomain($scope, 
					function($location) {gotoDomain.all($location, $scope.domain.project.id);}, 
					function($location) {gotoDomain.update($location, $scope.domain.project.id, $scope.domain.id);});
		} else {
			domainConnectorFactory.createDomain($scope, 
					function($location) {gotoDomain.all($location, $scope.domain.project.id);}, 
					function($location) {gotoDomain.create($location, $scope.domain.project.id);});
		}
	};
	
	$scope.addAttribute = function () {
		if ($scope.domain.attributes == undefined) {
			$scope.domain.attributes = [];
		}
		var attribute = {};
		attribute.dataType = "String";
		attribute.name = "attribute";
		$scope.domain.attributes.push(attribute);
	};
	
	$scope.removeAttribute = function ($index) {
		if ($scope.domain.attributes != undefined && $index > -1) {
			$scope.domain.attributes.splice($index, 1);
		}
	};
	
	$scope.doBack = function () {
		gotoDomain.all($location);
	};
};