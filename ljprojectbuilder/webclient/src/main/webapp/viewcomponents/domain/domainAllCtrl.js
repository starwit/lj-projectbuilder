(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.domain').controller('domainAllCtrl', domainAllCtrl);
	
	function domainAllCtrl($routeParams, $scope, domainConnectorFactory, gotoDomain) {
		var  ctrl = this;

		ctrl.domainAll = [];
		ctrl.refresh = function() { domainConnectorFactory.getDomainsByProject($routeParams.projectid); };
		ctrl.gotoDomain = gotoDomain;
		ctrl.deleteDomain = function(id) {	domainConnectorFactory.deleteDomain(id).then(
				function(response) {
					domainConnectorFactory.getDomainsByProject(id); 
				}, null	);	
		};
	
		init();
		function init() {
			//change title on view change
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.projectid != undefined) {
					ctrl.projectid = $routeParams.projectid;
					domainConnectorFactory.getDomainsByProject($routeParams.projectid)
					.then(setDomainAll, null);
				}
			});
		}
		
		function setDomainAll(response) {
			ctrl.domainAll = response;		
		}
		
		ctrl.showDetails = function(domainid) {
		    var x = document.getElementById(domainid);
		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		    } else {
		        x.className = x.className.replace(" w3-show", "");
		    }
		};
	};
})();