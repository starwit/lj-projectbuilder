/**
 * This controller facilitates the project.all.html - view to display all projects. 
 * It provides all needed functions for this view.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.project').controller('projectAllCtrl', projectAllCtrl);
	
	projectAllCtrl.$inject = ['projectConnectorFactory', 'dialogService', 'gotoProject'];
	function projectAllCtrl(projectConnectorFactory, dialogService, gotoProject) {
		var  ctrl = this;
		
		ctrl.refresh = refresh;
		ctrl.deleteProject = deleteProject;
		ctrl.gotoProject = gotoProject;
		ctrl.showDetails = showDetails;
		ctrl.dialog = dialogService.dialog;
		ctrl.closeDialog = closeDialog;
		init();
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.projectAll = [];
			ctrl.project = {};
			projectConnectorFactory.getProjectAll().then(setProjectAll, null);
		}
		
		function refresh() {
			projectConnectorFactory.getProjectAll().then(setProjectAll, loadError);
		};
		
		function deleteProject(id) {
			projectConnectorFactory.deleteProject(id).then(deleteSuccess, deleteError)
		};
		
		/**
		 * Shows the details of a project.
		 */
		function showDetails(projectid) {
		    var x = document.getElementById(projectid);
		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		    } else {
		        x.className = x.className.replace(" w3-show", "");
		    }
		};
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setProjectAll(response) {
			ctrl.projectAll = response;		
		}
		
		/**
		 * Success message after deleting.
		 */
		function deleteSuccess(response) {
			refresh();
			dialogService.showDialog("project.dialog.success.title", "project.delete.success", dialogService.dialog.id.success, gotoProject.all);
		};
		
		/**
		 * Error message after deleting.
		 */
		function deleteError(response) {
			refresh();
			dialogService.showDialog("project.dialog.error.title", "project.delete.error", dialogService.dialog.id.error, gotoProject.all);
		};
		
		/**
		 * Error message after loading domains for project.
		 */
		function loadError(response) {
			dialogService.showDialog("project.dialog.error.title", "project.load.error", dialogService.dialog.id.error, gotoProject.loaderror);
		};
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		};
	};
})();