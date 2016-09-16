(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.domain').controller('domainAllCtrl', domainAllCtrl);
	
	function domainAllCtrl($rootScope, $routeParams, $scope, $location, domainConnectorFactory, $translate, gotoDomain) {

		$scope.domainAll = [];
		$scope.refresh = function() { domainConnectorFactory.getDomainsByProject($routeParams.projectid); };
		$scope.gotoDomain = gotoDomain;
		$scope.deleteDomain = function(id) {	domainConnectorFactory.deleteDomain(id).then(
				function(response) {
					domainConnectorFactory.getDomainsByProject(id); 
				}, null	);	
		};
		$scope.setSelected = function (idSelected) { $scope.idSelected = idSelected; };
	
		init();
		function init() {
			//change title on view change
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.projectid != undefined) {
					$scope.projectid = $routeParams.projectid;
					domainConnectorFactory.getDomainsByProject($routeParams.projectid)
					.then(setDomainAll, null);
				}
			});
		}
		
		function setDomainAll(response) {
			$scope.domainAll = response;		
		}
		
		$scope.showDetails = function(domainid) {
		    var x = document.getElementById(domainid);
		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		    } else {
		        x.className = x.className.replace(" w3-show", "");
		    }
		};
	};
})();