/**
 * This controller maintains a domain object and belongs to the view domain.single.html.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.domain').controller('domainSingleCtrl', domainSingleCtrl);
	
	domainSingleCtrl = ['$scope', '$routeParams', 'domainConnectorFactory', 'gotoDomain', function ($scope, $routeParams, domainConnectorFactory, gotoDomain) {
		var  ctrl = this;
		
		ctrl.doMaintain = doMaintain;
		ctrl.deleteDomain = deleteDomain;
		ctrl.addAttribute = addAttribute;
		ctrl.removeAttribute = removeAttribute;
		init();
		
		/**
		 * Standard function to edit a domain object.
		 */
		function doMaintain() {
			if (ctrl.domain != null && ctrl.domain.id != null) {
				domainConnectorFactory.updateDomain(ctrl.domain).then(
						function(response) {gotoDomain.all(ctrl.domain.project.id);}, 
						function(response) {gotoDomain.update(ctrl.domain.project.id, ctrl.domain.id);});
			} else {
				domainConnectorFactory.createDomain(ctrl.domain).then(
						function(response) {gotoDomain.all(ctrl.domain.project.id);}, 
						function(response) {gotoDomain.create(ctrl.domain.project.id);});
			}
		};
		
		/**
		 * Standard function to delete a domain object.
		 */
		function deleteDomain(id) {	domainConnectorFactory.deleteDomain(id).then(
				function(response) {
					domainConnectorFactory.getDomainsByProject(id); 
				}, null	);	
		};

		/**
		 * Add an attribute to a domain.
		 */
		function addAttribute() {
			if (ctrl.domain.attributes == undefined) {
				ctrl.domain.attributes = [];
			}
			var attribute = {};
			attribute.dataType = "String";
			attribute.name = "attribute";
			ctrl.domain.attributes.push(attribute);
		};
		
		/**
		 * Remove an attribute to a domain.
		 */
		function removeAttribute($index) {
			if (ctrl.domain.attributes != undefined && $index > -1) {
				ctrl.domain.attributes.splice($index, 1);
			}
		};
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.domain = {};
			ctrl.projectid = {};
			domainConnectorFactory.getTypes().then(setDataTypes, null);

			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				ctrl.projectid = $routeParams.projectid;
				ctrl.domain.project = {};
				ctrl.domain.project.id = ctrl.projectid;

				if ($routeParams.id != undefined) {
					domainConnectorFactory.loadDomain($routeParams.id).then(setDomain, null);
				}
			});
		};
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setDataTypes(response) {
			ctrl.dataTypes = response;
		}
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setDomain(response) {
			ctrl.domain = response;
		}
	}];
})();	