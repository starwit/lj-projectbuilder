/**
 * This controller facilitates the domain.all.html - view to display all domains of a project. 
 * It provides all needed functions for this view.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.domain').controller('domainAllCtrl', domainAllCtrl);
	
	function domainAllCtrl($routeParams, $scope, domainConnectorFactory, gotoDomain) {
		var  ctrl = this;

		ctrl.domainAll = [];
		ctrl.refresh = refresh;
		ctrl.gotoDomain = gotoDomain;
		ctrl.deleteDomain = deleteDomain;
		ctrl.showDetails = showDetails;
	
		init();
		
		/**
		 * refreshs the view from database.
		 */
		function refresh() { 
			domainConnectorFactory.getDomainsByProject($routeParams.projectid); 
		};
		
		/**
		 * Deletes the domain object with the given id.
		 */
		function deleteDomain(id) {
			domainConnectorFactory.deleteDomain(id).then(
				function(response) {
					domainConnectorFactory.getDomainsByProject(id); 
				}, null	);	
		};
		
		/**
		 * Shows the details (e.g. attributes) of a domain object.
		 */
		function showDetails(domainid) {
		    var x = document.getElementById(domainid);
		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		    } else {
		        x.className = x.className.replace(" w3-show", "");
		    }
		};
		
		/** 
		 * Standard function for initialization.
		 */
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
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setDomainAll(response) {
			ctrl.domainAll = response;		
		}
	};
})();