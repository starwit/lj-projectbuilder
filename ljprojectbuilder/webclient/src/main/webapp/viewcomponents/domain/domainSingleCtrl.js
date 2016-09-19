/**
 * This controller maintains a domain object and belongs to the view domain.single.html.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.domain').controller('domainSingleCtrl', domainSingleCtrl);
	
	domainSingleCtrl.$inject = ['$scope', '$routeParams', 'domainConnectorFactory', 'dialogService', 'gotoDomain'];
	function domainSingleCtrl($scope, $routeParams, domainConnectorFactory, dialogService, gotoDomain) {
		var  ctrl = this;
		
		ctrl.doMaintain = doMaintain;
		ctrl.addAttribute = addAttribute;
		ctrl.removeAttribute = removeAttribute;
		ctrl.dialog = dialogService.dialog;
		ctrl.closeDialog = closeDialog;
		init();
		
		/**
		 * Standard function to edit a domain object.
		 */
		function doMaintain() {
			if (ctrl.domain.id != null) {
				domainConnectorFactory.updateDomain(ctrl.domain).then(saveSuccess, saveError);
			} else {
				domainConnectorFactory.createDomain(ctrl.domain).then(saveSuccess, saveError)
			}
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
					domainConnectorFactory.loadDomain($routeParams.id).then(setDomain, loadError);
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
		
		/**
		 * Success message after saving.
		 */
		function saveSuccess(response) {
			setDomain(response);
			dialogService.showDialog("domain.dialog.success.title", "domain.save.success", dialogService.dialog.id.success, gotoDomainAll);
		};
		
		/**
		 * Error message after saving.
		 */
		function saveError(response) {
			dialogService.showDialog("domain.dialog.error.title", "domain.save.error", dialogService.dialog.id.error, function(){});
		};
		
		/**
		 * Error message after loading the project.
		 */
		function loadError(response) {
			dialogService.showDialog("domain.dialog.error.title", "domain.load.error", dialogService.dialog.id.error, gotoDomainAll);
		};
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		};
		
		function gotoDomainAll() {
			gotoDomain.all(ctrl.domain.project.id);
		};
	};
})();	