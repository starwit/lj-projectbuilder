/**
 * This controller facilitates the project.all.html - view to display all projects. 
 * It provides all needed functions for this view.
 */
(function() {
	'use strict';
	angular.module('ljprojectbuilderApp.project').controller('projectAllCtrl', projectAllCtrl);
	
	projectAllCtrl.$inject = ['projectConnectorFactory', 'gotoProject'];
	function projectAllCtrl(projectConnectorFactory, gotoProject) {
		var  ctrl = this;
		
		ctrl.deleteProject = deleteProject;
		ctrl.gotoProject = gotoProject;
		init();
		
		/** 
		 * Standard function for initialization.
		 */
		function init() {
			ctrl.projectAll = [];
			projectConnectorFactory.getProjectAll().then(setProjectAll, null);
		}
		
		function deleteProject(id) {
			projectConnectorFactory.deleteProject(id).then(projectConnectorFactory.getProjectAll(), null)
		};
		
		/**
		 * Used for setting the database result to the representation-object in the controller.
		 */
		function setProjectAll(response) {
			ctrl.projectAll = response;		
		}
	};
})();