(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.domain').controller('domainSingleCtrl', domainSingleCtrl);
	
	function domainSingleCtrl($scope, $routeParams, domainConnectorFactory, gotoDomain) {
		var  ctrl = this;
		init();
		
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
		
		function setDataTypes(response) {
			ctrl.dataTypes = response;
		}
		
		function setDomain(response) {
			ctrl.domain = response;
		}
		
		ctrl.doMaintain = function () {
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
		
		ctrl.deleteDomain = function(id) {	domainConnectorFactory.deleteDomain(id).then(
				function(response) {
					domainConnectorFactory.getDomainsByProject(id); 
				}, null	);	
		};
		
		ctrl.addAttribute = function () {
			if (ctrl.domain.attributes == undefined) {
				ctrl.domain.attributes = [];
			}
			var attribute = {};
			attribute.dataType = "String";
			attribute.name = "attribute";
			ctrl.domain.attributes.push(attribute);
		};
		
		ctrl.removeAttribute = function ($index) {
			if (ctrl.domain.attributes != undefined && $index > -1) {
				ctrl.domain.attributes.splice($index, 1);
			}
		};
	};
})();	