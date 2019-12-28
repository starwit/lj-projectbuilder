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
		ctrl.gotoDomainGenerate = gotoDomainGenerate;
		ctrl.addAttribute = addAttribute;
		ctrl.removeAttribute = removeAttribute;
		ctrl.dialog = dialogService.dialog;
		ctrl.closeDialog = closeDialog;
		ctrl.closeDialogWithErrors = dialogService.closeDialogWithErrors;
		ctrl.resetAndContinue = dialogService.resetAndContinue;
		ctrl.showValidationDialog = dialogService.showValidationDialog;
		
		init();
		
		/**
		 * Standard function to edit the project configuration.
		 */
		function doMaintain() {
			if (ctrl.form.$dirty) {
				doMaintainThenGoto(gotoDomainAll);
			} else {
				gotoDomainAll();
			}
		}
		
		function doMaintainNext() {
			if (ctrl.form.$dirty) {
				doMaintainThenGoto(gotoDomainNext);
			} else {
				gotoDomainNext();
			}
		};
		
		function doMaintainDetail() {
			if (ctrl.form.$dirty) {
				doMaintainThenGoto(gotoDomainDetail);
			} else {
				gotoDomainDetail();
			}
		};		
		
		function doMaintainGenerate() {
			if (ctrl.form.$dirty) {
				doMaintainThenGoto(gotoDomainGenerate);
			} else {
				gotoDomainGenerate();
			}
		};		

		function doMaintainThenGoto(gotoDestination) {
			ctrl.domain.projectId = ctrl.projectid;
			var saveFunction = isUpdate() ? domainConnectorFactory.updateDomain : domainConnectorFactory.createDomain;
			saveFunction(ctrl.domain).then(saveSuccessCallbackThatGoesTo(gotoDestination), saveError(gotoDestination));
		}

		function isUpdate() {
			return ctrl.domain != null && ctrl.domain.id != null;
		}
		
		/**
		 * Success message after saving.
		 */
		function saveSuccessCallbackThatGoesTo(gotoDestination) {
			return function (response) {
				setDomain(response);
				dialogService.showDialog("domain.dialog.success.title", "domain.save.success", dialogService.dialog.id.success, gotoDestination);
			}
		}
		
		/**
		 * Error message after saving.
		 */
		function saveError(gotoDestination) {
			return function (response) {
				if (response.responseCode == 'NOT_VALID') {
					dialogService.showValidationDialog("domain.dialog.error.title", response.message, response.validationErrors, dialogService.dialog.id.error, gotoDestination);
				} else {
					dialogService.showDialog("domain.dialog.error.title", "domain.save.error", dialogService.dialog.id.error, function(){});
				}
			}
		}
		
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

			$scope.$on('$routeChangeSuccess', function(scope, next, current) {
				initinit();
			});
		};
		
		function initinit() {
			ctrl.projectid = $routeParams.projectid;
			ctrl.domain.project = {};
			ctrl.domain.project.id = ctrl.projectid;
			projectConnectorFactory.loadProject($routeParams.projectid)
			.then(setProjectTitle, null);

			if ($routeParams.id != undefined) {
				domainConnectorFactory.loadDomain($routeParams.id).then(setDomain, loadError);
			}
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
		}
		
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
			gotoDomain.all(ctrl.projectid);
		};
		
		function gotoDomainNext() {
			ctrl.domain = {};
			ctrl.projectid = {};
			domainConnectorFactory.getTypes().then(setDataTypes, null);
			initinit();
			gotoDomain.create(ctrl.projectid);
		};
		
		function gotoDomainDetail() {
			gotoDomain.detail(ctrl.projectid);
		};
		
		function gotoDomainGenerate() {
			gotoDomain.generate(ctrl.projectid);
		};
	};
})();	