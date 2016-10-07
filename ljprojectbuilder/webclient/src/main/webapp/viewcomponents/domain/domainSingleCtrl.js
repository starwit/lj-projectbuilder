/**
 * This controller maintains a domain object and belongs to the view domain.single.html.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.domain').controller('domainSingleCtrl', domainSingleCtrl);
	
	domainSingleCtrl.$inject = ['$scope', '$routeParams', 'domainConnectorFactory', 'projectConnectorFactory', 'dialogService', 'gotoDomain'];
	function domainSingleCtrl($scope, $routeParams, domainConnectorFactory, projectConnectorFactory, dialogService, gotoDomain) {
		var  ctrl = this;
		
		ctrl.projectid = 0;
		ctrl.doMaintain = doMaintain;
		ctrl.doMaintainNext = doMaintainNext;
		ctrl.doMaintainDetail = doMaintainDetail;
		ctrl.doMaintainGenerate = doMaintainGenerate;
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
		
		function doMaintainNext() {
			if (ctrl.domain.id != null) {
				domainConnectorFactory.updateDomain(ctrl.domain).then(saveSuccessNext, saveError);
			} else {
				domainConnectorFactory.createDomain(ctrl.domain).then(saveSuccessNext, saveError)
			}
		};
		
		function doMaintainDetail() {
			if (ctrl.domain.id != null) {
				domainConnectorFactory.updateDomain(ctrl.domain).then(saveSuccessDetail, saveError);
			} else {
				domainConnectorFactory.createDomain(ctrl.domain).then(saveSuccessDetail, saveError);
			}
		};		
		
		function doMaintainGenerate() {
			if (ctrl.domain.id != null) {
				domainConnectorFactory.updateDomain(ctrl.domain).then(saveSuccessGenerate, saveError);
			} else {
				domainConnectorFactory.createDomain(ctrl.domain).then(saveSuccessGenerate, saveError);
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
				projectConnectorFactory.loadProject($routeParams.projectid)
				.then(	setProjectTitle, null);

				if ($routeParams.id != undefined) {
					domainConnectorFactory.loadDomain($routeParams.id).then(setDomain, loadError);
				}
			});
		};
		
		function setProjectTitle(response) {
			ctrl.projecttitle = response.title;		
		}
		
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
			ctrl.projecttitle = ctrl.domain.project.title;
		}
		
		/**
		 * Success message after saving.
		 */
		function saveSuccess(response) {
			setDomain(response);
			dialogService.showDialog("domain.dialog.success.title", "domain.save.success", dialogService.dialog.id.success, gotoDomainAll);
		};
		
		/**
		 * Success message after saving.
		 */
		function saveSuccessNext(response) {
			setDomain(response);
			dialogService.showDialog("domain.dialog.success.title", "domain.save.success", dialogService.dialog.id.success, gotoDomainNext);
		};
		
		/**
		 * Success message after saving.
		 */
		function saveSuccessDetail(response) {
			setDomain(response);
			dialogService.showDialog("domain.dialog.success.title", "domain.save.success", dialogService.dialog.id.success, gotoDomainDetail);
		};
		
		/**
		 * Success message after saving.
		 */
		function saveSuccessGenerate(response) {
			setDomain(response);
			dialogService.showDialog("domain.dialog.success.title", "domain.save.success", dialogService.dialog.id.success, gotoDomainGenerate);
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
		
		function gotoDomainNext() {
			gotoDomain.create(ctrl.domain.project.id);
		};
		
		function gotoDomainDetail() {
			gotoDomain.detail(ctrl.domain.project.id);
		};
		
		function gotoDomainGenerate() {
			gotoDomain.generate(ctrl.domain.project.id);
		};
	};
})();	