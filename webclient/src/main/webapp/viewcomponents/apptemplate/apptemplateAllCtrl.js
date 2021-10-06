/**
 * This controller facilitates the apptemplate.all.html - view to display all apptemplates. 
 * It provides all needed functions for this view.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.apptemplate').controller('apptemplateAllCtrl', apptemplateAllCtrl);
	apptemplateAllCtrl.$inject = ['apptemplateConnectorFactory', 'dialogService', 'gotoAppTemplate', 'FileSaver', '$window'];
	function apptemplateAllCtrl(apptemplateConnectorFactory, dialogService, gotoAppTemplate, FileSaver, $window) {
		var  ctrl = this;

		ctrl.refresh = refresh;
		ctrl.deleteAppTemplate = deleteAppTemplate;
		ctrl.gotoAppTemplate = gotoAppTemplate;
		ctrl.showDetails = showDetails;
		ctrl.dialog = dialogService.dialog;
		ctrl.showImportDialog = showImportDialog;
		ctrl.closeDialog = closeDialog;
		ctrl.exportAppTemplate = exportAppTemplate;
		ctrl.importAppTemplate = importAppTemplate;
		init();
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.apptemplateAll = [];
			ctrl.apptemplate = {};
			ctrl.detailsign='+';
			apptemplateConnectorFactory.getAppTemplateAll().then(setAppTemplateAll, null);
		}
		
		function refresh() {
			apptemplateConnectorFactory.getAppTemplateAll().then(setAppTemplateAll, loadError);
		};
		
		function deleteAppTemplate(id) {
			apptemplateConnectorFactory.deleteAppTemplate(id).then(deleteSuccess, deleteError)
		};
		
		/**
		 * Shows the details of a apptemplate.
		 */
		function showDetails(apptemplateid) {
		    var x = document.getElementById(apptemplateid);
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
		function setAppTemplateAll(response) {
			ctrl.apptemplateAll = response;		
		}
		
		/**
		 * Success message after deleting.
		 */
		function deleteSuccess(response) {
			refresh();
			dialogService.showDialog("apptemplate.dialog.success.title", "apptemplate.delete.success", dialogService.dialog.id.success, gotoAppTemplate.all);
		};
		
		/**
		 * Error message after deleting.
		 */
		function deleteError(response) {
			refresh();
			dialogService.showDialog("apptemplate.dialog.error.title", "apptemplate.delete.error", dialogService.dialog.id.error, gotoAppTemplate.all);
		};
		
		/**
		 * Error message after loading domains for apptemplate.
		 */
		function loadError(response) {
			dialogService.showDialog("apptemplate.dialog.error.title", "apptemplate.load.error", dialogService.dialog.id.error, gotoAppTemplate.loaderror);
		};
		
		function closeDialog(dialogid) {
			dialogService.closeDialog(dialogid);
		};
		
		function showImportDialog() {
			dialogService.showDialog("apptemplate.dialog.import.title", "apptemplate.dialog.import.text", dialogService.dialog.id.fileimport, gotoAppTemplate.all);
		};
		
		function importError(response) {
			dialogService.showValidationDialog("apptemplate.dialog.error.title", response.message, response.validationErrors, dialogService.dialog.id.error, gotoAppTemplate.all);
		};
		
		function importSuccess() {
			refresh();
			dialogService.closeDialog('importdialog');
		}

		/**
		 * Export a loaded template
		 * @param {loaded template} template 
		 */
		function exportAppTemplate(template) {
			let filename = template.title + ".json";

			let toDownload = Object.assign({}, template);
			delete toDownload.id;
			delete toDownload["$$hashKey"];

			toDownload.templateFiles = toDownload.templateFiles.map(templateFile => {
				let templateFileCopy = Object.assign({}, templateFile);
				delete templateFileCopy.id;
				return templateFileCopy;
			})

			let fileToSave = new Blob([angular.toJson(toDownload, true)], {
				type: 'application/json',
				name: filename
			})
			FileSaver.saveAs(fileToSave, filename);
			
		}

		/**
		 * Import a template previously chosen in the file input
		 */
		function importAppTemplate() {
			let importInput = $window.document.getElementById("import");
			if (importInput.files.length > 0) {
				var reader = new FileReader();
				reader.addEventListener("loadend", uploadAppTemplate)
				reader.readAsText(importInput.files[0]);
			}
		}  

		/**
		 * Uploads the loaded template to the server
		 * @param {loadend event of a FileReader instance} event 
		 */
		function uploadAppTemplate(event) {
			let importTemplate = angular.fromJson(event.target.result)
			apptemplateConnectorFactory.createAppTemplate(importTemplate).then(importSuccess, importError);

		}
	};
})();