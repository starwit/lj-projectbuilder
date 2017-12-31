/**
 * This controller facilitates the projecttemplate.all.html - view to display all projecttemplates. 
 * It provides all needed functions for this view.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.projecttemplate').controller('projecttemplateAllCtrl', projecttemplateAllCtrl);
	projecttemplateAllCtrl.$inject = ['projecttemplateConnectorFactory', 'dialogService', 'gotoProjectTemplate'];
	function projecttemplateAllCtrl(projecttemplateConnectorFactory, dialogService, gotoProjectTemplate) {
		var  ctrl = this;
		
		ctrl.refresh = refresh;
		ctrl.deleteProjectTemplate = deleteProjectTemplate;
		ctrl.gotoProjectTemplate = gotoProjectTemplate;
		ctrl.showDetails = showDetails;
		ctrl.dialog = dialogService.dialog;
		ctrl.closeDialog = closeDialog;
		init();
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.projecttemplateAll = [];
			ctrl.projecttemplate = {};
			ctrl.detailsign='+';
			projecttemplateConnectorFactory.getProjectTemplateAll().then(setProjectTemplateAll, null);
		}
		
		function refresh() {
			projecttemplateConnectorFactory.getProjectTemplateAll().then(setProjectTemplateAll, loadError);
		};
		
		function deleteProjectTemplate(id) {
			projecttemplateConnectorFactory.deleteProjectTemplate(id).then(deleteSuccess, deleteError)
		};
		
		/**
		 * Shows the details of a projecttemplate.
		 */
		function showDetails(projecttemplateid) {
		    var x = document.getElementById(projecttemplateid);
		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		        ctrl.detailsign = '-';
		    } else {
		        x.className = x.className.replace(" w3-show", "");
		        ctrl.detailsign = '+';
		    }
		    
		};
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setProjectTemplateAll(response) {
			ctrl.projecttemplateAll = response;		
		}
		
		/**
		 * Success message after deleting.
		 */
		function deleteSuccess(response) {
			refresh();
			dialogService.showDialog("projecttemplate.dialog.success.title", "projecttemplate.delete.success", dialogService.dialog.id.success, gotoProjectTemplate.all);
		};
		
		/**
		 * Error message after deleting.
		 */
		function deleteError(response) {
			refresh();
			dialogService.showDialog("projecttemplate.dialog.error.title", "projecttemplate.delete.error", dialogService.dialog.id.error, gotoProjectTemplate.all);
		};
		
		/**
		 * Error message after loading domains for projecttemplate.
		 */
		function loadError(response) {
			dialogService.showDialog("projecttemplate.dialog.error.title", "projecttemplate.load.error", dialogService.dialog.id.error, gotoProjectTemplate.loaderror);
		};
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		};
	};
})();