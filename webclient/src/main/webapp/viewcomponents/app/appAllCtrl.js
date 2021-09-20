/**
 * This controller facilitates the app.all.html - view to display all apps. 
 * It provides all needed functions for this view.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.app').controller('appAllCtrl', appAllCtrl);
	appAllCtrl.$inject = ['appConnectorFactory', 'dialogService', 'gotoApp'];
	function appAllCtrl(appConnectorFactory, dialogService, gotoApp) {
		var  ctrl = this;
		
		ctrl.refresh = refresh;
		ctrl.deleteApp = deleteApp;
		ctrl.gotoApp = gotoApp;
		ctrl.showDetails = showDetails;
		ctrl.dialog = dialogService.dialog;
		ctrl.closeDialog = closeDialog;
		init();
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.appAll = [];
			ctrl.app = {};
			ctrl.detailsign='+';
			appConnectorFactory.getAppAll().then(setAppAll, null);
		}
		
		function refresh() {
			appConnectorFactory.getAppAll().then(setAppAll, loadError);
		};
		
		function deleteApp(id) {
			appConnectorFactory.deleteApp(id).then(deleteSuccess, deleteError)
		};
		
		/**
		 * Shows the details of a app.
		 */
		function showDetails(appId) {
		    var x = document.getElementById(appId);
		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		    } else {
		        x.className = x.className.replace(" w3-show", "");
		    }
		    
		};
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setAppAll(response) {
			ctrl.appAll = response;		
		}
		
		/**
		 * Success message after deleting.
		 */
		function deleteSuccess(response) {
			refresh();
			dialogService.showDialog("app.dialog.success.title", "app.delete.success", dialogService.dialog.id.success, gotoApp.all);
		};
		
		/**
		 * Error message after deleting.
		 */
		function deleteError(response) {
			refresh();
			dialogService.showDialog("app.dialog.error.title", "app.delete.error", dialogService.dialog.id.error, gotoApp.all);
		};
		
		/**
		 * Error message after loading domains for app.
		 */
		function loadError(response) {
			dialogService.showDialog("app.dialog.error.title", "app.load.error", dialogService.dialog.id.error, gotoApp.loaderror);
		};
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		};
	};
})();