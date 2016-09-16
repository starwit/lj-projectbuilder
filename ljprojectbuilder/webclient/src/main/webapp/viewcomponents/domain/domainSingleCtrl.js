(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.domain').controller('domainSingleCtrl', domainSingleCtrl);
	
	function domainSingleCtrl($scope, $routeParams, $location, domainConnectorFactory, $translate, gotoDomain) {
		init();
		
		function init() {
			$scope.domain = {};
			$scope.projectid = {};
			domainConnectorFactory.getTypes().then(setDataTypes, null);

			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				$scope.projectid = $routeParams.projectid;
				$scope.domain.project = {};
				$scope.domain.project.id = $scope.projectid;

				if ($routeParams.id != undefined) {
					domainConnectorFactory.loadDomain($routeParams.id).then(setDomain, null);
				}
			});
		};
		
		function setDataTypes(response) {
			$scope.dataTypes = response;
		}
		
		function setDomain(response) {
			$scope.domain = response;
		}
		
		$scope.doMaintain = function () {
			if ($scope.domain != null && $scope.domain.id != null) {
				domainConnectorFactory.updateDomain($scope.domain).then(
						function(response) {gotoDomain.all(response.project.id);}, 
						function(response) {gotoDomain.update($scope.domain.project.id, $scope.domain.id);});
			} else {
				domainConnectorFactory.createDomain($scope.domain).then(
						function(response) {gotoDomain.all($scope.domain.project.id);}, 
						function(response) {gotoDomain.create($scope.domain.project.id);});
			}
		};
		
		$scope.deleteDomain = function(id) {	domainConnectorFactory.deleteDomain(id).then(
				function(response) {
					domainConnectorFactory.getDomainsByProject(id); 
				}, null	);	
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
	};
})();	