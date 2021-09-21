/**
 * This controller facilitates the domain.all.html - view to display all domains of a app. 
 * It provides all needed functions for this view.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.domain').controller('domainAllCtrl', domainAllCtrl);
	
	domainAllCtrl.$inject = ['$scope', '$routeParams', 'domainConnectorFactory', 'appConnectorFactory', 'dialogService', 'gotoDomain'];
	function domainAllCtrl($scope, $routeParams, domainConnectorFactory, appConnectorFactory, dialogService, gotoDomain) {
		var  ctrl = this;

		ctrl.domainAll = [];
		ctrl.apptitle = "";
		ctrl.appid = 0;
		ctrl.refresh = refresh;
		ctrl.gotoDomain = gotoDomain;
		ctrl.deleteDomain = deleteDomain;
		ctrl.dialog = dialogService.dialog;
		ctrl.showDetails = showDetails;
		ctrl.closeDialog = closeDialog;
	
		init();
		
		/**
		 * refreshs the view from database.
		 */
		function refresh() { 
			domainConnectorFactory.getDomainsByApp(ctrl.appid).then(setDomainAll, loadError); 
		};
		
		/**
		 * Deletes the domain object with the given id.
		 */
		function deleteDomain(id) {	
			domainConnectorFactory.deleteDomain(id).then(deleteSuccess, deleteError);
		};
		
		/**
		 * Shows the details (e.g. attributes) of a domain object.
		 */
		function showDetails(domainId) {
		    var x = document.getElementById(domainId);
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
			$scope.$on('$routeChangeSuccess', function (scope, next, current) {
				if ($routeParams.appid != undefined) {
					ctrl.appid = $routeParams.appid;
					appConnectorFactory.loadApp($routeParams.appid)
					.then(	setAppTitle, null);
					refresh();
				}
			});
		}
		
		function setAppTitle(response) {
			ctrl.apptitle = response.title;		
		}
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setDomainAll(response) {
			ctrl.domainAll = response;		
		}
				
		/**
		 * Success message after deleting.
		 */
		function deleteSuccess(response) {
			refresh();
			dialogService.showDialog("domain.dialog.success.title", "domain.delete.success", dialogService.dialog.id.success, gotoDomainAll);
		};
		
		/**
		 * Error message after deleting.
		 */
		function deleteError(response) {
			refresh();
			dialogService.showDialog("domain.dialog.error.title", "domain.delete.error", dialogService.dialog.id.error, gotoDomainAll);
		};
		
		/**
		 * Error message after loading domains for app.
		 */
		function loadError(response) {
			dialogService.showDialog("domain.dialog.error.title", "domain.load.error", dialogService.dialog.id.error, gotoDomain.loaderror);
		};
		
		function gotoDomainAll() {
			gotoDomain.all(ctrl.appid);
		};
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		};
	};
})();