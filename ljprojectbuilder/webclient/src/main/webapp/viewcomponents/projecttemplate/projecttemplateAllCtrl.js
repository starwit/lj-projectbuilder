/**
 * This controller facilitates the projecttemplate.all.html - view to display all projecttemplates. 
 * It provides all needed functions for this view.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.projecttemplate').controller('projecttemplateAllCtrl', projecttemplateAllCtrl);
	projecttemplateAllCtrl.$inject = ['projecttemplateConnectorFactory', 'dialogService', 'gotoProjectTemplate', 'FileSaver', '$window'];
	function projecttemplateAllCtrl(projecttemplateConnectorFactory, dialogService, gotoProjectTemplate, FileSaver, $window) {
		var  ctrl = this;

		ctrl.refresh = refresh;
		ctrl.deleteProjectTemplate = deleteProjectTemplate;
		ctrl.gotoProjectTemplate = gotoProjectTemplate;
		ctrl.showDetails = showDetails;
		ctrl.dialog = dialogService.dialog;
		ctrl.closeDialog = closeDialog;
		ctrl.exportProjectTemplate = exportProjectTemplate;
		ctrl.importProjectTemplate = importProjectTemplate;
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

		/**
		 * Export a loaded template
		 * @param {loaded template} template 
		 */
		function exportProjectTemplate(template) {
			let filename = template.title + ".json";

			let toDownload = Object.assign({}, template);
			delete toDownload.id;
			delete toDownload["$$hashKey"];

			toDownload.codeTemplates = toDownload.codeTemplates.map(codeTemplate => {
				let codeTemplateCopy = Object.assign({}, codeTemplate);
				delete codeTemplateCopy.id;
				return codeTemplateCopy;
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
		function importProjectTemplate() {
			let importInput = $window.document.getElementById("import");

			if (importInput.files.length > 0) {
				var reader = new FileReader();
				reader.addEventListener("loadend", uploadProjectTemplate)
				reader.readAsText(importInput.files[0]);
			}
		}  

		/**
		 * Uploads the loaded template to the server
		 * @param {loadend event of a FileReader instance} event 
		 */
		function uploadProjectTemplate(event) {
			let importTemplate = angular.fromJson(event.target.result)
			projecttemplateConnectorFactory.createProjectTemplate(importTemplate);

		}
	};
})();